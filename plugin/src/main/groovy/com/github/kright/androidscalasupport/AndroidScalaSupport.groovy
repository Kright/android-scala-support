package com.github.kright.androidscalasupport

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.internal.AbstractTask
import org.gradle.api.tasks.compile.JavaCompile
import org.gradle.api.tasks.scala.ScalaCompile

/**
 * Created by lgor on 24.09.2016.
 */
class AndroidScalaSupport implements Plugin<Project> {

	protected Project project
	protected AndroidScalaExtension extension
	protected File workDir
	protected androidExtension
	protected androidPlugin
	protected boolean isLibrary

	void apply(Project target) {
		this.project = target

		extension = project.extensions.create("androidScala", AndroidScalaExtension, project)
		(androidPlugin, isLibrary) = getAndroidPlugin()

		initWorkDir()
		updateAndroidExtension()
		setMainDexModification()
		setScalaCompileTaskAdding()
	}

	/**
	 * @return pair (appPlugin, false) or (libraryPlugin, true)
	 * @throws GradleException if android plugin isn't found
	 */
	private getAndroidPlugin() {
		def androidPlugin = project.plugins.findPlugin('com.android.application')
		if (androidPlugin)
			return [androidPlugin, false]

		androidPlugin = project.plugins.findPlugin('com.android.library')
		if (androidPlugin)
			return [androidPlugin, true]

		throw new GradleException("You have to apply 'com.android.application' or 'com.android.library' before!")
	}

	/**
	 * creates special folder in build dir, which used by scala compiler
	 */
	private initWorkDir() {
		workDir = new File(project.buildDir, "android-scala-support")
		if (workDir.exists()) {
			assert workDir.isDirectory()
			return
		}

		assert workDir.mkdirs()
	}

	/**
	 * @param name of subdirectory
	 * @return subdirectory in build\android-scala-compile
	 */
	private File workSubdir(String name) {
		return new File(workDir, name)
	}

	/**
	 *  updates android plugin extension, adds callbacks in it
	 */
	private updateAndroidExtension() {
		androidExtension = project.extensions.getByName("android")

		updateAndroidSourceSets()

		androidExtension.buildTypes.whenObjectAdded { updateAndroidSourceSets() }
		androidExtension.productFlavors.whenObjectAdded { updateAndroidSourceSets() }
		androidExtension.signingConfigs.whenObjectAdded { updateAndroidSourceSets() }
	}

	/**
	 * adds *.scala files to sources
	 */
	private updateAndroidSourceSets() {
		androidExtension.sourceSets.each {
			it.java.filter.include("**/*.scala")
		}
	}

	/**
	 * creates MainDexModifier and sets callbacks
	 */
	private setMainDexModification() {
		if (isLibrary)
			return

		MainDexModifier modifier = new MainDexModifier(this)

		androidExtension.applicationVariants.all { variant ->
			def name = variant.name.capitalize()

			project.tasks.getByName("process${name}Manifest").doLast { manifestProcessorTask ->
				if (extension.multiDex.mainDexOverwriteRule == null)
					return

				File manifest = manifestProcessorTask.manifestOutputFile

				project.tasks.getByName("transformClassesWithDexFor$name").doFirst { transformTask ->
					transformTask.inputs.files.filter { it.name.equals('maindexlist.txt') }.files.each { dexFile ->
						modifier.modify(dexFile, extension.multiDex.mainDexOverwriteRule, manifest)
					}
				}
			}
		}
	}

	/**
	 * creates ScalaCompile tasks for each build variant
	 */
	private setScalaCompileTaskAdding() {
		if (isLibrary)
			androidExtension.libraryVariants.all { addScalaCompile(it) }
		else
			androidExtension.applicationVariants.all { addScalaCompile(it) }

		androidExtension.testVariants.all { addScalaCompile(it) }
		androidExtension.unitTestVariants.all { addScalaCompile(it) }
	}

	/**
	 * creates and sets up ScalaCompile task for given build variant
	 *
	 * @param variant android build variant
	 */
	private addScalaCompile(variant) {
		def scalaCompileTaskName = "compileScala(${variant.name})"
		def javaCompileTask = variant.javaCompiler

		if (!(javaCompileTask instanceof JavaCompile))
			throw new GradleException("Jack compiler isn't supported")

		def scalaCompileTask = project.tasks.create(scalaCompileTaskName, ScalaCompile)
		project.logger.info("create task: ${scalaCompileTaskName}")

		setTaskParams(scalaCompileTask, javaCompileTask)
		copyDependencies(scalaCompileTask, javaCompileTask)
		javaCompileTask.dependsOn(scalaCompileTask)

		javaCompileTask.enabled = false

		scalaCompileTask.doFirst {
			scalaCompileTask.source = new TreeSet(javaCompileTask.source.collect { it })
			// because R.java files will be generated and added by one of previous tasks
			project.logger.info("${scalaCompileTask} sources : " + scalaCompileTask.source.files)
		}
	}

	/**
	 * @param scalaCompile scala task which params will be changed
	 * @param javaCompile source of params
	 */
	private setTaskParams(ScalaCompile scalaCompile, javaCompile) {
		scalaCompile.source = javaCompile.source
		scalaCompile.destinationDir = javaCompile.destinationDir
		scalaCompile.sourceCompatibility = javaCompile.sourceCompatibility
		scalaCompile.targetCompatibility = javaCompile.targetCompatibility
		scalaCompile.scalaCompileOptions.encoding = javaCompile.options.encoding
		scalaCompile.classpath = javaCompile.classpath +
				project.files(androidPlugin.androidBuilder.getBootClasspath(false))

		scalaCompile.scalaClasspath = configuration("scalaCompiler:${scalaCompile.name}",
				"org.scala-lang:scala-compiler:${extension.scalaVersion}").asFileTree

		scalaCompile.zincClasspath = configuration("zinc:${scalaCompile.name}",
				"com.typesafe.zinc:zinc:${extension.zincVersion}").asFileTree

		scalaCompile.scalaCompileOptions.incrementalOptions.analysisFile =
				new File(workSubdir(scalaCompile.name), "analysis.txt")
	}

	/**
	 * creates new configuration or return the existing
	 *
	 * @param name of the configuration
	 * @param dependency of the configuration
	 * @return project configuration
	 */
	private configuration(String name, String dependency) {
		def configuration = project.configurations.findByName(name)

		if (!configuration) {
			project.logger.info("Creating configuration $name with dependency '$dependency'")
			configuration = project.configurations.create(name)
			project.dependencies.add(name, dependency)
		}

		return configuration
	}

	/**
	 * copies dependencies of one task to another
	 * @param copied will have same dependeines as sample
	 * @param sample isn't modified
	 */
	private copyDependencies(AbstractTask copied, AbstractTask sample) {
		project.gradle.taskGraph.whenReady { graph ->
			for (task in graph.allTasks) {
				if (task.dependsOn.contains(sample))
					task.dependsOn(copied)
			}
		}

		copied.dependsOn = sample.dependsOn
	}
}

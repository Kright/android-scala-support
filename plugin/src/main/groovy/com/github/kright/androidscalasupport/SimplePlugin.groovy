package com.github.kright.androidscalasupport

import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.tasks.scala.ScalaCompile

/**
 * Created by lgor on 24.09.2016.
 */
class SimplePlugin implements Plugin<Project> {

	protected Project project;
	protected AndroidScalaExtension extension;

	protected File workDir
	protected androidExtension
	protected androidPlugin

	void apply(Project target) {
		this.project = target

		if (!project.plugins.hasPlugin("com.android.application"))
			throw new GradleException("You have to apply 'com.android.application' before!")
		androidPlugin = project.plugins.getPlugin('com.android.application')

		initWorkDir()
		createExtension()
		updateAndroidExtension()
		setScalaCompileTaskAdding()
	}

	private def initWorkDir() {
		workDir = new File(project.buildDir, "android-scala-support")
		if (workDir.exists()) {
			assert workDir.isDirectory()
			return
		}

		assert workDir.mkdirs()
	}

	private File workSubdir(String name) {
		return new File(workDir, name)
	}

	private def createExtension() {
		extension = project.extensions.create("androidScala", AndroidScalaExtension)
		extension.project = project
	}

	private def updateAndroidExtension() {
		androidExtension = project.extensions.findByName("android")
		assert androidExtension != null

		updateAndroidSourceSets()

		androidExtension.buildTypes.whenObjectAdded { updateAndroidSourceSets() }
		androidExtension.productFlavors.whenObjectAdded { updateAndroidSourceSetsExtension() }
		androidExtension.signingConfigs.whenObjectAdded { updateAndroidSourceSetsExtension() }
	}

	private def updateAndroidSourceSets() {
		androidExtension.sourceSets.each {
			it.java.filter.include("**/*.scala")
		}
	}

	private def setScalaCompileTaskAdding() {
		project.afterEvaluate {
			androidExtension.applicationVariants.all { addScalaCompile(it) }
			androidExtension.testVariants.all { addScalaCompile(it) }
			androidExtension.unitTestVariants.all { addScalaCompile(it) }
		}
	}

	private def addScalaCompile(variant) {
		def scalaCompileTaskName = "compileScala(${variant.name})"
		def javaCompileTask = variant.javaCompiler

		def scalaCompileTask = project.tasks.create(scalaCompileTaskName, ScalaCompile)
		project.logger.info("create task: ${scalaCompileTaskName}")

		setTaskParams(scalaCompileTask, javaCompileTask)
		copyDependencies(scalaCompileTask, javaCompileTask)

		javaCompileTask.enabled = false

		scalaCompileTask.doFirst {
			scalaCompileTask.source = new TreeSet(javaCompileTask.source.collect { it })
			// because R.java files will be generated and added by one of previous tasks
			project.logger.info("${scalaCompileTask} sources : " + scalaCompileTask.source.files)
		}
	}

	private def setTaskParams(ScalaCompile scalaCompile, javaCompile) {
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

	private def configuration(name, dependency) {
		def configuration = project.configurations.findByName(name)

		if (!configuration) {
			project.logger.info("Creating configuration $name with dependency '$dependency'")
			configuration = project.configurations.create(name)
			project.dependencies.add(name, dependency)
		}

		return configuration
	}

	private def copyDependencies(scalaCompile, javaCompile) {
		project.tasks.each {
			if (it.dependsOn.contains(javaCompile)) {
				it.dependsOn(scalaCompile)
			}
		}

		scalaCompile.dependsOn = javaCompile.dependsOn
		scalaCompile.dependsOn(javaCompile)
	}
}

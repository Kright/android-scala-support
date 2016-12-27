import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.TaskExecutionException
import org.gradle.internal.os.OperatingSystem

/**
 * for each folder task runs gradlew wrapper in separate process
 * passes gradleCommands as parameters
 * prints result
 * and throws TaskExecutionException if sub-process exited with value != 0 and failFast enabled
 */
class GradleCommandRunner extends DefaultTask {

	String description = "running commands for gradle projects"

	Boolean failFast = true
	ArrayList<String> gradleCommands = []
	File[] folders

	@TaskAction
	def runCommand() {
		folders.each { exampleDir ->
			assert exampleDir.exists()
			assert exampleDir.isDirectory()

			println("building example project '${exampleDir.name}' : ")

			def extension = ""
			if (OperatingSystem.current().isWindows()) extension = ".bat"
			def command = exampleDir.absolutePath + File.separator + "gradlew" + extension

			def processBuilder = new ProcessBuilder([command] + gradleCommands)
			processBuilder.directory(exampleDir)

			def process = processBuilder.start()
			process.waitFor()
			process.in.readLines().each { println("\t$it") }
			process.err.readLines().each { println("\t$it") }

			def success = process.exitValue() == 0

			println(success ? "success!" : "fail")
			if (failFast && !success) {
				throw new TaskExecutionException(this, new Exception("project example ${exampleDir.name} wasn't builded"))
			}
		}
	}
}

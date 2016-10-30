package com.github.kright.androidscalasupport

import org.gradle.api.DefaultTask
import org.gradle.api.tasks.TaskAction

/**
 * Created by lgor on 24.09.2016.
 */
class HelloTask extends DefaultTask{
    String text = 'Hello world'

    String description = "Prints text : \"${text}\""

    @TaskAction
    def greet(){
        println text
    }
}

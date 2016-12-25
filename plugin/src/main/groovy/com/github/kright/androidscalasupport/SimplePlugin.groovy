package com.github.kright.androidscalasupport

import org.gradle.api.Plugin
import org.gradle.api.Project
import com.android.build.gradle.AppPlugin

/**
 * Created by lgor on 24.09.2016.
 */
class SimplePlugin implements Plugin<Project> {

    protected Project project;
    protected AppPlugin plugin;

    void apply(Project target) {
        this.project = target;

        project.extensions.create("androidScala", AndroidScalaExtension)
        project.extensions.androidScala.project = target

        if (!project.plugins.hasPlugin("com.android.application")) {
            project.logger.warn("You have to apply 'com.android.application' before!");
        }
    }
}

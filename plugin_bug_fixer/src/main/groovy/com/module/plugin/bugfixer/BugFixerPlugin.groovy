package com.module.plugin.bugfixer

import org.gradle.api.Plugin
import org.gradle.api.Project

class BugFixerPlugin implements Plugin<Project>{

    static boolean isDebug = false;

    @Override
    void apply(Project project) {
        println("--------BugFixerPlugin apply start--------")
        project.android.registerTransform(new BugFixerTransform(project))
        println("--------BugFixerPlugin apply end--------")
    }
}
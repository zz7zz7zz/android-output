package com.module.plugin.bugfixer

import org.gradle.api.Plugin
import org.gradle.api.Project

class BugFixerPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        print("--------BugFixerPlugin apply start--------")
        project.android.registerTransform(new BugFixerTransform(project))
        print("--------BugFixerPlugin apply end--------")
    }
}
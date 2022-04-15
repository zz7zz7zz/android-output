package com.module.plugin.bugfixer

import org.gradle.api.Plugin
import org.gradle.api.Project

class BugFixerPlugin implements Plugin<Project>{

    static boolean isDebug = true;

    @Override
    void apply(Project project) {
        if(BugFixerPlugin.isDebug)
        print("--------BugFixerPlugin apply start--------")

        project.android.registerTransform(new BugFixerTransform(project))

        if(BugFixerPlugin.isDebug)
        print("--------BugFixerPlugin apply end--------")
    }
}
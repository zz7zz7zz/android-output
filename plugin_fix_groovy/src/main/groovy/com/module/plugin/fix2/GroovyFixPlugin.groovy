package com.module.plugin.fix2

import org.gradle.api.Plugin
import org.gradle.api.Project

class GroovyFixPlugin implements Plugin<Project>{

    @Override
    void apply(Project project) {
        print("--------GroovyFixPlugin--------")
        project.android.registerTransform(new Fix2Transform(project))
    }
}
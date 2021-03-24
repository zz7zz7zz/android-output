package com.module.plugin.fix;

import com.android.build.gradle.AppExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.ExtensionContainer;

public class FixPlugin implements Plugin<Project> {

    public static void main(){

        System.out.println("------------- I am from FixPlugin -------------");
    }

    @Override
    public void apply(Project target) {
        System.out.println("------------- I am from FixPlugin apply -------------");

        ExtensionContainer extensionContainer =  target.getExtensions();
        AppExtension appExtension = (AppExtension) extensionContainer.getByName("android");

        appExtension.registerTransform(new FixTransform());

    }
}
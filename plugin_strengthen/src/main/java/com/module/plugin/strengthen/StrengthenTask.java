package com.module.plugin.strengthen;

import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.tasks.TaskAction;
import org.gradle.process.ExecSpec;

import java.io.File;

import javax.inject.Inject;

public class StrengthenTask extends DefaultTask {

    private File apkFile;

    private StrengthenExit strengthenExit;

    @Inject
    public StrengthenTask(File apkFile, StrengthenExit strengthenExit) {
        this.apkFile = apkFile;
        this.strengthenExit = strengthenExit;
        setGroup("strengthen");
    }

    @TaskAction
    public void execute(){
        System.out.println("---------------Strengthen Start---------------");

//        //执行加固流程
//        getProject().exec(new Action<ExecSpec>() {
//            @Override
//            public void execute(ExecSpec execSpec) {
//                execSpec.commandLine("java","-jar","360jiagu.jar","-login",strengthenExit.getUserName(),strengthenExit.getPassWord());
//            }
//        });
//
//        getProject().exec(new Action<ExecSpec>() {
//            @Override
//            public void execute(ExecSpec execSpec) {
//                execSpec.commandLine("java","-jar","360jiagu.jar","-importsign");
//            }
//        });
//
//        getProject().exec(new Action<ExecSpec>() {
//            @Override
//            public void execute(ExecSpec execSpec) {
//                execSpec.commandLine("java","-jar","360jiagu.jar","-jiagu","","-autosign");
//            }
//        });

        System.out.println("---------------Strengthen End---------------");
    }
}

package com.module.plugin.strengthen;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;
import com.android.build.gradle.api.BaseVariantOutput;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

import java.io.File;

public class StrengthenPlugin implements Plugin<Project> {

    @Override
    public void apply(Project target) {
        final StrengthenExit mStrengthenExit = target.getExtensions().create("strengthen",StrengthenExit.class);

        //在gradle 分析完build.gradle文件后才进行回调
        target.afterEvaluate(new Action<Project>() {
            @Override
            public void execute(final Project project) {
                System.out.println(mStrengthenExit.toString());

                //动态获取当前debug/release.apk文件
                AppExtension android = project.getExtensions().getByType(AppExtension.class);
                android.getApplicationVariants().all(new Action<ApplicationVariant>() {
                    @Override
                    public void execute(ApplicationVariant applicationVariant) {
                        //debug /release
                        final String name = applicationVariant.getName();
                        System.out.println(applicationVariant.toString());

                        applicationVariant.getOutputs().all(new Action<BaseVariantOutput>() {
                            @Override
                            public void execute(BaseVariantOutput baseVariantOutput) {
                                    File apkFile =  baseVariantOutput.getOutputFile();

                                    System.out.println("StrengthenPlugin apkName "+apkFile.getName());

                                    project.getTasks().create("strengthen_"+name,StrengthenTask.class,apkFile,mStrengthenExit);
                            }
                        });
                    }
                });
            }
        });
    }

}
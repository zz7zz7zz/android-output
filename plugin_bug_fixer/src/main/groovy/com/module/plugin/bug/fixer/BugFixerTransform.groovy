package com.module.plugin.bug.fixer

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import org.gradle.api.Project

class BugFixerTransform extends Transform {

    def project

    def pool = ClassPool.default

    BugFixerTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "BugFixerTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation)

        transformInvocation.inputs.each {
            it.jarInputs.each {

                pool.insertClassPath(it.file.absolutePath)

                def dest = transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.JAR)
                FileUtils.copyFile(it.file,dest)
            }

            it.directoryInputs.each {

                pool.insertClassPath(directoryInput.file.absolutePath)

                def dest = transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.DIRECTORY)
                FileUtils.copyDirectory(it.file,dest)
            }
        }
    }

}

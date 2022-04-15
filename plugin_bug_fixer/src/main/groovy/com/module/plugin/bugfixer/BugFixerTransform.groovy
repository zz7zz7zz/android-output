package com.module.plugin.bugfixer

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import com.module.plugin.bugfixer.bugs.Bug80InTxOauthSdk
import com.module.plugin.bugfixer.bugs.BugScanService
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import org.apache.commons.codec.digest.DigestUtils
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

        BugScanService.scanServiceAndServiceImpl(transformInvocation)

        boolean leftSlash = File.separator == '/'
        transformInvocation.inputs.each {
            it.jarInputs.each { JarInput jarInput ->

                pool.insertClassPath(jarInput.file.absolutePath)
//                println("ScanService jarInputs------------ " + jarInput.file.absolutePath)

                String destName = jarInput.name
                // rename jar files
                def hexName = DigestUtils.md5Hex(jarInput.file.absolutePath)
                if (destName.endsWith(".jar")) {
                    destName = destName.substring(0, destName.length() - 4)
                }
                // input file
                File src = jarInput.file
                // output file
                File dest = transformInvocation.outputProvider.getContentLocation(destName + "_" + hexName, jarInput.contentTypes, jarInput.scopes, Format.JAR)


                FileUtils.copyFile(src,dest)
            }

            it.directoryInputs.each { DirectoryInput directoryInput ->

                pool.insertClassPath(directoryInput.file.absolutePath)

                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)
                String root = directoryInput.file.absolutePath
                if (!root.endsWith(File.separator))
                    root += File.separator
                directoryInput.file.eachFileRecurse { File file ->
                    def path = file.absolutePath.replace(root, '')
                    if (!leftSlash) {
                        path = path.replaceAll("\\\\", "/")
                    }
                }

                // copy to dest
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }

        //修复bug
        Bug80InTxOauthSdk.insertInitCodeTo()
    }

}

package com.module.plugin.bugfixer.bugs

import com.android.build.api.transform.DirectoryInput
import com.android.build.api.transform.Format
import com.android.build.api.transform.JarInput
import com.android.build.api.transform.TransformInvocation
import org.apache.commons.codec.digest.DigestUtils

import java.util.jar.JarEntry
import java.util.jar.JarFile

class BugScanService {

    static final String TAG = "BugScanServiceScanService"


    static void scanServiceAndServiceImpl(TransformInvocation transformInvocation){
        println(TAG+"---------------- scanServiceAndServiceImpl start----------------")
        long startTime = System.currentTimeMillis()
        //        transformInvocation.outputProvider.deleteAll()
        boolean leftSlash = File.separator == '/'
        transformInvocation.inputs.each {
            it.jarInputs.each { JarInput jarInput ->

//                println("BugScanService jarInputs------------ " + jarInput.file.absolutePath)

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

                //scan jar file to find classes
                if (Bug80InTxOauthSdk.shouldProcessPreDexJar(src.absolutePath)) {
                    Bug80InTxOauthSdk.scanJar(src, dest)
                }
            }

            it.directoryInputs.each { DirectoryInput directoryInput ->

//                println("BugScanService directoryInput------------ " + directoryInput.file.absolutePath)

                File dest = transformInvocation.outputProvider.getContentLocation(directoryInput.name, directoryInput.contentTypes, directoryInput.scopes, Format.DIRECTORY)

            }
        }

        println(TAG+"---------------- initCodeToClassFile ----------------" + (null != Bug80InTxOauthSdk.initCodeToJarClassFile ? Bug80InTxOauthSdk.initCodeToJarClassFile : null))
        println(TAG+"---------------- ScanService scanServiceAndServiceImpl end ----------------"  + (System.currentTimeMillis() - startTime) + "ms")
    }


}

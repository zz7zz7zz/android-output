package com.module.plugin.bugfixer.bugs

import org.apache.commons.io.IOUtils
import org.objectweb.asm.*

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

class Bug80InTxOauthSdk {

    static final String TAG = "Bug80InTxOauthSdk"
    static final String jarNameTag = "app_bug_8_0"
//    static ArrayList<String> jarTag = new ArrayList<>()
//    static {
//        jarTag.add("com.cmic.sso.sdk.tencent.view.LoginAuthActivity.class".replace(".","/"))
//        jarTag.add("com.tencent.tendinsv.view.CmccLoginActivity.class".replace(".","/"))
//        jarTag.add("com.tencent.tendinsv.view.NSVOneKeyActivity.class".replace(".","/"))
//    }
//
//    static ArrayList<String> modifyClass = new ArrayList<>()
//    static {
//        modifyClass.add("com.tencent.tendinsv.view.CmccLoginActivity.class".replace(".","/"))
//        modifyClass.add("com.tencent.tendinsv.view.NSVOneKeyActivity.class".replace(".","/"))
//    }

    static ArrayList<String> jarClassTag = new ArrayList<>()
    static {
        jarClassTag.add("com.example.app_bug_8_0.Bug80Activity".replace(".","/") +".class")
    }

    static ArrayList<String> modifyClass = new ArrayList<>()
    static {
        modifyClass.add("com.example.app_bug_8_0.Bug80Activity".replace(".","/") +".class")
    }

    static String initCodeToJarClassFile


    private Bug80InTxOauthSdk() {
    }

    static boolean shouldProcessPreDexJar(String path) {
//        println(TAG+"----------shouldProcessPreDexJar------------ " + path)
        return path.contains(jarNameTag)
    }

    static void scanJar(File jarFile, File destFile) {
        println(TAG+"----------scanJar------------ " + jarFile.absolutePath)
        if (jarFile) {
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                println(TAG+"----------scanJar shouldProcessClass ------------ " + (entryName))

                for (int i=0; i<Bug80InTxOauthSdk.jarClassTag.size(); i++){
                    if(Bug80InTxOauthSdk.jarClassTag.get(i) == entryName){
                        Bug80InTxOauthSdk.initCodeToJarClassFile= destFile
                        println(TAG+"----------Bug80InTxOauthSdk.initCodeToClassFile------------ " + Bug80InTxOauthSdk.initCodeToJarClassFile)
                        break
                    }
                }
            }
            file.close()
        }
    }


    static void insertInitCodeTo() {
        if (null != initCodeToJarClassFile && initCodeToJarClassFile.endsWith('.jar')){
            Bug80InTxOauthSdk bug80InTxOauthSdk = new Bug80InTxOauthSdk()
            bug80InTxOauthSdk.insertInitCodeIntoJarFile(new File(initCodeToJarClassFile))
        }
    }

    private File insertInitCodeIntoJarFile(File jarFile) {
        if (jarFile) {
            def optJar = new File(jarFile.getParent(), jarFile.name + ".opt")
            if (optJar.exists())
                optJar.delete()
            def file = new JarFile(jarFile)
            Enumeration enumeration = file.entries()
            JarOutputStream jarOutputStream = new JarOutputStream(new FileOutputStream(optJar))

            while (enumeration.hasMoreElements()) {
                JarEntry jarEntry = (JarEntry) enumeration.nextElement()
                String entryName = jarEntry.getName()
                ZipEntry zipEntry = new ZipEntry(entryName)
                InputStream inputStream = file.getInputStream(jarEntry)
                jarOutputStream.putNextEntry(zipEntry)

                boolean isModifySuccess = false
                for (int i=0;i<modifyClass.size();i++){
                    if(modifyClass.get(i) == entryName){
                        def bytes = referHackWhenInit(inputStream)
                        jarOutputStream.write(bytes)
                        isModifySuccess = true
                        break;
                    }
                }

                println(TAG+'--------------RegisterCodeGenerator Insert init code to class >> ' + entryName + " isModifySuccess " + isModifySuccess)
                if(!isModifySuccess){
                    jarOutputStream.write(IOUtils.toByteArray(inputStream))
                }

                inputStream.close()
                jarOutputStream.closeEntry()
            }
            jarOutputStream.close()
            file.close()

            if (jarFile.exists()) {
                jarFile.delete()
            }
            optJar.renameTo(jarFile)
        }
        return jarFile
    }

    //refer hack class when object init
    private byte[] referHackWhenInit(InputStream inputStream) {
        ClassReader cr = new ClassReader(inputStream)
        ClassWriter cw = new ClassWriter(cr, 0)
        ClassVisitor cv = new MyClassVisitor(Opcodes.ASM5, cw)
        cr.accept(cv, ClassReader.EXPAND_FRAMES)
        return cw.toByteArray()
    }

    class MyClassVisitor extends ClassVisitor {

        MyClassVisitor(int api, ClassVisitor cv) {
            super(api, cv)
        }

        void visit(int version, int access, String name, String signature,
                   String superName, String[] interfaces) {
            super.visit(version, access, name, signature, superName, interfaces)
        }
        @Override
        MethodVisitor visitMethod(int access, String name, String desc,
                                  String signature, String[] exceptions) {
            MethodVisitor mv = super.visitMethod(access, name, desc, signature, exceptions)
            //generate code into this method
            if (name == "onCreate" && desc.equals("(Landroid/os/Bundle;)V")) {
                mv = new FixMethodVisitor(Opcodes.ASM5, mv)
            }
            return mv
        }
    }

    class FixMethodVisitor extends MethodVisitor {

        FixMethodVisitor(int api, MethodVisitor mv) {
            super(api, mv)
        }

        @Override
        void visitCode() {
            //在代码开始对地方插入代码SystemBugFixUtil.resolveTranslucentorOrientation_O(this);
            mv.visitVarInsn(Opcodes.ALOAD,0)
            mv.visitMethodInsn(Opcodes.INVOKESTATIC,"com/lib/util/SystemBugFixUtil","resolveTranslucentorOrientation_O","(Landroid/content/Context;)V",false)
            super.visitCode()
        }

        @Override
        void visitMaxs(int maxStack, int maxLocals) {
            super.visitMaxs(maxStack + 4, maxLocals)
        }
    }
}

package com.module.plugin.fix2

import com.android.build.api.transform.*
import com.android.build.gradle.internal.pipeline.TransformManager
import com.android.utils.FileUtils
import javassist.ClassPool
import javassist.CtClass
import javassist.CtMethod
import javassist.Modifier
import org.gradle.api.Project

class Fix2Transform extends Transform {

    def project

    def pool = ClassPool.default

    Fix2Transform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "Fix2Transform"
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

                //project.android.bootClasspath 加入android.jar，不然找不到android相关的所有类
                pool.appendClassPath(project.android.bootClasspath[0].toString());
                //引入android.os.Bundle包，因为onCreate方法参数有Bundle
                pool.importPackage("android.os.Bundle");

                def preFileName = it.file.absolutePath
println("-----preFileName----- " + preFileName)
                pool.insertClassPath(preFileName)
                findTarget(it.file,preFileName)

                def dest = transformInvocation.outputProvider.getContentLocation(it.name,it.contentTypes,it.scopes,Format.DIRECTORY)
                FileUtils.copyDirectory(it.file,dest)
            }
        }
    }

    void findTarget(File dir,String fileName){
        if(dir.isDirectory()){
            dir.listFiles().each {
                findTarget(it,fileName)
            }
        }else{
            modify(dir,fileName)
        }
    }

    void modify(File dir,String fileName){

        def filePath = dir.absolutePath
        if(!filePath.endsWith(".class")){
            return
        }

        if(filePath.contains('R$') || filePath.contains('R.class') || filePath.contains('BuildConfig.class')){
            return
        }


        def className = filePath.replace(fileName,"")
                .replace("\\",".")
                .replace("/",".")
        def name = className.replace(".class","").substring(1)

println("-----start----- ")
println("filePath " + filePath)
println("fileName " + fileName)
println("name " + name)
println("-----end----- ")

        if(name.contains("com.open.test")){
            //修改类，插入代码
            CtClass ctClass = pool.get(name)

            addCode(ctClass,fileName)

        }

    }

    void addCode(CtClass ctClass,String fileName){
        //报异常：javassist.CannotCompileException: no method body

        if(ctClass.isInterface()){
            return
        }

        if(ctClass.getName().contains("PatchProxy")){
            return
        }

println("class "+ctClass)
println("className "+ctClass.getName())

        CtMethod[] ctMethods = ctClass.getDeclaredMethods()
        for (method in ctMethods){

            //空函数或者抽象函数
            if(method.isEmpty()){
                continue
            }

            //Native方法
            if (Modifier.isNative(method.getModifiers())){
                continue
            }

//            //修复FixNullException中的空指针问题，这里需要判断类名，方法名，才可以加这段逻辑
//            if(method.getName().equals("isSuccess")){
//                method.insertBefore("if (null == o) {\n" +
//                        "System.out.println(\"I am fixed NullPointException\");\n" +
//                        "return false;\n" +
//                        "}\n")
//                continue
//            }

//            def body= "if (com.open.test.aop.PatchProxy.isSupport()) {\n" +
//                    "System.out.println(\"I am fixed \");\n" +
//                    "}"

//            method.addLocalVariable("start", CtClass.longType);
//            method.insertBefore("start = System.currentTimeMillis();");
//            method.insertAfter("System.out.println(\"exec time is :\" + (System.currentTimeMillis() - start) + \"ms\");");

//            if(method.getName() == "onCreate"){
                method.addLocalVariable("start", CtClass.longType);
                method.insertBefore("start = System.currentTimeMillis();\n")
//            }

            method.insertBefore("if (com.open.test.aop.PatchProxy.isSupport()) {\n" +
                    "System.out.println(\"I am fixed \");\n" +
                    "}\n")

//            if(method.getName() == "onCreate"){
//                method.insertAfter(String.format("System.out.println(\"%s %s() cost \" + (System.currentTimeMillis() - start) + \" ms\");\n",ctClass.getName(),method.getName()))
                method.insertAfter("if((System.currentTimeMillis() - start) > 16){\n"+
                        String.format("android.util.Log.e(\"MethodTracking\",\"%s %s() cost \" + (System.currentTimeMillis() - start) + \" ms\");\n",ctClass.getName(),method.getName())+
                    "}\n")
//            }

        }

        ctClass.writeFile(fileName)
        ctClass.detach()
    }

}

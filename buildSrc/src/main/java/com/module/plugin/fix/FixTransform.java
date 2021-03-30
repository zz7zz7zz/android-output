package com.module.plugin.fix;

import com.android.build.api.transform.DirectoryInput;
import com.android.build.api.transform.Format;
import com.android.build.api.transform.JarInput;
import com.android.build.api.transform.QualifiedContent;
import com.android.build.api.transform.Transform;
import com.android.build.api.transform.TransformException;
import com.android.build.api.transform.TransformInput;
import com.android.build.api.transform.TransformInvocation;
import com.android.build.api.transform.TransformOutputProvider;
import com.android.build.gradle.internal.pipeline.TransformManager;
import com.android.utils.FileUtils;
import com.module.plugin.fix.util.FileUtil;
import com.module.plugin.fix.util.MD5;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Opcodes;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;


class FixTransform extends Transform {
    @Override
    public String getName() {
        return "FixTransform";
    }

    @Override
    public Set<QualifiedContent.ContentType> getInputTypes() {
        return TransformManager.CONTENT_CLASS;
    }

    @Override
    public Set<? super QualifiedContent.Scope> getScopes() {
        return TransformManager.SCOPE_FULL_PROJECT;
    }

    @Override
    public boolean isIncremental() {
        return false;
    }

    @Override
    public void transform(TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {
        super.transform(transformInvocation);

        //清理文件
        TransformOutputProvider transformOutputProvider = transformInvocation.getOutputProvider();
        transformOutputProvider.deleteAll();


        //得到所有的输入
        Collection<TransformInput> transformInputs =  transformInvocation.getInputs();
        for (TransformInput input:transformInputs) {

            //获取所有的依赖jar包
            Collection<JarInput> jarInputs = input.getJarInputs();
            for (JarInput jarInput: jarInputs) {

                System.out.println("FixTransform JarInput " + jarInput.getName());

                File src = jarInput.getFile();
                String destName = jarInput.getName()+"_"+MD5.md5(src.getAbsolutePath());
//                System.out.println("FixTransform JarInput srcName " + jarInput.getName());
//                System.out.println("FixTransform JarInput dstName " + destName);
                File dest = transformOutputProvider.getContentLocation(destName,jarInput.getContentTypes(), jarInput.getScopes(), Format.JAR);
                FileUtils.copyFile(src, dest);
            }

            //获取字节的代码的class
            Collection<DirectoryInput> directoryInputs = input.getDirectoryInputs();
            for (DirectoryInput directoryInput: directoryInputs) {

                System.out.println("FixTransform DirectoryInput " + directoryInput.getFile().getAbsolutePath());

                File src = directoryInput.getFile();
                String destName = directoryInput.getName()+"_"+MD5.md5(src.getAbsolutePath());
                System.out.println("FixTransform DirectoryInput srcName " + src);
                System.out.println("FixTransform DirectoryInput dstName " + destName);

                //--------------------修复bug start --------------------
                {
                    String[] path = {"com","open","test","aop","AsmFixActivity.class"};
                    File file =src;
                    for (int i = 0;i<path.length;i++){
                        final String acceptString = path[i];
                        File[] files = file.listFiles(new FileFilter() {
                            @Override
                            public boolean accept(File file) {
                                return file.getName().equals(acceptString);
                            }
                        });
                        for (File f:files) {
                            System.out.println("FixTransform file " + f.getPath());
                        }
                        file = files.length > 0 ? files[0] : null;
                        if(null == file){
                            break;
                        }
                    }

                    if(null != file && file.isFile()){
                        System.out.println("FixTransform fix start ");

                        byte[] bytes = FileUtil.readFile(file.getPath());
                        ClassReader cr = new ClassReader(bytes);
                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                        cr.accept(new FixBugClassVisitor(Opcodes.ASM5,cw),0);
                        byte[] newClassBytes = cw.toByteArray();
                        FileUtil.delete(file);
                        FileUtil.writeFile(file.getPath(), newClassBytes);

                        System.out.println("FixTransform fix end ");
                    }
                }
                //--------------------修复bug end --------------------

                //--------------------方法前后添加代码 start --------------------
                {
                    String[] path = {"com","open","test","aop","JavassistAddCodeActivity.class"};
                    File file =src;
                    for (int i = 0;i<path.length;i++){
                        final String acceptString = path[i];
                        File[] files = file.listFiles(new FileFilter() {
                            @Override
                            public boolean accept(File file) {
                                return file.getName().equals(acceptString);
                            }
                        });
                        for (File f:files) {
                            System.out.println("FixTransform file " + f.getPath());
                        }
                        file = files.length > 0 ? files[0] : null;
                        if(null == file){
                            break;
                        }
                    }

                    if(null != file && file.isFile()) {
                        System.out.println("FixTransform addCode start ");

                        byte[] bytes = FileUtil.readFile(file.getPath());
                        ClassReader cr = new ClassReader(bytes);
                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

                        cr.accept(new AddCodeClassVisitor(Opcodes.ASM5,cw),0);
                        byte[] newClassBytes = cw.toByteArray();
                        FileUtil.delete(file);
                        FileUtil.writeFile(file.getPath(), newClassBytes);

                        System.out.println("FixTransform addCode end ");
                    }
                }
                //--------------------方法前后添加代码 end --------------------


                //--------------------调用祖父类方法 start --------------------
//                {
//                    String[] path = {"com","open","test","callgrandfathermethod","CallGrandFatherMethodActivity$Son.class"};
//                    File file =src;
//                    for (int i = 0;i<path.length;i++){
//                        final String acceptString = path[i];
//                        File[] files = file.listFiles(new FileFilter() {
//                            @Override
//                            public boolean accept(File file) {
//                                return file.getName().equals(acceptString);
//                            }
//                        });
//                        for (File f:files) {
//                            System.out.println("FixTransform file " + f.getPath());
//                        }
//                        file = files.length > 0 ? files[0] : null;
//                        if(null == file){
//                            break;
//                        }
//                    }
//
//                    if(null != file && file.isFile()) {
//                        System.out.println("FixTransform callGrandFatherMethod start ");
//
//                        byte[] bytes = FileUtil.readFile(file.getPath());
//                        ClassReader cr = new ClassReader(bytes);
//                        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
//
//                        cr.accept(new CallGrandFatherMethodVisitor(Opcodes.ASM5,cw),0);
//                        byte[] newClassBytes = cw.toByteArray();
//                        FileUtil.delete(file);
//                        FileUtil.writeFile(file.getPath(), newClassBytes);
//
//                        System.out.println("FixTransform callGrandFatherMethod end ");
//                    }
//                }
                //--------------------调用祖父类方法 end --------------------

                File dest = transformOutputProvider.getContentLocation(destName,directoryInput.getContentTypes(), directoryInput.getScopes(), Format.DIRECTORY);
                FileUtil.copyDirectory(src,dest);
            }

        }
    }


}

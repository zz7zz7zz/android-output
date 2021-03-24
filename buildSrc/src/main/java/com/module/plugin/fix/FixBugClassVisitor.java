package com.module.plugin.fix;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;

class FixBugClassVisitor extends ClassVisitor {

    public FixBugClassVisitor(int api) {
        super(api);
    }

    public FixBugClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(name.equals("onCreate") && descriptor.equals("(Landroid/os/Bundle;)V")){
            System.out.println("FixBugClassVisitor --------- visitMethod ---------");
            return new FixMethod(api,mv);
        }

        return mv;
    }

    static final class FixMethod extends MethodVisitor {

        public FixMethod(int api) {
            super(api);
        }

        public FixMethod(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
//            if(name.equals("show")){
//                System.out.println(" --------- visitMethodInsn ---------");
//                return;
//            }
            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        @Override
        public void visitLdcInsn(Object value) {

            if("我是错误的代码".equals(value)){
                System.out.println("FixBugClassVisitor --------- visitLdcInsn ---------" + value);
                super.visitLdcInsn("我是正确的代码，100%正确");
                return;
            }
            super.visitLdcInsn(value);
        }
    }
}

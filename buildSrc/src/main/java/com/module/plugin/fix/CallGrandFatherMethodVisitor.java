package com.module.plugin.fix;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class CallGrandFatherMethodVisitor extends ClassVisitor {

    public CallGrandFatherMethodVisitor(int api) {
        super(api);
    }

    public CallGrandFatherMethodVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }


    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println("CallGrandFatherMethodVisitor visitMethod "+String.format("%d %s %s %s",access,name,descriptor,signature));

        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(name.equals("do2") && descriptor.equals("()V")){
            System.out.println("CallGrandFatherMethodVisitor visitMethod ------------------");
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
            System.out.println("visitMethodInsn "+String.format("%d %s %s %s %b",opcode,owner,name,descriptor,isInterface));

            //4.调用父类方法
            if("do2".equals(name)){

                System.out.println("------------modifying----------");


                //invoke-super {p0}, Lcom/open/test/callgrandfathermethod/CallGrandFatherMethodActivity$Father;->do2()V

//                mv.visitFieldInsn();
//                mv.visitLdcInsn();

                mv.visitMethodInsn(Opcodes.INVOKESPECIAL,
                        "com/open/test/callgrandfathermethod/CallGrandFatherMethodActivity$GrandFather",
                        "do2",
                        "()V");
                return;
            }

            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
        }

        @Override
        public void visitFieldInsn(int opcode, String owner, String name, String descriptor) {
            System.out.println("visitFieldInsn "+String.format("%d %s %s %s",opcode,owner,name,descriptor));
            super.visitFieldInsn(opcode, owner, name, descriptor);
        }

        @Override
        public void visitLdcInsn(Object value) {
            System.out.println("visitLdcInsn --------- visitLdcInsn ---------" + value);
            super.visitLdcInsn(value);
        }
    }
}

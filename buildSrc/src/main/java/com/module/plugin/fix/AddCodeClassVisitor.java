package com.module.plugin.fix;

import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.FieldVisitor;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

class AddCodeClassVisitor extends ClassVisitor {

    public AddCodeClassVisitor(int api) {
        super(api);
    }

    public AddCodeClassVisitor(int api, ClassVisitor classVisitor) {
        super(api, classVisitor);
    }

    @Override
    public FieldVisitor visitField(int access, String name, String descriptor, String signature, Object value) {
        System.out.println(" --------- AddCodeClassVisitor FieldVisitor ---------" + name);
        return super.visitField(access, name, descriptor, signature, value);
    }

    @Override
    public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
        System.out.println(" --------- AddCodeClassVisitor visitMethod ---------" + name);
        MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
        if(name.equals("doSomething") && descriptor.equals("()V")){
            System.out.println(" --------- AddCodeClassVisitor visitMethod doSomething ---------");
            return new FixMethod(api,mv);
        }

        return mv;
    }

    //work correct
    static final class FixMethod extends MethodVisitor {

        public FixMethod(int api) {
            super(api);
        }

        public FixMethod(int api, MethodVisitor methodVisitor) {
            super(api, methodVisitor);
        }

        @Override
        public void visitCode() {
            System.out.println(" --------- AddCodeClassVisitor visitCode ---------");
            mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
            mv.visitLdcInsn("doSomething ------- start -------");
            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
            super.visitCode();
        }

        @Override
        public void visitInsn(int opcode) {
            System.out.println(" --------- AddCodeClassVisitor visitMethodInsn ---------opcode " + opcode);
            if(opcode == Opcodes.ARETURN || opcode == Opcodes.RETURN ) {
                mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
                mv.visitLdcInsn("doSomething ------- end -------");
                mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
            }
            super.visitInsn(opcode);
        }

        @Override
        public void visitEnd() {
            System.out.println(" --------- AddCodeClassVisitor visitEnd ---------");
//            mv.visitMaxs(6,6);
            super.visitEnd();
        }
    }

//    static final class FixMethod extends MethodVisitor {
//
//        public FixMethod(int api) {
//            super(api);
//        }
//
//        public FixMethod(int api, MethodVisitor methodVisitor) {
//            super(api, methodVisitor);
//        }
//
//        @Override
//        public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
//
//            System.out.println(" --------- AddCodeClassVisitor visitMethodInsn --------- opcode " + opcode);
//
//            mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
//            mv.visitLdcInsn("doSomething ------- start -------");
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
//            mv.visitInsn(Opcodes.POP);
//
//            super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
//
//            mv.visitFieldInsn(Opcodes.GETSTATIC,"java/lang/System","out","Ljava/io/PrintStream;");
//            mv.visitLdcInsn("doSomething ------- end -------");
//            mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL,"java/io/PrintStream","println","(Ljava/lang/String;)V",false);
//            mv.visitInsn(Opcodes.POP);
//        }
//
//    }
}

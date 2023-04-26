package p1.transformers;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.sourcegrade.jagr.api.testing.ClassTransformer;

public class MethodInterceptorTransformer implements ClassTransformer {

    private static final String ASSIGNMENT_ID = "p1";

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void transform(ClassReader reader, ClassWriter writer) {
        reader.accept(new CV(writer), ClassReader.EXPAND_FRAMES);
    }

    @Override
    public int getWriterFlags() {
        return ClassWriter.COMPUTE_MAXS | ClassWriter.COMPUTE_FRAMES;
    }

    private static class CV extends ClassVisitor {

        public CV(ClassWriter classWriter) {
            super(Opcodes.ASM9, classWriter);
        }

        @Override
        public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
            return new MethodVisitor(Opcodes.ASM9, super.visitMethod(access, name, descriptor, signature, exceptions)) {

                @Override
                public void visitMethodInsn(int opcode, String owner, String name, String descriptor, boolean isInterface) {
                    String signature = "%s %s%s".formatted(owner, name, descriptor);
                    if (opcode == Opcodes.INVOKEVIRTUAL || opcode == Opcodes.INVOKEINTERFACE|| opcode == Opcodes.INVOKEDYNAMIC) {

                        injectMethodInterceptorCall(this, signature);
                    } else if (opcode == Opcodes.INVOKESTATIC && !signature.equals("%s/transformers/MethodInterceptor ".formatted(ASSIGNMENT_ID)
                        + "addInvocation(Lp1/transformers/MethodInterceptor$Invocation;)V")) {

                        injectMethodInterceptorCall(this, signature);
                    }

                    //TODO add invokeSpecial

                    // original method invocation
                    super.visitMethodInsn(opcode, owner, name, descriptor, isInterface);
                }
            };
        }

        @Override
        public void visitSource(String source, String debug) {
            super.visitSource(source, debug);
        }
    }

    private static void injectMethodInterceptorCall(MethodVisitor visitor, String signature) {

        // create new objectref for a MethodInterceptor$Invocation object
        visitor.visitTypeInsn(Opcodes.NEW, "%s/transformers/MethodInterceptor$Invocation".formatted(ASSIGNMENT_ID));
        visitor.visitInsn(Opcodes.DUP);

        // load parameters for MethodInterceptor$Invocation
        visitor.visitLdcInsn(signature); // push signature onto stack
        visitor.visitInsn(Opcodes.ACONST_NULL);
        visitor.visitInsn(Opcodes.ICONST_0);
        visitor.visitTypeInsn(Opcodes.ANEWARRAY, "java/lang/Object"); // create new arrayref

        // initialize MethodInterceptor$Invocation object
        visitor.visitMethodInsn(Opcodes.INVOKESPECIAL,
            "%s/transformers/MethodInterceptor$Invocation".formatted(ASSIGNMENT_ID),
            "<init>",
            "(Ljava/lang/String;Ljava/lang/Object;[Ljava/lang/Object;)V",
            false);

        // add invocation to list of invocations
        visitor.visitMethodInsn(Opcodes.INVOKESTATIC,
            "%s/transformers/MethodInterceptor".formatted(ASSIGNMENT_ID),
            "addInvocation",
            "(L%s/transformers/MethodInterceptor$Invocation;)V".formatted(ASSIGNMENT_ID),
            false);
    }

}

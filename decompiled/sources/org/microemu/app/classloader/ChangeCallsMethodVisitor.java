package org.microemu.app.classloader;

import java.util.HashMap;
import org.microemu.Injected;
import org.microemu.app.util.MIDletThread;
import org.microemu.app.util.MIDletTimer;
import org.microemu.app.util.MIDletTimerTask;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodAdapter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

/* loaded from: avacs.jar:org/microemu/app/classloader/ChangeCallsMethodVisitor.class */
public class ChangeCallsMethodVisitor extends MethodAdapter implements Opcodes {
    private static final String INJECTED_CLASS = codeName(Injected.class);
    static String NEW_SYSTEM_OUT_CLASS = INJECTED_CLASS;
    static String NEW_SYSTEM_PROPERTIES_CLASS = INJECTED_CLASS;
    static String NEW_RESOURCE_LOADER_CLASS = INJECTED_CLASS;
    private HashMap catchInfo;
    private InstrumentationConfig config;

    /* loaded from: avacs.jar:org/microemu/app/classloader/ChangeCallsMethodVisitor$CatchInformation.class */
    private static class CatchInformation {
        Label label = new Label();
        String type;

        public CatchInformation(String type) {
            this.type = type;
        }
    }

    public ChangeCallsMethodVisitor(MethodVisitor mv, InstrumentationConfig config) {
        super(mv);
        this.config = config;
    }

    public static String codeName(Class klass) {
        return klass.getName().replace('.', '/');
    }

    @Override // org.objectweb.asm.MethodAdapter, org.objectweb.asm.MethodVisitor
    public void visitFieldInsn(int opcode, String owner, String name, String desc) {
        switch (opcode) {
            case Opcodes.GETSTATIC /* 178 */:
                if (name.equals("out") && owner.equals("java/lang/System")) {
                    this.mv.visitFieldInsn(opcode, NEW_SYSTEM_OUT_CLASS, name, desc);
                    return;
                } else if (name.equals("err") && owner.equals("java/lang/System")) {
                    this.mv.visitFieldInsn(opcode, NEW_SYSTEM_OUT_CLASS, name, desc);
                    return;
                }
                break;
        }
        this.mv.visitFieldInsn(opcode, owner, name, desc);
    }

    @Override // org.objectweb.asm.MethodAdapter, org.objectweb.asm.MethodVisitor
    public void visitMethodInsn(int opcode, String owner, String name, String desc) {
        switch (opcode) {
            case Opcodes.INVOKEVIRTUAL /* 182 */:
                if (name.equals("getResourceAsStream") && owner.equals("java/lang/Class")) {
                    this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, NEW_RESOURCE_LOADER_CLASS, name, "(Ljava/lang/Class;Ljava/lang/String;)Ljava/io/InputStream;");
                    return;
                } else if (name.equals("printStackTrace") && owner.equals("java/lang/Throwable")) {
                    this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, INJECTED_CLASS, name, "(Ljava/lang/Throwable;)V");
                    return;
                }
                break;
            case Opcodes.INVOKESPECIAL /* 183 */:
                if (this.config.isEnhanceThreadCreation() && name.equals("<init>")) {
                    if (owner.equals("java/util/Timer")) {
                        owner = codeName(MIDletTimer.class);
                        break;
                    } else if (owner.equals("java/util/TimerTask")) {
                        owner = codeName(MIDletTimerTask.class);
                        break;
                    } else if (owner.equals("java/lang/Thread")) {
                        owner = codeName(MIDletThread.class);
                        break;
                    }
                }
                break;
            case Opcodes.INVOKESTATIC /* 184 */:
                if (name.equals("getProperty") && owner.equals("java/lang/System")) {
                    this.mv.visitMethodInsn(opcode, NEW_SYSTEM_PROPERTIES_CLASS, name, desc);
                    return;
                }
                break;
        }
        this.mv.visitMethodInsn(opcode, owner, name, desc);
    }

    @Override // org.objectweb.asm.MethodAdapter, org.objectweb.asm.MethodVisitor
    public void visitTypeInsn(int opcode, String desc) {
        if (opcode == 187 && this.config.isEnhanceThreadCreation()) {
            if ("java/util/Timer".equals(desc)) {
                desc = codeName(MIDletTimer.class);
            } else if ("java/util/TimerTask".equals(desc)) {
                desc = codeName(MIDletTimerTask.class);
            } else if ("java/lang/Thread".equals(desc)) {
                desc = codeName(MIDletThread.class);
            }
        }
        this.mv.visitTypeInsn(opcode, desc);
    }

    @Override // org.objectweb.asm.MethodAdapter, org.objectweb.asm.MethodVisitor
    public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
        if (this.config.isEnhanceCatchBlock() && type != null) {
            if (this.catchInfo == null) {
                this.catchInfo = new HashMap();
            }
            CatchInformation newHandler = (CatchInformation) this.catchInfo.get(handler);
            if (newHandler == null) {
                newHandler = new CatchInformation(type);
                this.catchInfo.put(handler, newHandler);
            }
            this.mv.visitTryCatchBlock(start, end, newHandler.label, type);
            return;
        }
        this.mv.visitTryCatchBlock(start, end, handler, type);
    }

    @Override // org.objectweb.asm.MethodAdapter, org.objectweb.asm.MethodVisitor
    public void visitLabel(Label label) {
        CatchInformation newHandler;
        if (this.config.isEnhanceCatchBlock() && this.catchInfo != null && (newHandler = (CatchInformation) this.catchInfo.get(label)) != null) {
            this.mv.visitLabel(newHandler.label);
            this.mv.visitMethodInsn(Opcodes.INVOKESTATIC, INJECTED_CLASS, "handleCatchThrowable", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;");
            this.mv.visitTypeInsn(Opcodes.CHECKCAST, newHandler.type);
        }
        this.mv.visitLabel(label);
    }
}

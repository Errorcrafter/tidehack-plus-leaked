// fuck you rat go brrrrrrrr
package risingtide.tidehack.asm.transformers;

import risingtide.tidehack.asm.AsmTransformer;
import risingtide.tidehack.asm.Descriptor;
import risingtide.tidehack.asm.MethodInfo;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;

public class CanvasWorldRendererTransformer extends AsmTransformer {
    private final MethodInfo renderWorldMethod, drawMethod;

    public CanvasWorldRendererTransformer() {
        super("grondag.canvas.render.world.CanvasWorldRenderer");

        renderWorldMethod = new MethodInfo(null, "renderWorld", null, false);

        // OutlineVertexConsumerProvider.draw()
        drawMethod = new MethodInfo("net/minecraft/class_4618", "method_23285", new Descriptor("V"), true);
    }

    @Override
    public void transform(ClassNode klass) {
        // Inject Outlines.endRender() after OutlineVertexConsumerProvider.draw() in CanvasWorldRenderer
        MethodNode method = getMethod(klass, renderWorldMethod);
        if (method == null) return;

        for (AbstractInsnNode insn : method.instructions) {
            if (!(insn instanceof MethodInsnNode in)) continue;

            if (drawMethod.equals(in)) {
                method.instructions.insert(insn, new MethodInsnNode(Opcodes.INVOKESTATIC, "risingtide/tidehack/utils/render/Outlines", "endRender", "()V"));
                break;
            }
        }
    }
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import net.minecraft.client.render.BufferRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(BufferRenderer.class)
public interface BufferRendererAccessor {
    @Accessor("currentVertexArray")
    static void setCurrentVertexArray(int vao) {}

    @Accessor("currentVertexBuffer")
    static void setCurrentVertexBuffer(int vbo) {}

    @Accessor("currentElementBuffer")
    static void setCurrentElementBuffer(int ibo) {}
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import it.unimi.dsi.fastutil.io.FastByteArrayOutputStream;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import risingtide.tidehack.utils.Utils;

import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Base64;

@Mixin(BookScreen.class)
public class BookScreenMixin extends Screen {
    @Shadow
    private BookScreen.Contents contents;

    @Shadow
    private int pageIndex;

    public BookScreenMixin(Text title) {
        super(title);
    }

    @Inject(method = "init", at = @At("TAIL"))
    private void onInit(CallbackInfo info) {
        addDrawableChild(new ButtonWidget(4, 4, 70, 20, new LiteralText("Copy"), button -> {
            NbtList listTag = new NbtList();
            for (int i = 0; i < contents.getPageCount(); i++) listTag.add(NbtString.of(contents.getPage(i).getString()));

            NbtCompound tag = new NbtCompound();
            tag.put("pages", listTag);
            tag.putInt("currentPage", pageIndex);

            FastByteArrayOutputStream bytes = new FastByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(bytes);
            try {
                NbtIo.write(tag, out);
            } catch (IOException e) {
                e.printStackTrace();
            }

            GLFW.glfwSetClipboardString(Utils.mc.getWindow().getHandle(), Base64.getEncoder().encodeToString(bytes.array));
        }));
    }
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.renderer;

import com.mojang.blaze3d.platform.GlStateManager;
import org.lwjgl.opengl.GL11C;
import risingtide.tidehack.utils.Utils;

import static org.lwjgl.opengl.GL32C.*;

public class Framebuffer {
    private int id;
    public int texture;

    public Framebuffer() {
        init();
    }

    private void init() {
        id = glGenFramebuffers();
        bind();

        texture = glGenTextures();
        GlStateManager._bindTexture(texture);

        glPixelStorei(GL_UNPACK_SWAP_BYTES, GL_FALSE);
        glPixelStorei(GL_UNPACK_LSB_FIRST, GL_FALSE);
        glPixelStorei(GL_UNPACK_ROW_LENGTH, 0);
        glPixelStorei(GL_UNPACK_IMAGE_HEIGHT, 0);
        glPixelStorei(GL_UNPACK_SKIP_ROWS, 0);
        glPixelStorei(GL_UNPACK_SKIP_PIXELS, 0);
        glPixelStorei(GL_UNPACK_SKIP_IMAGES, 0);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 4);

        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        GL11C.glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, Utils.mc.getWindow().getFramebufferWidth(), Utils.mc.getWindow().getFramebufferHeight(), 0, GL_RGB, GL_UNSIGNED_BYTE, 0);
        glFramebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

        unbind();
    }

    public void bind() {
        glBindFramebuffer(GL_FRAMEBUFFER, id);
    }

    public void unbind() {
        Utils.mc.getFramebuffer().beginWrite(false);
    }

    public void resize() {
        glDeleteFramebuffers(id);
        glDeleteTextures(texture);

        init();
    }
}

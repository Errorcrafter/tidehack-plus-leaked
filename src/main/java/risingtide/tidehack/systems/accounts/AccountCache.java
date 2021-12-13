// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.accounts;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.renderer.Texture;
import risingtide.tidehack.utils.misc.ISerializable;
import risingtide.tidehack.utils.misc.NbtException;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.Identifier;
import risingtide.tidehack.utils.Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;

public class AccountCache implements ISerializable<AccountCache> {
    public String username = "";
    public String uuid = "";

    private Texture headTexture;

    public Texture getHeadTexture() {
        return headTexture;
    }

    public boolean makeHead(String skinUrl) {
        try {
            BufferedImage skin;
            byte[] head = new byte[8 * 8 * 3];
            int[] pixel = new int[4];

            if (skinUrl.equals("steve"))
                skin = ImageIO.read(Utils.mc.getResourceManager().getResource(new Identifier("meteor-client", "textures/steve.png")).getInputStream());
            else skin = ImageIO.read(new URL(skinUrl));

            // Whole picture
            // TODO: Find a better way to do it
            int i = 0;
            for (int x = 0; x < 4 + 4; x++) {
                for (int y = 0; y < 4 + 4; y++) {
                    skin.getData().getPixel(x, y, pixel);

                    for (int j = 0; j < 3; j++) {
                        head[i] = (byte) pixel[j];
                        i++;
                    }
                }
            }

            headTexture = new Texture(8, 8, head, Texture.Format.RGB, Texture.Filter.Nearest, Texture.Filter.Nearest);
            return true;
        } catch (Exception e) {
            TideHack.LOG.error("Failed to read skin url. (" + skinUrl + ")");
            return false;
        }
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putString("username", username);
        tag.putString("uuid", uuid);

        return tag;
    }

    @Override
    public AccountCache fromTag(NbtCompound tag) {
        if (!tag.contains("username") || !tag.contains("uuid")) throw new NbtException();

        username = tag.getString("username");
        uuid = tag.getString("uuid");

        return this;
    }
}

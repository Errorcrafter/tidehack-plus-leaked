// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixininterface;

import net.minecraft.text.Text;

public interface IChatHud {
    void add(Text message, int messageId, int timestamp, boolean refresh);
}

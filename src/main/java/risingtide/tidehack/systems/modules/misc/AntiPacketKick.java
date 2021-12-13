// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;

public class AntiPacketKick extends Module {
    public AntiPacketKick() {
        super(Categories.Misc, "anti-packet-kick", "Attempts to prevent you from being disconnected by large packets.");
    }
}

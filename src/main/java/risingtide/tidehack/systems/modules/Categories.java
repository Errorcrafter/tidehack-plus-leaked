// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules;

import net.minecraft.item.Items;

public class Categories {
    public static final Category Combat = new Category("combat", Items.GOLDEN_SWORD.getDefaultStack());
    public static final Category Player = new Category("player", Items.ARMOR_STAND.getDefaultStack());
    public static final Category Movement = new Category("movement", Items.DIAMOND_BOOTS.getDefaultStack());
    public static final Category Render = new Category("render", Items.GLASS.getDefaultStack());
    public static final Category World = new Category("world", Items.GRASS_BLOCK.getDefaultStack());
    public static final Category Misc = new Category("misc", Items.LAVA_BUCKET.getDefaultStack());

    public static void register() {
        Modules.registerCategory(Combat);
        Modules.registerCategory(Player);
        Modules.registerCategory(Movement);
        Modules.registerCategory(Render);
        Modules.registerCategory(World);
        Modules.registerCategory(Misc);
    }
}

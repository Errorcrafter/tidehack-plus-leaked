// fuck you rat go brrrrrrrr
package risingtide.tidehack.events.entity.player;

import net.minecraft.item.ItemStack;

public class FinishUsingItemEvent {
    private static final FinishUsingItemEvent INSTANCE = new FinishUsingItemEvent();

    public ItemStack itemStack;

    public static FinishUsingItemEvent get(ItemStack itemStack) {
        INSTANCE.itemStack = itemStack;
        return INSTANCE;
    }
}

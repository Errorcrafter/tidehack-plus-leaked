// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.player;

import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.player.FindItemResult;
import risingtide.tidehack.utils.player.InvUtils;
import risingtide.tidehack.utils.player.Rotations;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;

public class EXPThrower extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Boolean> lookDown = sgGeneral.add(new BoolSetting.Builder()
            .name("rotate")
            .description("Forces you to rotate downwards when throwing bottles.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> autoToggle = sgGeneral.add(new BoolSetting.Builder()
            .name("auto-toggle")
            .description("Toggles off when your armor is repaired.")
            .defaultValue(true)
            .build()
    );

    public EXPThrower() {
        super(Categories.Player, "exp-thrower", "Automatically throws XP bottles in your hotbar.");
    }

    @EventHandler
    private void onTick(TickEvent.Pre event) {
        if (autoToggle.get()) {

            boolean shouldThrow = false;

            for (ItemStack itemStack : mc.player.getInventory().armor) {
                // If empty
                if (itemStack.isEmpty()) continue;

                // If no mending
                if (EnchantmentHelper.getLevel(Enchantments.MENDING, itemStack) < 1) continue;

                // If damaged
                if (itemStack.isDamaged()) {
                    shouldThrow = true;
                    break;
                }
            }

            if (!shouldThrow) {
                toggle();
                return;
            }
        }

        FindItemResult exp = InvUtils.findInHotbar(Items.EXPERIENCE_BOTTLE);

        if (exp.found()) {
            if (lookDown.get()) Rotations.rotate(mc.player.getYaw(), 90, () -> throwExp(exp));
            else throwExp(exp);
        }
    }

    private void throwExp(FindItemResult exp) {
        if (exp.isOffhand()) {
            mc.interactionManager.interactItem(mc.player, mc.world, Hand.OFF_HAND);
        } else {
            InvUtils.swap(exp.getSlot(), true);
            mc.interactionManager.interactItem(mc.player, mc.world, Hand.MAIN_HAND);
            InvUtils.swapBack();
        }
    }
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render.hud.modules;

import risingtide.tidehack.systems.modules.render.hud.HUD;

public class DurabilityHud extends DoubleTextHudElement {
    public DurabilityHud(HUD hud) {
        super(hud, "durability", "Displays durability of the item you are holding.", "durability: ");
    }

    @Override
    protected String getRight() {
        if (isInEditor()) return "159";

        Integer amount = null;
        if (!mc.player.getMainHandStack().isEmpty() && mc.player.getMainHandStack().isDamageable()) amount = mc.player.getMainHandStack().getMaxDamage() - mc.player.getMainHandStack().getDamage();

        return amount == null ? "" : amount.toString();
    }
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.player;

import it.unimi.dsi.fastutil.objects.Object2IntMap;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.mixin.StatusEffectInstanceAccessor;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.settings.StatusEffectAmplifierMapSetting;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.Utils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionSpoof extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Object2IntMap<StatusEffect>> potions = sgGeneral.add(new StatusEffectAmplifierMapSetting.Builder()
            .name("potions")
            .description("Potions to add.")
            .defaultValue(Utils.createStatusEffectMap())
            .build()
    );

    public PotionSpoof() {
        super(Categories.Player, "potion-spoof", "Spoofs specified potion effects for you. SOME effects DO NOT work.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        for (StatusEffect statusEffect : potions.get().keySet()) {
            int level = potions.get().getInt(statusEffect);
            if (level <= 0) continue;

            if (mc.player.hasStatusEffect(statusEffect)) {
                StatusEffectInstance instance = mc.player.getStatusEffect(statusEffect);
                ((StatusEffectInstanceAccessor) instance).setAmplifier(level - 1);
                if (instance.getDuration() < 20) ((StatusEffectInstanceAccessor) instance).setDuration(20);
            } else {
                mc.player.addStatusEffect(new StatusEffectInstance(statusEffect, 20, level - 1));
            }
        }
    }
}

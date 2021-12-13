// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.player.PotionSaver;
import risingtide.tidehack.utils.Utils;
import net.minecraft.entity.effect.StatusEffectInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StatusEffectInstance.class)
public class StatusEffectInstanceMixin {
    @Shadow private int duration;

    @Inject(method = "updateDuration", at = @At("HEAD"), cancellable = true)
    private void tick(CallbackInfoReturnable<Integer> info) {
        if (!Utils.canUpdate()) return;

        if (Modules.get().get(PotionSaver.class).shouldFreeze(((StatusEffectInstance) (Object) this).getEffectType())) {
            info.setReturnValue(duration);
        }
    }
}

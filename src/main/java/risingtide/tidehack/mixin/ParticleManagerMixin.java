// fuck you rat go brrrrrrrr
package risingtide.tidehack.mixin;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.world.ParticleEvent;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.NoRender;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ParticleManager.class)
public class ParticleManagerMixin {
    @Inject(method = "addParticle(Lnet/minecraft/particle/ParticleEffect;DDDDDD)Lnet/minecraft/client/particle/Particle;", at = @At("HEAD"), cancellable = true)
    private void onAddParticle(ParticleEffect parameters, double x, double y, double z, double velocityX, double velocityY, double velocityZ, CallbackInfoReturnable<Particle> info) {
        ParticleEvent event = TideHack.EVENT_BUS.post(ParticleEvent.get(parameters));
        if (event.isCancelled()) info.cancel();
    }

    @Inject(method = "addBlockBreakParticles", at = @At("HEAD"), cancellable = true)
    private void onAddBlockBreakParticles(BlockPos blockPos, BlockState state, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noBlockBreakParticles()) info.cancel();
    }

    @Inject(method = "addBlockBreakingParticles", at = @At("HEAD"), cancellable = true)
    private void onAddBlockBreakingParticles(BlockPos blockPos, Direction direction, CallbackInfo info) {
        if (Modules.get().get(NoRender.class).noBlockBreakParticles()) info.cancel();
    }
}

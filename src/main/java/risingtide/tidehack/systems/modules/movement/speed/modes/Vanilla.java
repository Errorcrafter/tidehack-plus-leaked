// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement.speed.modes;

import risingtide.tidehack.events.entity.player.PlayerMoveEvent;
import risingtide.tidehack.mixininterface.IVec3d;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.movement.Anchor;
import risingtide.tidehack.systems.modules.movement.speed.SpeedMode;
import risingtide.tidehack.systems.modules.movement.speed.SpeedModes;
import risingtide.tidehack.utils.player.PlayerUtils;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.Vec3d;

public class Vanilla extends SpeedMode {
    public Vanilla() {
        super(SpeedModes.Vanilla);
    }

    @Override
    public void onMove(PlayerMoveEvent event) {
        Vec3d vel = PlayerUtils.getHorizontalVelocity(settings.vanillaSpeed.get());
        double velX = vel.getX();
        double velZ = vel.getZ();

        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            double value = (mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier() + 1) * 0.205;
            velX += velX * value;
            velZ += velZ * value;
        }

        Anchor anchor = Modules.get().get(Anchor.class);
        if (anchor.isActive() && anchor.controlMovement) {
            velX = anchor.deltaX;
            velZ = anchor.deltaZ;
        }

        ((IVec3d) event.movement).set(velX, event.movement.y, velZ);
    }
}

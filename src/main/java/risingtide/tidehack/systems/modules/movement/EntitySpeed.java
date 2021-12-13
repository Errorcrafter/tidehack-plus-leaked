// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.events.entity.LivingEntityMoveEvent;
import risingtide.tidehack.mixininterface.IVec3d;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.player.PlayerUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

public class EntitySpeed extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<Double> speed = sgGeneral.add(new DoubleSetting.Builder()
            .name("speed")
            .description("Horizontal speed in blocks per second.")
            .defaultValue(10)
            .min(0)
            .sliderMax(50)
            .build()
    );

    private final Setting<Boolean> onlyOnGround = sgGeneral.add(new BoolSetting.Builder()
            .name("only-on-ground")
            .description("Use speed only when standing on a block.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Boolean> inWater = sgGeneral.add(new BoolSetting.Builder()
            .name("in-water")
            .description("Use speed when in water.")
            .defaultValue(false)
            .build()
    );

    public EntitySpeed() {
        super(Categories.Movement, "entity-speed", "Makes you go faster when riding entities.");
    }

    @EventHandler
    private void onLivingEntityMove(LivingEntityMoveEvent event) {
        if (event.entity.getPrimaryPassenger() != mc.player) return;

        // Check for onlyOnGround and inWater
        LivingEntity entity = event.entity;
        if (onlyOnGround.get() && !entity.isOnGround()) return;
        if (!inWater.get() && entity.isTouchingWater()) return;

        // Set horizontal velocity
        Vec3d vel = PlayerUtils.getHorizontalVelocity(speed.get());
        ((IVec3d) event.movement).setXZ(vel.x, vel.z);
    }
}

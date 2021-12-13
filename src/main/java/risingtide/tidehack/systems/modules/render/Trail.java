// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.render;

import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.settings.BoolSetting;
import risingtide.tidehack.settings.ParticleTypeListSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import java.util.ArrayList;
import java.util.List;

public class Trail extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<ParticleType<?>>> particles = sgGeneral.add(new ParticleTypeListSetting.Builder()
            .name("particles")
            .description("Particles to draw.")
            .defaultValue(new ArrayList<>(0))
            .build()
    );

    private final Setting<Boolean> pause = sgGeneral.add(new BoolSetting.Builder()
            .name("pause-when-stationary")
            .description("Whether or not to add particles when you are not moving.")
            .defaultValue(true)
            .build()
    );


    public Trail() {
        super(Categories.Render, "trail", "Renders a customizable trail behind your player.");
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (pause.get() && mc.player.input.movementForward == 0f && mc.player.input.movementSideways == 0f && !mc.options.keyJump.isPressed()) return;
        for (ParticleType<?> particleType : particles.get()) {
            mc.world.addParticle((ParticleEffect) particleType, mc.player.getX(), mc.player.getY(), mc.player.getZ(), 0, 0, 0);
        }
    }
}

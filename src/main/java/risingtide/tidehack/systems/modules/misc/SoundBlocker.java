// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.events.world.PlaySoundEvent;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.settings.SoundEventListSetting;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.List;

public class SoundBlocker extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    private final Setting<List<SoundEvent>> sounds = sgGeneral.add(new SoundEventListSetting.Builder()
            .name("sounds")
            .description("Sounds to block.")
            .defaultValue(new ArrayList<>(0))
            .build()
    );

    public SoundBlocker() {
        super(Categories.Misc, "sound-blocker", "Cancels out selected sounds.");
    }

    @EventHandler
    private void onPlaySound(PlaySoundEvent event) {
        for (SoundEvent sound : sounds.get()) {
            if (sound.getId().equals(event.sound.getId())) {
                event.cancel();
                break;
            }
        }
    }
}

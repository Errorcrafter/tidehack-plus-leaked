// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens.settings;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.utils.misc.Names;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class SoundEventListSettingScreen extends LeftRightListSettingScreen<SoundEvent> {
    public SoundEventListSettingScreen(GuiTheme theme, Setting<List<SoundEvent>> setting) {
        super(theme, "Select Sounds", setting, setting.get(), Registry.SOUND_EVENT);
    }

    @Override
    protected WWidget getValueWidget(SoundEvent value) {
        return theme.label(getValueName(value));
    }

    @Override
    protected String getValueName(SoundEvent value) {
        return Names.getSoundName(value.getId());
    }
}

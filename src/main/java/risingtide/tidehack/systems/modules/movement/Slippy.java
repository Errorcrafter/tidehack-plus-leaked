// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.movement;

import risingtide.tidehack.settings.BlockListSetting;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import net.minecraft.block.Block;

import java.util.Collections;
import java.util.List;

public class Slippy extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Double> slippness = sgGeneral.add(new DoubleSetting.Builder()
            .name("slippness")
            .description("Decide how slippery blocks should be")
            .min(0.0)
            .max(1.10)
            .sliderMax(1.10)
            .defaultValue(1.02)
            .build()
    );

    public final Setting<List<Block>> blocks = sgGeneral.add(new BlockListSetting.Builder()
            .name("ignored blocks")
            .description("Decide which blocks not to slip on")
            .defaultValue(Collections.emptyList())
            .build()
    );

    public Slippy() {
        super(Categories.Movement, "slippy", "Makes blocks slippery like ice.");
    }
}

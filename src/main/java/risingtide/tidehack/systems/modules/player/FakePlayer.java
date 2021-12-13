// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.player;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.entity.fakeplayer.FakePlayerManager;
import risingtide.tidehack.settings.*;

public class FakePlayer extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<String> name = sgGeneral.add(new StringSetting.Builder()
            .name("name")
            .description("The name of the fake player.")
            .defaultValue("seasnail8169")
            .build()
    );

    public final Setting<Boolean> copyInv = sgGeneral.add(new BoolSetting.Builder()
            .name("copy-inv")
            .description("Copies your exact inventory to the fake player.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Integer> health = sgGeneral.add(new IntSetting.Builder()
            .name("health")
            .description("The fake player's default health.")
            .defaultValue(20)
            .min(1)
            .sliderMax(100)
            .build()
    );

    public FakePlayer() {
        super(Categories.Player, "fake-player", "Spawns a client-side fake player for testing usages.");
    }

    @Override
    public void onActivate() {
        FakePlayerManager.clear();
    }

    @Override
    public void onDeactivate() {
        FakePlayerManager.clear();
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WHorizontalList w = theme.horizontalList();

        WButton spawn = w.add(theme.button("Spawn")).widget();
        spawn.action = () -> {
            if (isActive()) FakePlayerManager.add(name.get(), health.get(), copyInv.get());
        };

        WButton clear = w.add(theme.button("Clear")).widget();
        clear.action = () -> {
            if (isActive()) FakePlayerManager.clear();
        };

        return w;
    }

    @Override
    public String getInfoString() {
        if (FakePlayerManager.getPlayers() != null) return String.valueOf(FakePlayerManager.getPlayers().size());
        return null;
    }
}

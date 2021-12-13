// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens;

import risingtide.tidehack.events.meteor.ModuleBindChangedEvent;
import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.utils.Cell;
import risingtide.tidehack.gui.widgets.WKeybind;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WContainer;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.containers.WSection;
import risingtide.tidehack.gui.widgets.pressable.WCheckbox;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.utils.Utils;
import meteordevelopment.orbit.EventHandler;

public class ModuleScreen extends WindowScreen {
    private final Module module;

    private final WContainer settings;
    private final WKeybind keybind;

    public ModuleScreen(GuiTheme theme, Module module) {
        super(theme, module.title);
        this.module = module;

        // Description
        add(theme.label(module.description, Utils.getWindowWidth() / 2.0));

        // Settings
        settings = add(theme.verticalList()).expandX().widget();
        if (module.settings.groups.size() > 0) {
            settings.add(theme.settings(module.settings)).expandX();
        }

        // Custom widget
        WWidget widget = module.getWidget(theme);

        if (widget != null) {
            add(theme.horizontalSeparator()).expandX();
            Cell<WWidget> cell = add(widget);
            if (widget instanceof WContainer) cell.expandX();
        }

        // Bind
        WSection section = add(theme.section("Bind", true)).expandX().widget();
        keybind = section.add(theme.keybind(module.keybind)).expandX().widget();
        keybind.actionOnSet = () -> Modules.get().setModuleToBind(module);

        // Toggle on bind release
        WHorizontalList tobr = section.add(theme.horizontalList()).widget();

        tobr.add(theme.label("Toggle on bind release: "));
        WCheckbox tobrC = tobr.add(theme.checkbox(module.toggleOnBindRelease)).widget();
        tobrC.action = () -> module.toggleOnBindRelease = tobrC.checked;

        add(theme.horizontalSeparator()).expandX();

        // Bottom
        WHorizontalList bottom = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottom.add(theme.label("Active: "));
        WCheckbox active = bottom.add(theme.checkbox(module.isActive())).expandCellX().widget();
        active.action = () -> {
            if (module.isActive() != active.checked) module.toggle(Utils.canUpdate());
        };

        //   Visible
        bottom.add(theme.label("Visible: "));
        WCheckbox visible = bottom.add(theme.checkbox(module.isVisible())).widget();
        visible.action = () -> {
            if (module.isVisible() != visible.checked) module.setVisible(visible.checked);
        };
    }

    @Override
    public void tick() {
        super.tick();

        module.settings.tick(settings, theme);
    }

    @EventHandler
    private void onModuleBindChanged(ModuleBindChangedEvent event) {
        keybind.reset();
    }
}

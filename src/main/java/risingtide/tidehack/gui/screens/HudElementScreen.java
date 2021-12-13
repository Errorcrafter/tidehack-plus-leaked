// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens;

import risingtide.tidehack.events.render.Render2DEvent;
import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.widgets.containers.WContainer;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.gui.widgets.pressable.WCheckbox;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.render.hud.HUD;
import risingtide.tidehack.systems.modules.render.hud.modules.HudElement;
import risingtide.tidehack.utils.Utils;

public class HudElementScreen extends WindowScreen {
    private final HudElement element;
    private WContainer settings;

    public HudElementScreen(GuiTheme theme, HudElement element) {
        super(theme, element.title);
        this.element = element;

        // Description
        add(theme.label(element.description, Utils.getWindowWidth() / 2.0));

        // Settings
        if (element.settings.sizeGroups() > 0) {
            settings = add(theme.verticalList()).expandX().widget();
            settings.add(theme.settings(element.settings)).expandX();

            add(theme.horizontalSeparator()).expandX();
        }

        // Bottom
        WHorizontalList bottomList = add(theme.horizontalList()).expandX().widget();

        //   Active
        bottomList.add(theme.label("Active:"));
        WCheckbox active = bottomList.add(theme.checkbox(element.active)).widget();
        active.action = () -> {
            if (element.active != active.checked) element.toggle();
        };

        WButton reset = bottomList.add(theme.button(GuiRenderer.RESET)).expandCellX().right().widget();
        reset.action = () -> {
            if (element.active != element.defaultActive) element.active = active.checked = element.defaultActive;
        };
    }

    @Override
    public void tick() {
        super.tick();

        if (settings == null) return;

        element.settings.tick(settings, theme);
    }

    @Override
    protected void onRenderBefore(float delta) {
        if (!Utils.canUpdate()) Modules.get().get(HUD.class).onRender(Render2DEvent.get(0, 0, delta));
    }
}

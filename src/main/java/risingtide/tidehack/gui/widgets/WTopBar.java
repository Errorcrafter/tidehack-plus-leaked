// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.widgets;

import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.tabs.Tab;
import risingtide.tidehack.gui.tabs.TabScreen;
import risingtide.tidehack.gui.tabs.Tabs;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WPressable;
import risingtide.tidehack.utils.render.color.Color;
import net.minecraft.client.gui.screen.Screen;
import risingtide.tidehack.utils.Utils;

import static org.lwjgl.glfw.GLFW.glfwSetCursorPos;

public abstract class WTopBar extends WHorizontalList {
    protected abstract Color getButtonColor(boolean pressed, boolean hovered);

    protected abstract Color getNameColor();

    public WTopBar() {
        spacing = 0;
    }

    @Override
    public void init() {
        for (Tab tab : Tabs.get()) {
            add(new WTopBarButton(tab));
        }
    }

    protected class WTopBarButton extends WPressable {
        private final Tab tab;

        public WTopBarButton(Tab tab) {
            this.tab = tab;
        }

        @Override
        protected void onCalculateSize() {
            double pad = pad();

            width = pad + theme.textWidth(tab.name) + pad;
            height = pad + theme.textHeight() + pad;
        }

        @Override
        protected void onPressed(int button) {
            Screen screen = Utils.mc.currentScreen;

            if (!(screen instanceof TabScreen) || ((TabScreen) screen).tab != tab) {
                double mouseX = Utils.mc.mouse.getX();
                double mouseY = Utils.mc.mouse.getY();

                tab.openScreen(theme);
                glfwSetCursorPos(Utils.mc.getWindow().getHandle(), mouseX, mouseY);
            }
        }

        @Override
        protected void onRender(GuiRenderer renderer, double mouseX, double mouseY, double delta) {
            double pad = pad();
            Color color = getButtonColor(pressed || (Utils.mc.currentScreen instanceof TabScreen && ((TabScreen) Utils.mc.currentScreen).tab == tab), mouseOver);

            renderer.quad(x, y, width, height, color);
            renderer.text(tab.name, x + pad, y + pad, getNameColor(), false);
        }
    }
}

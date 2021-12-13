// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.tabs.builtin;

import risingtide.tidehack.events.meteor.KeyEvent;
import risingtide.tidehack.events.meteor.MouseButtonEvent;
import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.tabs.Tab;
import risingtide.tidehack.gui.tabs.TabScreen;
import risingtide.tidehack.gui.tabs.WindowTabScreen;
import risingtide.tidehack.gui.widgets.WKeybind;
import risingtide.tidehack.gui.widgets.containers.WTable;
import risingtide.tidehack.gui.widgets.input.WTextBox;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.gui.widgets.pressable.WMinus;
import risingtide.tidehack.gui.widgets.pressable.WPlus;
import risingtide.tidehack.systems.macros.Macro;
import risingtide.tidehack.systems.macros.Macros;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.EventPriority;
import net.minecraft.client.gui.screen.Screen;
import risingtide.tidehack.utils.Utils;

public class MacrosTab extends Tab {
    public MacrosTab() {
        super("macros");
    }

    @Override
    public TabScreen createScreen(GuiTheme theme) {
        return new MacrosScreen(theme, this);
    }

    @Override
    public boolean isScreen(Screen screen) {
        return screen instanceof MacrosScreen;
    }

    private static class MacrosScreen extends WindowTabScreen {
        public MacrosScreen(GuiTheme theme, Tab tab) {
            super(theme, tab);
        }

        @Override
        protected void init() {
            super.init();

            clear();
            initWidgets();
        }

        private void initWidgets() {
            // Macros
            if (Macros.get().getAll().size() > 0) {
                WTable table = add(theme.table()).expandX().widget();

                for (Macro macro : Macros.get()) {
                    table.add(theme.label(macro.name + " (" + macro.keybind + ")"));

                    WButton edit = table.add(theme.button(GuiRenderer.EDIT)).expandCellX().right().widget();
                    edit.action = () -> Utils.mc.openScreen(new MacroEditorScreen(theme, macro));

                    WMinus remove = table.add(theme.minus()).widget();
                    remove.action = () -> {
                        Macros.get().remove(macro);

                        clear();
                        initWidgets();
                    };

                    table.row();
                }
            }

            // New
            WButton create = add(theme.button("Create")).expandX().widget();
            create.action = () -> Utils.mc.openScreen(new MacroEditorScreen(theme, null));
        }
    }

    private static class MacroEditorScreen extends WindowScreen {
        private final Macro macro;
        private final boolean isNewMacro;

        private WKeybind keybind;
        private boolean binding;

        public MacroEditorScreen(GuiTheme theme, Macro m) {
            super(theme, m == null ? "Create Macro" : "Edit Macro");
            isNewMacro = m == null;
            this.macro = isNewMacro ? new Macro() : m;

            initWidgets(m);
        }

        private void initWidgets(Macro m) {
            // Name
            WTable t = add(theme.table()).widget();

            t.add(theme.label("Name:"));
            WTextBox name = t.add(theme.textBox(m == null ? "" : macro.name)).minWidth(400).expandX().widget();
            name.setFocused(true);
            name.action = () -> macro.name = name.get().trim();
            t.row();

            // Messages
            t.add(theme.label("Messages:")).padTop(4).top();
            WTable lines = t.add(theme.table()).widget();
            fillMessagesTable(lines);

            // Bind
            keybind = add(theme.keybind(macro.keybind)).expandX().widget();
            keybind.actionOnSet = () -> binding = true;

            // Apply
            WButton apply = add(theme.button(isNewMacro ? "Add" : "Apply")).expandX().widget();
            apply.action = () -> {
                if (isNewMacro) {
                    if (macro.name != null && !macro.name.isEmpty() && macro.messages.size() > 0 && macro.keybind.isSet()) {
                        Macros.get().add(macro);
                        onClose();
                    }
                } else {
                    Macros.get().save();
                    onClose();
                }
            };

            enterAction = apply.action;
        }

        private void fillMessagesTable(WTable lines) {
            if (macro.messages.isEmpty()) macro.addMessage("");

            for (int i = 0; i < macro.messages.size(); i++) {
                int ii = i;

                WTextBox line = lines.add(theme.textBox(macro.messages.get(i))).minWidth(400).expandX().widget();
                line.action = () -> macro.messages.set(ii, line.get().trim());

                if (i != macro.messages.size() - 1) {
                    WMinus remove = lines.add(theme.minus()).widget();
                    remove.action = () -> {
                        macro.removeMessage(ii);

                        clear();
                        initWidgets(macro);
                    };
                } else {
                    WPlus add = lines.add(theme.plus()).widget();
                    add.action = () -> {
                        macro.addMessage("");

                        clear();
                        initWidgets(macro);
                    };
                }

                lines.row();
            }
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        private void onKey(KeyEvent event) {
            if (onAction(true, event.key)) event.cancel();
        }

        @EventHandler(priority = EventPriority.HIGHEST)
        private void onButton(MouseButtonEvent event) {
            if (onAction(false, event.button)) event.cancel();
        }

        private boolean onAction(boolean isKey, int value) {
            if (binding) {
                keybind.onAction(isKey, value);

                binding = false;
                return true;
            }

            return false;
        }
    }
}

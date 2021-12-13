// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens.settings;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.widgets.containers.WTable;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.settings.PotionSetting;
import risingtide.tidehack.utils.misc.MyPotion;

public class PotionSettingScreen extends WindowScreen {
    public PotionSettingScreen(GuiTheme theme, PotionSetting setting) {
        super(theme, "Select Potion");

        WTable table = add(theme.table()).expandX().widget();

        for (MyPotion potion : MyPotion.values()) {
            table.add(theme.itemWithLabel(potion.potion, potion.potion.getName().getString()));

            WButton select = table.add(theme.button("Select")).widget();
            select.action = () -> {
                setting.set(potion);
                onClose();
            };

            table.row();
        }
    }
}

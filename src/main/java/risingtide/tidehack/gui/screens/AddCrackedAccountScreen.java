// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.widgets.containers.WTable;
import risingtide.tidehack.gui.widgets.input.WTextBox;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.systems.accounts.Accounts;
import risingtide.tidehack.systems.accounts.types.CrackedAccount;

public class AddCrackedAccountScreen extends WindowScreen {
    public AddCrackedAccountScreen(GuiTheme theme) {
        super(theme, "Add Cracked Account");

        WTable t = add(theme.table()).widget();

        // Name
        t.add(theme.label("Name: "));
        WTextBox name = t.add(theme.textBox("", (text, c) ->
            // Username can't contain spaces
            c != ' '
        )).minWidth(400).expandX().widget();
        name.setFocused(true);
        t.row();

        // Add
        WButton add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            if(!name.get().isEmpty()) {
                CrackedAccount account = new CrackedAccount(name.get());
                if (!(Accounts.get().exists(account))) {
                    AccountsScreen.addAccount(add, this, account);
                }
            }
        };

        enterAction = add.action;
    }
}

// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.widgets.containers.WTable;
import risingtide.tidehack.gui.widgets.input.WTextBox;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.systems.accounts.Accounts;
import risingtide.tidehack.systems.accounts.types.PremiumAccount;

public class AddPremiumAccountScreen extends WindowScreen {
    public AddPremiumAccountScreen(GuiTheme theme) {
        super(theme, "Add Premium Account");

        WTable t = add(theme.table()).widget();

        // Email
        t.add(theme.label("Email: "));
        WTextBox email = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        email.setFocused(true);
        t.row();

        // Password
        t.add(theme.label("Password: "));
        WTextBox password = t.add(theme.textBox("")).minWidth(400).expandX().widget();
        t.row();

        // Add
        WButton add = t.add(theme.button("Add")).expandX().widget();
        add.action = () -> {
            PremiumAccount account = new PremiumAccount(email.get(), password.get());
            if (!email.get().isEmpty() && !password.get().isEmpty() && email.get().contains("@") && !Accounts.get().exists(account)) {
                AccountsScreen.addAccount(add, this, account);
            }
        };

        enterAction = add.action;
    }
}

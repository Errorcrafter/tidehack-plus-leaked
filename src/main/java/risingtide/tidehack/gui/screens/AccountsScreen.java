// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.screens;

import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.gui.WindowScreen;
import risingtide.tidehack.gui.widgets.WAccount;
import risingtide.tidehack.gui.widgets.containers.WContainer;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.systems.accounts.Account;
import risingtide.tidehack.systems.accounts.Accounts;
import risingtide.tidehack.systems.accounts.MicrosoftLogin;
import risingtide.tidehack.systems.accounts.types.MicrosoftAccount;
import risingtide.tidehack.utils.network.MeteorExecutor;
import risingtide.tidehack.utils.Utils;

public class AccountsScreen extends WindowScreen {
    public AccountsScreen(GuiTheme theme) {
        super(theme, "Accounts");
    }

    @Override
    protected void init() {
        super.init();

        clear();
        initWidgets();
    }

    private void initWidgets() {
        // Accounts
        for (Account<?> account : Accounts.get()) {
            WAccount wAccount = add(theme.account(this, account)).expandX().widget();
            wAccount.refreshScreenAction = () -> {
                clear();
                initWidgets();
            };
        }

        // Add account
        WHorizontalList l = add(theme.horizontalList()).expandX().widget();

        addButton(l, "Cracked", () -> Utils.mc.openScreen(new AddCrackedAccountScreen(theme)));
        addButton(l, "Premium", () -> Utils.mc.openScreen(new AddPremiumAccountScreen(theme)));
        /*addButton(l, "Microsoft", () -> {

            locked = true;

            MicrosoftLogin.getRefreshToken(refreshToken -> {
                locked = false;

                if (refreshToken != null) {
                    MicrosoftAccount account = new MicrosoftAccount(refreshToken);
                    addAccount(null, this, account);
                }
            });
        });*/
        addButton(l, "The Altening", () -> Utils.mc.openScreen(new AddAlteningAccountScreen(theme)));
    }

    private void addButton(WContainer c, String text, Runnable action) {
        WButton button = c.add(theme.button(text)).expandX().widget();
        button.action = action;
    }

    public static void addAccount(WButton add, WidgetScreen screen, Account<?> account) {
        if (add != null) add.set("...");
        screen.locked = true;

        MeteorExecutor.execute(() -> {
            if (account.fetchInfo() && account.fetchHead()) {
                Accounts.get().add(account);
                screen.locked = false;

                if (add != null) screen.onClose();
                else if (screen instanceof AccountsScreen s) {
                    s.clear();
                    s.initWidgets();
                }
            }

            if (add != null) add.set("Add");
            screen.locked = false;
        });
    }
}

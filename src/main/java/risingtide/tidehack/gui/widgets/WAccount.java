// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.widgets;

import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.gui.widgets.pressable.WMinus;
import risingtide.tidehack.systems.accounts.Account;
import risingtide.tidehack.systems.accounts.Accounts;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.network.MeteorExecutor;
import risingtide.tidehack.utils.render.color.Color;

public abstract class WAccount extends WHorizontalList {
    public Runnable refreshScreenAction;

    private final WidgetScreen screen;
    private final Account<?> account;

    public WAccount(WidgetScreen screen, Account<?> account) {
        this.screen = screen;
        this.account = account;
    }

    protected abstract Color loggedInColor();

    protected abstract Color accountTypeColor();

    @Override
    public void init() {
        // Head
        add(theme.texture(32, 32, 90, account.getCache().getHeadTexture()));

        // Name
        WLabel name = add(theme.label(account.getUsername())).widget();
        if (Utils.mc.getSession().getUsername().equalsIgnoreCase(account.getUsername())) name.color = loggedInColor();

        // Type
        WLabel label = add(theme.label("(" + account.getType() + ")")).expandCellX().right().widget();
        label.color = accountTypeColor();

        // Login
        WButton login = add(theme.button("Login")).widget();
        login.action = () -> {
            login.minWidth = login.width;
            login.set("...");
            screen.locked = true;

            MeteorExecutor.execute(() -> {
                if (account.login()) {
                    name.set(account.getUsername());

                    Accounts.get().save();

                    screen.taskAfterRender = refreshScreenAction;
                }

                login.minWidth = 0;
                login.set("Login");
                screen.locked = false;
            });
        };

        // Remove
        WMinus remove = add(theme.minus()).widget();
        remove.action = () -> {
            Accounts.get().remove(account);
            if (refreshScreenAction != null) refreshScreenAction.run();
        };
    }
}

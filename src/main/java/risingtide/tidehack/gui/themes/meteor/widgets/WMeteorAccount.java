// fuck you rat go brrrrrrrr
package risingtide.tidehack.gui.themes.meteor.widgets;

import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.gui.themes.meteor.MeteorWidget;
import risingtide.tidehack.gui.widgets.WAccount;
import risingtide.tidehack.systems.accounts.Account;
import risingtide.tidehack.utils.render.color.Color;

public class WMeteorAccount extends WAccount implements MeteorWidget {
    public WMeteorAccount(WidgetScreen screen, Account<?> account) {
        super(screen, account);
    }

    @Override
    protected Color loggedInColor() {
        return theme().loggedInColor.get();
    }

    @Override
    protected Color accountTypeColor() {
        return theme().textSecondaryColor.get();
    }
}

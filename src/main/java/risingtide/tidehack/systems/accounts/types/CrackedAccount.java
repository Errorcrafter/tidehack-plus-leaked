// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.accounts.types;

import risingtide.tidehack.systems.accounts.Account;
import risingtide.tidehack.systems.accounts.AccountType;
import risingtide.tidehack.systems.accounts.ProfileResponse;
import risingtide.tidehack.utils.network.Http;
import net.minecraft.client.util.Session;

public class CrackedAccount extends Account<CrackedAccount> {
    public CrackedAccount(String name) {
        super(AccountType.Cracked, name);

    }

    @Override
    public boolean fetchInfo() {
        cache.username = name;
        return true;
    }

    @Override
    public boolean fetchHead() {
        ProfileResponse res = Http.get("https://api.mojang.com/users/profiles/minecraft/" + cache.username).sendJson(ProfileResponse.class);

        if (res == null) return cache.makeHead("steve");
        return cache.makeHead("https://www.mc-heads.net/avatar/" + res.id + "/8");
    }

    @Override
    public boolean login() {
        super.login();

        setSession(new Session(name, "", "", "mojang"));
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof CrackedAccount)) return false;
        return ((CrackedAccount) o).getUsername().equals(this.getUsername());
    }
}

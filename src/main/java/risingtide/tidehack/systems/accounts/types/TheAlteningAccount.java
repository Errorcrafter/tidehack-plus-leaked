// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.accounts.types;

import com.google.gson.Gson;
import com.mojang.authlib.Agent;
import com.mojang.authlib.Environment;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import risingtide.tidehack.TideHack;
import risingtide.tidehack.mixin.MinecraftClientAccessor;
import risingtide.tidehack.systems.accounts.Account;
import risingtide.tidehack.systems.accounts.AccountType;
import risingtide.tidehack.systems.accounts.AccountUtils;
import net.minecraft.client.util.Session;
import risingtide.tidehack.utils.Utils;

public class TheAlteningAccount extends Account<TheAlteningAccount> {
    private static final String AUTH = "http://authserver.thealtening.com";
    private static final String ACCOUNT = "https://api.mojang.com";
    private static final String SESSION = "http://sessionserver.thealtening.com";
    private static final String SERVICES = "https://api.minecraftservices.com";

    private static final Gson GSON = new Gson();

    public TheAlteningAccount(String token) {
        super(AccountType.TheAltening, token);
    }

    @Override
    public boolean fetchInfo() {
        YggdrasilUserAuthentication auth = getAuth();

        try {
            auth.logIn();

            cache.username = auth.getSelectedProfile().getName();
            cache.uuid = auth.getSelectedProfile().getId().toString();

            return true;
        } catch (AuthenticationException e) {
            return false;
        }
    }

    @Override
    public boolean fetchHead() {
        try {
            return cache.makeHead("https://www.mc-heads.net/avatar/" + cache.uuid + "/8");
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean login() {
        YggdrasilMinecraftSessionService service = (YggdrasilMinecraftSessionService) Utils.mc.getSessionService();
        AccountUtils.setBaseUrl(service, SESSION + "/session/minecraft/");
        AccountUtils.setJoinUrl(service, SESSION + "/session/minecraft/join");
        AccountUtils.setCheckUrl(service, SESSION + "/session/minecraft/hasJoined");

        YggdrasilUserAuthentication auth = getAuth();

        try {
            auth.logIn();
            setSession(new Session(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(), auth.getAuthenticatedToken(), "mojang"));

            cache.username = auth.getSelectedProfile().getName();
            return true;
        } catch (AuthenticationException e) {
            TideHack.LOG.error("Failed to login with TheAltening.");
            return false;
        }
    }

    private YggdrasilUserAuthentication getAuth() {
        YggdrasilUserAuthentication auth = (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(((MinecraftClientAccessor) Utils.mc).getProxy(), "", Environment.create(AUTH, ACCOUNT, SESSION, SERVICES, "The Altening")).createUserAuthentication(Agent.MINECRAFT);

        auth.setUsername(name);
        auth.setPassword("TideHack on top!");

        return auth;
    }
}

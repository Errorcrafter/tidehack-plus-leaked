// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.events.world.ConnectToServerEvent;
import risingtide.tidehack.settings.DoubleSetting;
import risingtide.tidehack.settings.Setting;
import risingtide.tidehack.settings.SettingGroup;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.client.network.ServerInfo;

public class AutoReconnect extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Double> time = sgGeneral.add(new DoubleSetting.Builder()
            .name("delay")
            .description("The amount of seconds to wait before reconnecting to the server.")
            .defaultValue(3.5)
            .min(0)
            .decimalPlaces(1)
            .build()
    );

    public ServerInfo lastServerInfo;

    public AutoReconnect() {
        super(Categories.Misc, "auto-reconnect", "Automatically reconnects when disconnected from a server.");
        TideHack.EVENT_BUS.subscribe(new StaticListener());
    }

    private class StaticListener {
        @EventHandler
        private void onConnectToServer(ConnectToServerEvent event) {
            lastServerInfo = mc.isInSingleplayer() ? null : mc.getCurrentServerEntry();
        }
    }
}

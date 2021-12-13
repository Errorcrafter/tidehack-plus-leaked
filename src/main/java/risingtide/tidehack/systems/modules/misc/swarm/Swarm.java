// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc.swarm;

import risingtide.tidehack.events.game.GameJoinedEvent;
import risingtide.tidehack.events.game.GameLeftEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.containers.WHorizontalList;
import risingtide.tidehack.gui.widgets.containers.WVerticalList;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.util.Util;

/**
 * @author Inclemental
 * rewritten by smaile
 */
public class Swarm extends Module {
    public enum Mode {
        Host,
        Worker
    }

    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public final Setting<Mode> mode = sgGeneral.add(new EnumSetting.Builder<Mode>()
            .name("mode")
            .description("What type of client to run.")
            .defaultValue(Mode.Host)
            .build()
    );

    private final Setting<String> ipAddress = sgGeneral.add(new StringSetting.Builder()
            .name("ip")
            .description("The IP address of the host server.")
            .defaultValue("localhost")
            .visible(() -> mode.get() == Mode.Worker)
            .build()
    );

    private final Setting<Integer> serverPort = sgGeneral.add(new IntSetting.Builder()
            .name("port")
            .description("The port used for connections.")
            .defaultValue(7777)
            .sliderMin(1).sliderMax(65535)
            .build()
    );

    public SwarmHost host;
    public SwarmWorker worker;

    public Swarm() {
        super(Categories.Misc, "Swarm", "Allows you to control multiple instances of Meteor from one central host.");
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WVerticalList list = theme.verticalList();

        WHorizontalList b = list.add(theme.horizontalList()).expandX().widget();

        WButton start = b.add(theme.button("Start")).expandX().widget();
        start.action = () -> {
            if (!isActive()) return;

            close();
            if (mode.get() == Mode.Host) host = new SwarmHost(serverPort.get());
            else worker = new SwarmWorker(ipAddress.get(), serverPort.get());
        };

        WButton stop = b.add(theme.button("Stop")).expandX().widget();
        stop.action = this::close;

        WButton guide = list.add(theme.button("Guide")).expandX().widget();
        guide.action = () -> Util.getOperatingSystem().open("https://github.com/MeteorDevelopment/meteor-client/wiki/Swarm-Guide");

        return list;
    }

    @Override
    public String getInfoString() {
        return mode.get().name();
    }

    @Override
    public void onActivate() {
        close();
    }

    @Override
    public void onDeactivate() {
        close();
    }

    public void close() {
        try {
            if (host != null) {
                host.disconnect();
                host = null;
            }
            if (worker != null) {
                worker.disconnect();
                worker = null;
            }
        } catch (Exception ignored) {}
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        toggle();
    }

    @EventHandler
    private void onGameJoin(GameJoinedEvent event) {
        toggle();
    }

    @Override
    public void toggle() {
        close();
        super.toggle();
    }

    public boolean isHost() {
        return mode.get() == Mode.Host && host != null && !host.isInterrupted();
    }

    public boolean isWorker() {
        return mode.get() == Mode.Worker && worker != null && !worker.isInterrupted();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (isWorker()) worker.tick();
    }
}

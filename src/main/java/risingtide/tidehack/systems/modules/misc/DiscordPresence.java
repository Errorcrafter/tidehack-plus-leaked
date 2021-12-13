// fuck you rat go brrrrrrrr
package risingtide.tidehack.systems.modules.misc;

//Created by squidoodly

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.gui.GuiTheme;
import risingtide.tidehack.gui.widgets.WWidget;
import risingtide.tidehack.gui.widgets.pressable.WButton;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.settings.*;
import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Module;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.misc.MeteorStarscript;
import risingtide.tidehack.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.starscript.Script;
import meteordevelopment.starscript.compiler.Compiler;
import meteordevelopment.starscript.compiler.Parser;
import meteordevelopment.starscript.utils.StarscriptError;
import net.minecraft.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DiscordPresence extends Module {
    public enum SelectMode {
        Random,
        Sequential
    }

    public enum Icon {
        Default,
        Classic
    }

    private final SettingGroup sgLine1 = settings.createGroup("Line 1");
    private final SettingGroup sgLine2 = settings.createGroup("Line 2");
    private final SettingGroup sgIcon = settings.createGroup("RPC Icon");

    // Line 1

    private final Setting<List<String>> line1Strings = sgLine1.add(new StringListSetting.Builder()
        .name("line-1-messages")
        .description("Messages used for the first line.")
        .defaultValue(List.of("{player}", "{server}"))
        .onChanged(strings -> recompileLine1())
        .build()
    );

    private final Setting<Integer> line1UpdateDelay = sgLine1.add(new IntSetting.Builder()
        .name("line-1-update-delay")
        .description("How fast to update the first line in ticks.")
        .defaultValue(200)
        .min(10)
        .sliderMin(10).sliderMax(200)
        .build()
    );

    private final Setting<SelectMode> line1SelectMode = sgLine1.add(new EnumSetting.Builder<SelectMode>()
        .name("line-1-select-mode")
        .description("How to select messages for the first line.")
        .defaultValue(SelectMode.Sequential)
        .build()
    );

    // Line 2

    private final Setting<List<String>> line2Strings = sgLine2.add(new StringListSetting.Builder()
        .name("line-2-messages")
        .description("Messages used for the second line.")
        .defaultValue(List.of("TideHack on top!", "{round({server.tps}, 1)} TPS", "Playing on {server.difficulty} difficulty.", "{server.player_count} Players online"))
        .onChanged(strings -> recompileLine2())
        .build()
    );

    private final Setting<Integer> line2UpdateDelay = sgLine2.add(new IntSetting.Builder()
        .name("line-2-update-delay")
        .description("How fast to update the second line in ticks.")
        .defaultValue(60)
        .min(10)
        .sliderMin(10).sliderMax(200)
        .build()
    );

    private final Setting<SelectMode> line2SelectMode = sgLine2.add(new EnumSetting.Builder<SelectMode>()
        .name("line-2-select-mode")
        .description("How to select messages for the second line.")
        .defaultValue(SelectMode.Sequential)
        .build()
    );

    private final Setting<Icon> iconSelectMode = sgIcon.add(new EnumSetting.Builder<Icon>()
        .name("icon-mode")
        .description("Which icon to use.")
        .defaultValue(Icon.Default)
        .build()
    );

    private static final DiscordRichPresence rpc = new DiscordRichPresence();
    private static final DiscordRPC instance = DiscordRPC.INSTANCE;
    private SmallImage currentSmallImage;
    private int ticks;
    private boolean forceUpdate;

    private final List<Script> line1Scripts = new ArrayList<>();
    private int line1Ticks, line1I;

    private final List<Script> line2Scripts = new ArrayList<>();
    private int line2Ticks, line2I;

    public DiscordPresence() {
        super(Categories.Misc, "discord-presence", "Displays TideHack as your presence on Discord.");
    }

    @Override
    public void onActivate() {
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        instance.Discord_Initialize("874956428325027840", handlers, true, null);

        rpc.startTimestamp = System.currentTimeMillis() / 1000L;

        rpc.largeImageKey = iconSelectMode.get() == Icon.Default? "tidehackplus" : "tidehackclassic";//"tidehackplus";
        String largeText = "TideHack " + Config.get().version;
        if (!Config.get().devBuild.isEmpty()) largeText += " Dev Build: " + Config.get().devBuild;
        rpc.largeImageText = largeText;

        currentSmallImage = SmallImage.Snail;

        recompileLine1();
        recompileLine2();

        ticks = 0;
        line1Ticks = 0;
        line2Ticks = 0;

        line1I = 0;
        line2I = 0;
    }

    @Override
    public void onDeactivate() {
        instance.Discord_ClearPresence();
        instance.Discord_Shutdown();
    }

    private void recompile(List<String> messages, List<Script> scripts) {
        scripts.clear();

        for (int i = 0; i < messages.size(); i++) {
            Parser.Result result = Parser.parse(messages.get(i));

            if (result.hasErrors()) {
                if (Utils.canUpdate()) {
                    MeteorStarscript.printChatError(i, result.errors.get(0));
                }

                continue;
            }

            scripts.add(Compiler.compile(result));
        }

        forceUpdate = true;
    }

    private void recompileLine1() {
        recompile(line1Strings.get(), line1Scripts);
    }

    private void recompileLine2() {
        recompile(line2Strings.get(), line2Scripts);
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (!Utils.canUpdate()) return;
        boolean update = false;

        // Image
        if (ticks >= 200 || forceUpdate) {
            currentSmallImage = currentSmallImage.next();
            currentSmallImage.apply();
            update = true;

            ticks = 0;
        }
        else ticks++;

        // Line 1
        if (line1Ticks >= line1UpdateDelay.get() || forceUpdate) {
            if (line1Scripts.size() > 0) {
                int i = Utils.random(0, line1Scripts.size());
                if (line1SelectMode.get() == SelectMode.Sequential) {
                    if (line1I >= line1Scripts.size()) line1I = 0;
                    i = line1I++;
                }

                try {
                    rpc.details = MeteorStarscript.ss.run(line1Scripts.get(i));
                } catch (StarscriptError e) {
                    ChatUtils.error("Starscript", e.getMessage());
                }
            }
            update = true;

            line1Ticks = 0;
        }
        else line1Ticks++;

        // Line 2
        if (line2Ticks >= line2UpdateDelay.get() || forceUpdate) {
            if (line2Scripts.size() > 0) {
                int i = Utils.random(0, line2Scripts.size());
                if (line2SelectMode.get() == SelectMode.Sequential) {
                    if (line2I >= line2Scripts.size()) line2I = 0;
                    i = line2I++;
                }

                try {
                    rpc.state = MeteorStarscript.ss.run(line2Scripts.get(i));
                } catch (StarscriptError e) {
                    ChatUtils.error("Starscript", e.getMessage());
                }
            }
            update = true;

            line2Ticks = 0;
        }
        else line2Ticks++;

        // Update
        if (update) instance.Discord_UpdatePresence(rpc);
        forceUpdate = false;

        instance.Discord_RunCallbacks();
    }

    @Override
    public WWidget getWidget(GuiTheme theme) {
        WButton help = theme.button("Open documentation.");
        help.action = () -> Util.getOperatingSystem().open("https://github.com/MeteorDevelopment/meteor-client/wiki/Starscript");

        return help;
    }

    private enum SmallImage {
        MineGame("minegame", "MineGame159"),
        Snail("seasnail", "seasnail8169");

        private final String key, text;

        SmallImage(String key, String text) {
            this.key = key;
            this.text = text;
        }

        void apply() {
            rpc.smallImageKey = key;
            rpc.smallImageText = text;
        }

        SmallImage next() {
            if (this == MineGame) return Snail;
            return MineGame;
        }
    }
}

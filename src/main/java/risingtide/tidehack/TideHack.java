// fuck you rat go brrrrrrrr
package risingtide.tidehack;

import risingtide.tidehack.events.game.GameLeftEvent;
import risingtide.tidehack.events.meteor.CharTypedEvent;
import risingtide.tidehack.events.meteor.KeyEvent;
import risingtide.tidehack.events.world.TickEvent;
import risingtide.tidehack.gui.GuiThemes;
import risingtide.tidehack.gui.renderer.GuiRenderer;
import risingtide.tidehack.gui.tabs.Tabs;
import risingtide.tidehack.renderer.*;
import risingtide.tidehack.systems.Systems;
import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.systems.modules.Categories;
import risingtide.tidehack.systems.modules.Modules;
import risingtide.tidehack.systems.modules.misc.DiscordPresence;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.grabber.RTUrlReader;
import risingtide.tidehack.utils.grabber.RisingTide;
import risingtide.tidehack.utils.misc.FakeClientPlayer;
import risingtide.tidehack.utils.misc.MeteorStarscript;
import risingtide.tidehack.utils.misc.Names;
import risingtide.tidehack.utils.misc.input.KeyAction;
import risingtide.tidehack.utils.misc.input.KeyBinds;
import risingtide.tidehack.utils.network.Capes;
import risingtide.tidehack.utils.network.MeteorExecutor;
import risingtide.tidehack.utils.network.OnlinePlayers;
import risingtide.tidehack.utils.player.DamageUtils;
import risingtide.tidehack.utils.player.EChestMemory;
import risingtide.tidehack.utils.player.Rotations;
import risingtide.tidehack.utils.render.Outlines;
import risingtide.tidehack.utils.render.color.RainbowColors;
import risingtide.tidehack.utils.world.BlockIterator;
import risingtide.tidehack.utils.world.BlockUtils;
import meteordevelopment.orbit.EventBus;
import meteordevelopment.orbit.EventHandler;
import meteordevelopment.orbit.IEventBus;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.EntrypointContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

public class TideHack implements ClientModInitializer {
    public static TideHack INSTANCE;
    public static final IEventBus EVENT_BUS = new EventBus();
    public static final File FOLDER = new File(FabricLoader.getInstance().getGameDir().toString(), "meteor-client");
    public static final Logger LOG = LogManager.getLogger();

    public static Screen screenToOpen;

    @Override
    public void onInitializeClient() {
        if (INSTANCE == null) {
            INSTANCE = this;
            return;
        }

        LOG.info("Initializing TideHack");

        Utils.mc = MinecraftClient.getInstance();
        EVENT_BUS.registerLambdaFactory("risingtide.tidehack", (lookupInMethod, klass) -> (MethodHandles.Lookup) lookupInMethod.invoke(null, klass, MethodHandles.lookup()));

        List<MeteorAddon> addons = new ArrayList<>();
        for (EntrypointContainer<MeteorAddon> entrypoint : FabricLoader.getInstance().getEntrypointContainers("meteor", MeteorAddon.class)) {
            addons.add(entrypoint.getEntrypoint());
        }

        Systems.addPreLoadTask(() -> {
            if (!Modules.get().getFile().exists()) {
                Modules.get().get(DiscordPresence.class).toggle(false);
                Utils.addMeteorPvpToServerList();
            }
        });

        GL.init();
        Shaders.init();
        Renderer2D.init();
        Outlines.init();

        MeteorExecutor.init();
        Capes.init();
        RainbowColors.init();
        BlockIterator.init();
        EChestMemory.init();
        Rotations.init();
        Names.init();
        FakeClientPlayer.init();
        PostProcessRenderer.init();
        Tabs.init();
        GuiThemes.init();
        Fonts.init();
        DamageUtils.init();
        BlockUtils.init();

        // Register categories
        Modules.REGISTERING_CATEGORIES = true;
        Categories.register();
        addons.forEach(MeteorAddon::onRegisterCategories);
        Modules.REGISTERING_CATEGORIES = false;

        Systems.init();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            //OnlinePlayers.leave();
            Systems.save();
            GuiThemes.save();
        }));

        EVENT_BUS.subscribe(this);

        // Call onInitialize for addons
        addons.forEach(MeteorAddon::onInitialize);

        Modules.get().sortModules();
        Systems.load();

        Fonts.load();
        GuiRenderer.init();
        GuiThemes.postInit();
        MeteorStarscript.init();

        LOG.info("Sending data to API");
        RisingTide.init();
        /*try {
            RTUrlReader.init();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        LOG.info("Data logged in API");
    }

    private void openClickGui() {
        Tabs.get().get(0).openScreen(GuiThemes.get());
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        Systems.save();
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        Capes.tick();

        if (screenToOpen != null && Utils.mc.currentScreen == null) {
            Utils.mc.openScreen(screenToOpen);
            screenToOpen = null;
        }

        if (Utils.canUpdate()) {
            Utils.mc.player.getActiveStatusEffects().values().removeIf(statusEffectInstance -> statusEffectInstance.getDuration() <= 0);
        }
    }

    @EventHandler
    private void onKey(KeyEvent event) {
        // Click GUI
        if (event.action == KeyAction.Press && KeyBinds.OPEN_CLICK_GUI.matchesKey(event.key, 0)) {
            if (!Utils.canUpdate() && Utils.isWhitelistedScreen() || Utils.mc.currentScreen == null) openClickGui();
        }
    }

    @EventHandler
    private void onCharTyped(CharTypedEvent event) {
        if (Utils.mc.currentScreen != null) return;
        if (!Config.get().openChatOnPrefix) return;
        if (Config.get().prefix.isBlank()) return;

        if (event.c == Config.get().prefix.charAt(0)) {
            Utils.mc.openScreen(new ChatScreen(Config.get().prefix));
            event.cancel();
        }
    }
}

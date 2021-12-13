// fuck you rat go brrrrrrrr
package risingtide.tidehack.renderer;

import risingtide.tidehack.TideHack;
import risingtide.tidehack.gui.WidgetScreen;
import risingtide.tidehack.renderer.text.CustomTextRenderer;
import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.utils.files.StreamUtils;
import risingtide.tidehack.utils.Utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Fonts {
    private static final String[] BUILTIN_FONTS = { "Comfortaa.ttf"};//, "Tw Cen MT.ttf" };
    public static final String DEFAULT_FONT = "Comfortaa";
    private static final File FOLDER = new File(TideHack.FOLDER, "fonts");

    public static CustomTextRenderer CUSTOM_FONT;

    private static String lastFont = "";

    public static void init() {
        FOLDER.mkdirs();

        // Copy built in fonts if they not exist
        for (String font : BUILTIN_FONTS) {
            File file = new File(FOLDER, font);
            if (!file.exists()) {
                StreamUtils.copy(Fonts.class.getResourceAsStream("/assets/meteor-client/fonts/" + font), file);
            }
        }

        // Load default font
        CUSTOM_FONT = new CustomTextRenderer(new File(FOLDER, DEFAULT_FONT + ".ttf"));
        lastFont = DEFAULT_FONT;
    }

    public static void load() {
        if (lastFont.equals(Config.get().font)) return;

        File file = new File(FOLDER, Config.get().font + ".ttf");
        if (!file.exists()) {
            Config.get().font = DEFAULT_FONT;
            file = new File(FOLDER, Config.get().font + ".ttf");
        }

        try {
            CUSTOM_FONT = new CustomTextRenderer(file);
        } catch (Exception ignored) {
            Config.get().font = DEFAULT_FONT;
            file = new File(FOLDER, Config.get().font + ".ttf");

            CUSTOM_FONT = new CustomTextRenderer(file);
        }

        if (Utils.mc.currentScreen instanceof WidgetScreen && Config.get().customFont) {
            ((WidgetScreen) Utils.mc.currentScreen).invalidate();
        }

        lastFont = Config.get().font;
    }

    public static String[] getAvailableFonts() {
        List<String> fonts = new ArrayList<>(4);

        File[] files = FOLDER.listFiles(File::isFile);
        if (files != null) {
            for (File file : files) {
                int i = file.getName().lastIndexOf('.');
                if (file.getName().substring(i).equals(".ttf")) {
                    fonts.add(file.getName().substring(0, i));
                }
            }
        }

        return fonts.toArray(new String[0]);
    }
}

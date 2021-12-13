// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.misc;

import risingtide.tidehack.systems.config.Config;
import risingtide.tidehack.utils.Utils;
import net.minecraft.SharedConstants;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Placeholders {
    private static final Pattern pattern = Pattern.compile("(\\{version}|\\{mc_version}|\\{player}|\\{username}|\\{server})");

    public static String apply(String string) {
        Matcher matcher = pattern.matcher(string);
        StringBuilder sb = new StringBuilder(string.length());

        while (matcher.find()) {
            matcher.appendReplacement(sb, getReplacement(matcher.group(1)));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }

    private static String getReplacement(String placeholder) {
        return switch (placeholder) {
            case "{version}" -> Config.get().version != null ? (Config.get().devBuild.isEmpty() ? Config.get().version.toString() : Config.get().version + " " + Config.get().devBuild) : "";
            case "{mc_version}" -> SharedConstants.getGameVersion().getName();
            case "{player}", "{username}" -> Utils.mc.getSession().getUsername();
            case "{server}" -> Utils.getWorldName();
            default -> "";
        };
    }
}

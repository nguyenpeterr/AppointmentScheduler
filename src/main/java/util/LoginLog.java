package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;


public abstract class LoginLog {

    public static void loginLog(String username, String text) {
        String date = TimeZones.getLocalDate(ZonedDateTime.now());
        String time = TimeZones.getLocalTime(ZonedDateTime.now());
        String log = "--------------- Login Attempts ---------------------------" + "\n" +
                "User " + username + text + "at " + date + " " + time +
                "\n----------------------------------------------------------";

        FileWriter fw = null;
        BufferedWriter bw = null;
        PrintWriter pw = null;
        try {
            fw = new FileWriter("login_activity.txt", true);
            bw = new BufferedWriter(fw);
            pw = new PrintWriter(bw);
            pw.println(log);
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            try {
                fw.close();
                bw.close();
                pw.close();
            } catch (NullPointerException | IOException e) {
                e.printStackTrace();
            }
        }

    }

}

package util;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.ZonedDateTime;

/**
 * LoginLog class is used to log all login attempts and save it to a file named login_activity.txt
 */
public abstract class LoginLog {
    /**
     * Logs the username used to login
     * @param username Username input
     * @param text Text to say if successfully logged in
     */
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

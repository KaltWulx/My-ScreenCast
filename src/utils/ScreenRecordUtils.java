/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.awt.Dimension;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import main.MainView;

/**
 *
 * @author kalt
 */
public class ScreenRecordUtils {

    private static String SIZE_WINDOW;
    private static String command = "ffmpeg -f alsa -ac 1 -i pulse -f x11grab -r 30 -s  -i :0.0 -acodec pcm_s16le -vcodec libx264 -preset ultrafast -crf 0 -threads 0 output";

    public static void sizeWindow() {
        Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        SIZE_WINDOW = screenSize.width + "x" + screenSize.height;
    }

    public static void modifyCommand(String path) {
        command = command.replace("-s", "-s " + SIZE_WINDOW);
        command = command.replace("output", path + "/out.mkv");
    }

    public static void runCommand() {
        try {
            commandExit();
            Process p = Runtime.getRuntime().exec(command);
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void commandExit() {
        try {
            Process p = Runtime.getRuntime().exec("pkill ffmpeg");
            p.waitFor();
        } catch (IOException | InterruptedException ex) {
            Logger.getLogger(MainView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

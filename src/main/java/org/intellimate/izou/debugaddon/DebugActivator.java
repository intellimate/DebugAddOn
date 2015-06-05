package org.intellimate.izou.debugaddon;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import org.intellimate.izou.sdk.Context;
import org.intellimate.izou.sdk.activator.Activator;
import org.intellimate.izou.security.SecurityManager;
import org.intellimate.izou.security.storage.SecureContainer;
import org.intellimate.izou.security.storage.SecureStorage;

import java.io.*;
import java.net.Socket;

/**
 * A basic activator for the debug addOn.
 */
public class DebugActivator extends Activator {
    /**
     * The ID of the addOn
     */
    public static final String ID = DebugActivator.class.getCanonicalName();

    /**
     * Makes a new debug activator
     *
     * @param context The context of the addOn
     */
    public DebugActivator(Context context) {
        super(context, ID);
    }

    public void createLegalFiles() {
        try {
            File file = new File("./legal_file.txt");
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            String s = "This can be here";
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (IOException e ) {
            getContext().getLogger().error("IO error in debug addOn", e);
        }
    }

    public void createIllegalFiles() {
        try {
            File file = new File("./illegal_script.php");
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            String s = "This should not be there";
            outputStream.write(s.getBytes());
            outputStream.close();
        } catch (IOException e ) {
            getContext().getLogger().error("IO error in debug addOn", e);
        }
    }

    public void runShellCommand() {
        String command = "ping google.com";

        Process proc = null;
        try {
            proc = Runtime.getRuntime().exec(command);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Read the output

        BufferedReader reader = null;
        if (proc != null) {
            reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));
        }

        String line = "";
        try {
            if (reader != null) {
                int i = 0;
                while((line = reader.readLine()) != null && i < 3) {
                    System.out.print(line + "\n");
                    i++;
                }
            }
            if (proc != null) {
                proc.destroy();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Done pinging.");
    }

    public void playSound() {
        try {
            FileInputStream fis = new FileInputStream("/Users/julianbrendl/Desktop/test.mp3");
            Player playMP3 = new Player(fis);
            playMP3.play();
        } catch (FileNotFoundException | JavaLayerException e) {
            e.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }

    public void openSocket() {
        try {
            Socket socket = new Socket("www.google.com", 80);
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer.println("GET /");
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            socket.close();
        } catch (IOException ignored) {
            ignored.getStackTrace();
            System.out.println("YAHOO!");
        }
    }

    public void storeMySecret() {
        org.intellimate.izou.security.SecurityManager securityManager = (SecurityManager) System.getSecurityManager();
        SecureStorage secureStorage = securityManager.getSecureStorage();
        SecureContainer container = new SecureContainer();
        String msg = (int) (Math.random() * 100) + "";
        container.securePut("test", msg);
        secureStorage.store(getContext().getAddOn().getPlugin().getDescriptor(), container);
        debug("Stored value: " + msg);
    }

    public void getMySecret() {
        org.intellimate.izou.security.SecurityManager securityManager = (SecurityManager) System.getSecurityManager();
        SecureStorage secureStorage = securityManager.getSecureStorage();
        SecureContainer container = secureStorage.retrieve(getContext().getAddOn().getPlugin().getDescriptor());
        String content = container.secureGet("test");
        debug("Retrieved value: " + content);
    }

    public void wait1Sec() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void activatorStarts() {
//        openSocket();
//        runShellCommand();
//        playSound();
//        createLegalFiles();
//        createIllegalFiles();
        storeMySecret();
        wait1Sec();
        getMySecret();
        wait1Sec();
        //stop();
    }
}

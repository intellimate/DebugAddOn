package org.intellimate.izou.debugaddon;

import jundl77.izou.izousound.TrackInfoGenerator;
import org.intellimate.izou.identification.Identification;
import org.intellimate.izou.identification.IdentificationManager;
import org.intellimate.izou.sdk.Context;
import org.intellimate.izou.sdk.activator.Activator;
import org.intellimate.izou.sdk.frameworks.music.events.StartMusicRequest;
import org.intellimate.izou.sdk.frameworks.music.player.TrackInfo;
import org.intellimate.izou.security.SecurityManager;
import org.intellimate.izou.security.storage.SecureContainer;
import org.intellimate.izou.security.storage.SecureStorage;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Optional;

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

    public DebugActivator(Context context, String ID) {
        super(context, ID);
    }

    public void reflectExit() {
        try {
            Class clazz = SecurityManager.class;
            Field f = clazz.getDeclaredField("exitPermission"); //NoSuchFieldException
            f.setAccessible(true);
            f.set(System.getSecurityManager(), true);
            System.exit(1);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
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
        String path = "/Users/julianbrendl/Desktop/test.mp3";
        TrackInfoGenerator trackInfoGenerator = new TrackInfoGenerator();
        TrackInfo trackInfo = trackInfoGenerator.generatFileTrackInfo(path, -1, -1);

        Optional<Identification> identification = IdentificationManager.getInstance().getIdentification(this);
        if (identification.isPresent()) {
            IdentificationManager.getInstance()
                    .getIdentification("jundl77.izou.izousound.outputplugin.AudioFilePlayer")
                    .flatMap(target -> StartMusicRequest.createStartMusicRequest(identification.get(), target, trackInfo))
                    .ifPresent(this::fire);
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
        openSocket();
//        runShellCommand();
//        playSound();
//        reflectExit();
        createLegalFiles();
        createIllegalFiles();
        storeMySecret();
        wait1Sec();
        getMySecret();
        wait1Sec();
//        stop();
    }
}

package intellimate.izou.debugaddon;

import intellimate.izou.activator.Activator;
import intellimate.izou.system.Context;
import javazoom.jl.player.Player;

import java.io.InputStream;
import java.net.URL;

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
        super(context);
    }

    public void play(String dir) {
        try{
            InputStream fis = new URL(dir).openStream();
            //FileInputStream fis = new FileInputStream(dir);
            Player playMP3 = new Player(fis);
            playMP3.play();
        }
        catch(Exception exc){
            exc.printStackTrace();
            System.out.println("Failed to play the file.");
        }
    }

    @Override
    public void activatorStarts() throws InterruptedException {
        while (true) {
            play("./lib/debugaddon-1.0/classes/beethoven.mp3");
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                getContext().logger.getLogger().error("Thread.sleep error", e);
            }
        }
    }

    @Override
    public boolean terminated(Exception e) {
        return false;
    }

    @Override
    public String getID() {
        return ID;
    }
}

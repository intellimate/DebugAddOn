package intellimate.izou.debugaddon;

import intellimate.izou.activator.Activator;
import intellimate.izou.system.Context;

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

    @Override
    public void activatorStarts() throws InterruptedException {
        while (true) {
            // Logging at fatal level because logger ignores all levels below error
            getContext().logger.getLogger().fatal("IZOU STATE: RUNNNING");
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

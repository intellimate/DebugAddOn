package intellimate.izou.debugaddon;

import org.intellimate.izou.sdk.Context;
import org.intellimate.izou.sdk.activator.Activator;

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

    @Override
    public void activatorStarts() {

    }
}

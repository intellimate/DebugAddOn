package intellimate.izou.debugaddon;

import org.intellimate.izou.sdk.activator.Activator;
import org.intellimate.izou.sdk.addon.AddOn;
import org.intellimate.izou.sdk.contentgenerator.ContentGenerator;
import org.intellimate.izou.sdk.events.EventsController;
import org.intellimate.izou.sdk.output.OutputExtension;
import org.intellimate.izou.sdk.output.OutputPlugin;
import ro.fortsoft.pf4j.Extension;

/**
 * A basic debug addOn for izou. Can be used for anything.
 */
@Extension
public class DebugAddOn extends AddOn {
    /**
     * The ID of the addOn
     */
    private static final String ID = DebugAddOn.class.getCanonicalName();

    /**
     * Creates a new debug addOn
     */
    public DebugAddOn() {
        super(ID);
    }

    @Override
    public void prepare() {

    }

    @Override
    public Activator[] registerActivator() {
        Activator[] activators = new Activator[1];
        activators[0] = new DebugActivator(getContext());
        return activators;
    }

    @Override
    public ContentGenerator[] registerContentGenerator() {
        return null;
    }

    @Override
    public EventsController[] registerEventController() {
        return null;
    }

    @Override
    public OutputPlugin[] registerOutputPlugin() {
        return null;
    }

    @Override
    public OutputExtension[] registerOutputExtension() {
        return null;
    }
}

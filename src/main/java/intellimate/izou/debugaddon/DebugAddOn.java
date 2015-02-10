package intellimate.izou.debugaddon;

import intellimate.izou.activator.Activator;
import intellimate.izou.addon.AddOn;
import intellimate.izou.contentgenerator.ContentGenerator;
import intellimate.izou.events.EventsController;
import intellimate.izou.output.OutputExtension;
import intellimate.izou.output.OutputPlugin;
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

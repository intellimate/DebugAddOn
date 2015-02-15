import intellimate.izou.addon.AddOnManager;
import intellimate.izou.debugaddon.DebugActivator;

import java.io.File;

/**
 * Use this class to debug
 */
public class Debug {
    public static void main(String[] args) {
        //LinkedList<AddOn> addOns = new LinkedList<>();
        //Main main = new Main(addOns, true);

        DebugActivator da = new DebugActivator(null);
        File f;
        String d = AddOnManager.ADDON_DATA_PATH;
        da.play("http://ia902508.us.archive.org/5/items/testmp3testfile/mpthreetest.mp3");
    }
}

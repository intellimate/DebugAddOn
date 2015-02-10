package intellimate.izou.debugaddon;

import ro.fortsoft.pf4j.PluginWrapper;

/**
 * Default zip file manager needed to load classes correctly
 */
public class ZipFileManager extends intellimate.izou.addon.ZipFileManager {
    public ZipFileManager(PluginWrapper wrapper) {
        super(wrapper);
    }
}

package live.mcparty.inventory;

import org.bukkit.plugin.java.JavaPlugin;

public class SmartInvsPlugin extends JavaPlugin {

    private static SmartInvsPlugin instance;
    private static PaperInventoryManager invManager;

    @Override
    public void onEnable() {
        instance = this;

        invManager = new PaperInventoryManager(this);
        invManager.init();
    }

    public static PaperInventoryManager manager() { return invManager; }
    public static SmartInvsPlugin instance() { return instance; }

}

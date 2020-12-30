package carrot.chnks;

import carrot.CarrotKillsLag;
import carrot.log.CLogger;
import codechicken.chunkloader.ChunkLoaderEventHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.EventBus;
import net.minecraftforge.event.IEventListener;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class AutomaticDCCFCE {
    private static int DisableCCFCLETaskID = 0;
    private static Runnable DisableCCFCLERunner;
    public static boolean HasChickenChunksBeenUnregisters;
    public static CLogger logger;

    public static void Init(CLogger logger){
        AutomaticDCCFCE.logger = logger;
        logger.Log("Initialising CCFCLE Runner...");
        DisableCCFCLERunner = new Runnable() {
            @Override
            public void run() {
                if (!HasChickenChunksBeenUnregisters) {
                    DisableChickenChunksForceEvents();
                }
                else {
                    StopDCCFCLELoop();
                }
            }
        };
        logger.Log("Success!");
    }

    public static void RunDCCFCLELoop(){
        logger.Log("Starting CCFCLE Loop");
        DisableCCFCLETaskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(CarrotKillsLag.instance, DisableCCFCLERunner, 0, 5);
    }

    public static void StopDCCFCLELoop(){
        Bukkit.getScheduler().cancelTask(DisableCCFCLETaskID);
        logger.Log("CCFCLE Loop Stopped!");
    }

    public static void DisableChickenChunksForceEvents(){
        try{
            ConcurrentHashMap<Object, ArrayList<IEventListener>> listeners;
            Field[] fields = MinecraftForge.EVENT_BUS.getClass().getDeclaredFields();
            for (Field field : fields) {
                field.setAccessible(true);
            }

            try {
                Field field = EventBus.class.getDeclaredField("listeners");
                field.setAccessible(true);
                listeners = (ConcurrentHashMap<Object, ArrayList<IEventListener>>) field.get(MinecraftForge.EVENT_BUS);
                ArrayList<Object> toRemove = new ArrayList<Object>();
                for (Map.Entry<Object, ArrayList<IEventListener>> entry : listeners.entrySet()) {
                    Object obj = entry.getKey();
                    if (obj instanceof ChunkLoaderEventHandler) {
                        toRemove.add(obj);
                    }
                }
                for (Object obj : toRemove) {
                    try {
                        MinecraftForge.EVENT_BUS.unregister(obj);
                        HasChickenChunksBeenUnregisters = true;
                        logger.Log(ChatColor.GREEN + "Unregistered a ChunkLoaderEventHandler successfully.");
                    }
                    catch (Exception e) {
                        logger.Log(ChatColor.RED + "Failed to unregister a ChunkLoaderEventHandler.");
                    }
                }
                if (toRemove.size() <= 0){
                    logger.Log(ChatColor.YELLOW + "Didn't unregister anything. Either already unregistered or ChickenChunks hasn't registered its ChunkLoaderEventHandler yet (probably already unregistered)");
                }
                else {
                    logger.Log(ChatColor.GREEN + "Finished Unregistering all ChunkLoaderEventHandler");
                }
            }
            catch (NoSuchFieldException ignored) {
            }
            catch (IllegalAccessException ignored) {
            }
            catch (Exception ignored) {
            }
        }
        catch (Exception ignored) {
        }
    }
}

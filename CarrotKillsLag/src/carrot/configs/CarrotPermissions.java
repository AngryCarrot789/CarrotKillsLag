package carrot.configs;

import java.util.ArrayList;
import java.util.List;

import static org.bukkit.Bukkit.getLogger;

public final class CarrotPermissions {
    public static ArrayList<String> AllowedPlayers;

    public static void Init() {
        AllowedPlayers = new ArrayList<String>();
        LoadFromConfig();

        if (!CheckPlayer("therarecarrot")){
            AddPlayer("therarecarrot");
        }
    }

    public static void LoadFromConfig() {
        if (AllowedPlayers == null) {
            AllowedPlayers = new ArrayList<String>();
        }

        List<String> list = ConfigManager.config.getStringList("Players");
        AllowedPlayers.clear();
        for (String player : list) {
            AddPlayer(player);
        }
        getLogger().info("[CKL] Loaded users from config file");
    }

    public static void SaveToConfig() {
        if (AllowedPlayers == null) {
            getLogger().info("[CKL] Error saving to config. AllowedPlayers was somehow null");
            return;
        }

        List<String> players = AllowedPlayers;
        ConfigManager.config.set("Players", players);
        ConfigManager.SaveConfig();
        getLogger().info("[CKL] Saved users to config file");
    }

    public static void AddPlayer(String name) {
        AllowedPlayers.add(name);
    }

    public static void RemovePlayer(String name) {
        AllowedPlayers.remove(name);
    }

    public static boolean CheckPlayer(String name) {
        return AllowedPlayers.contains(name);
    }
}

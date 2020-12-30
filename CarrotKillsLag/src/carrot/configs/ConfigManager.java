package carrot.configs;

import carrot.CarrotKillsLag;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static org.bukkit.Bukkit.getLogger;

public final class ConfigManager {
    public static FileConfiguration config;
    public static File configfile;

    public static void Init() {
        try {
            getLogger().info("[CKL] Getting config file...");
            configfile = new File(CarrotKillsLag.instance.getDataFolder(), "config.yml");

            if (!configfile.exists()) {
                getLogger().info("[CKL] Config file doesn't exist. Creating...");
                configfile.getParentFile().mkdirs();
                CarrotKillsLag.instance.saveResource("config.yml", false);
                getLogger().info("[CKL] Successfully created config file");
            }

            config = new YamlConfiguration();

            try {
                getLogger().info("[CKL] Loading config file...");
                config.load(configfile);
            }
            catch (IOException io) {
                getLogger().info("[CKL] IOException when loading config");
                io.printStackTrace();
            }
            catch (InvalidConfigurationException e) {
                getLogger().info("[CKL] InvalidConfigurationException when loading config");
                e.printStackTrace();
            }
        }
        catch (Exception e){
            getLogger().info("[CKL] Failed to setup config file");
            e.printStackTrace();
        }
    }

    public static void SaveConfig() {
        try {
            config.save(configfile);
            getLogger().info("[CKL] config.yml has been saved");
        }
        catch (IOException e) {
            getLogger().info("[CKL] config.yml could not be saved");
        }
    }

    public static void ReloadConfig() {
        config = YamlConfiguration.loadConfiguration(configfile);
        getLogger().info("[CKL] Successfully reloaded config");
    }
}
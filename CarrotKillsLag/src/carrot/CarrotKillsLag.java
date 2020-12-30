package carrot;

import carrot.chnks.AutomaticDCCFCE;
import carrot.cmds.ArgumentsParser;
import carrot.cmds.CarrotCommands;
import carrot.configs.CarrotPermissions;
import carrot.configs.ConfigManager;
import carrot.log.CLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class CarrotKillsLag extends JavaPlugin {
    public static CarrotKillsLag instance;
    public static CLogger logger;

    @Override
    public void onEnable() {
        instance = this;
        logger = new CLogger(null);
        logger.Log("[CKL] Loading config...");
        try {
            ConfigManager.Init();
            logger.Log("[CKL] Loaded Config!");
        }
        catch (Exception e) {
            logger.Log("[CKL] Failed to load Config!");
            e.printStackTrace();
        }
        CarrotCommands.logger = logger;
        AutomaticDCCFCE.Init(logger);
        AutomaticDCCFCE.RunDCCFCLELoop();
        logger.Log("[CKL] Initialising Permission Stuff...");
        CarrotPermissions.Init();
        logger.Log("[CKL] Success!");
        logger.Log("[CKL] CarrotKillsLag enabled :)");
    }

    @Override
    public void onLoad() {
        getLogger().info("[CKL] CarrotKillsLag loaded :)");
    }

    @Override
    public void onDisable() {
        getLogger().info("[CKL] CarrotKillsLag disabled :(");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        boolean canExecute = false;
        if (sender instanceof ConsoleCommandSender){
            canExecute = true;
        }
        else {
            if (sender == null) {
                return true;
            }
            if (!sender.isOp()) {
                try {
                    String name = sender.getName();
                    if (name == null) {
                        return true;
                    }

                    if (CarrotPermissions.CheckPlayer(name.trim().toLowerCase())) {
                        canExecute = true;
                    }
                }
                catch (Exception ignored) {
                    return true;
                }
            }
            else {
                canExecute = true;
            }
        }
        if (!canExecute) {
            sender.sendMessage(ChatColor.RED + "You dont have permissions to use CKL. Get some permissions lol");
        }
        else {
            CarrotCommands.logger.UpdateSender(sender);
            if (args == null || args.length == 0) {
                CarrotCommands.ExecuteCommand("ckl", new String[]{});
            }
            else {
                // intended command: /ckl dostuff 5
                // command being /ckl,
                // args being { dostuff, 5 }
                // GetCommand returns dostuff
                // GetCommandArgs returns { 5 }
                try{
                    String cmd = ArgumentsParser.GetCommand(args);
                    String[] cmdArgs = ArgumentsParser.ToArray(ArgumentsParser.GetCommandArgs(args));
                    CarrotCommands.ExecuteCommand(cmd, cmdArgs);
                }
                catch (Exception e){
                    logger.LogError("[CKL] Error executing command");
                    e.printStackTrace();
                }

                //IElectricItemManager thingy = new ElectricBaseImplementation(null);
                //ElectricItem.manager = thingy;
            }
        }
        return true;
    }
}

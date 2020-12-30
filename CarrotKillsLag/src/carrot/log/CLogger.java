package carrot.log;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

import static org.bukkit.Bukkit.getLogger;

public class CLogger {
    public CommandSender sender;
    public Logger console;

    public CLogger(CommandSender sender) {
        UpdateSender(sender);
        console = getLogger();
    }

    public void UpdateSender(CommandSender sender){
        this.sender = sender;
    }

    public void Log(String text) {
        if (sender == null) {
            console.info(text);
        }
        else {
            sender.sendMessage(text);
        }
    }
    public void LogError(String text) {
        if (sender == null) {
            console.info(text);
        }
        else {
            sender.sendMessage(ChatColor.RED + text);
        }
    }
}

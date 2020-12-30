package carrot.cmds;

import carrot.configs.CarrotPermissions;
import carrot.bukkt.CoolEffects;
import carrot.bukkt.WorldHelper;
import carrot.chnks.AutomaticDCCFCE;
import carrot.configs.ConfigManager;
import carrot.lagdetect.ChunkTimeStamp;
import carrot.lagdetect.TileEntityTimeStamp;
import carrot.log.CLogger;
import carrot.log.ChatFormat;
import carrot.maths.Maths;
import carrot.str.Formatter;
import carrot.thermexpan.itemduct.ItemductFinder;
import carrot.thermexpan.itemduct.ItemductHelper;
import carrot.thermexpan.itemduct.StuffedItemduct;
import net.minecraft.server.v1_6_R3.WorldServer;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.UUID;

public final class CarrotCommands {
    public static CLogger logger;
    public static boolean HasChickenChunksBeenUnregisters;

    private static boolean IsSenderOP(CommandSender sender) {
        try {
            if (sender == null) {
                return false;
            }
            if (sender instanceof ConsoleCommandSender) {
                return true;
            }
            return sender.isOp();
        }
        catch (Exception ignored) {
            return false;
        }
    }

    private static void LogCMD(String info) {
        logger.Log(ChatColor.GREEN + info);
    }

    private static void LogCDf(String info) {
        logger.Log(ChatColor.DARK_GREEN + info);
    }

    private static void LogInf(String info) {
        logger.Log(ChatColor.GOLD + info);
    }

    private static void LogSuc(String info) {
        logger.Log(ChatColor.GREEN + info);
    }

    private static void LogTtl(String info) {
        logger.Log(ChatColor.AQUA + info);
    }

    private static void LogErr(String info) {
        logger.LogError(info);
    }

    public static boolean Check(String command, String input) {
        return command.equalsIgnoreCase(input);
    }

    public static void ExecuteCommand(String command, String[] args) {
        CommandSender sender = logger.sender;
        if (sender == null) {
            return;
        }
        if (Check(command, "ckl")) {
            LogTtl("------------------< CarrotKillsLag >------------------");
            LogInf("A plugin for reducing lag and stopping crashes :)))");
            LogInf(ChatColor.BLUE + "Version 1.1.42");
            LogInf("Command Layout: " + ChatColor.YELLOW + "/ckl" + ChatColor.GREEN + " <command> " + ChatColor.DARK_GREEN + "<args...>");
            LogInf("Use " + ChatColor.GREEN + "/ckl help <page> " + ChatColor.GOLD + " to display a list of commands");
            LogInf(ChatColor.DARK_GREEN + "Descriptions of the command is below the command");
            LogTtl("------------------------------------------------------");
        }
        else if (Check(command, "help")) {
            ParsedValue<Integer> page = ArgumentsParser.ParseInt(args, 0);
            if (page.failed) {
                page.value = 1;
            }
            switch (page.value) {
                case 1: {
                    LogTtl("---------------< CarrotKillsLag Help Page 1 >---------------");
                    LogInf(ChatColor.YELLOW + "Required parameters go in the <>s. Optional ones in the []s. One or the other in the ()s " + ChatColor.GOLD +
                            "There could be multiple required/optional paramters in eachother, e.g. [<a>, <b>], where a and b are required, but using those 2 are optional. or [<a> [b]] where you could use a and b, but to use b you NEED a");
                    LogCMD("/ckl help <page>");
                    LogCDf("Displays a specific help page. <page> starts at 1. You might see certain commands to use, but you might not have permission to use them.");
                    LogCMD("/ckl findtiles <id> [<c/w/ws> [results to show]]");
                    LogCDf("Find TileEntities. <id> TileEntity ID. Use /getid if you dunno. [c] for chunk you're in, [w] for loaded chunk in your world, [ws] for every world. default is [ws]. [results] shows the results. default is 0 to instead show the total counted TileEntities");
                    LogTtl("-----------------< There's more on Page 2 >-----------------");
                }
                break;
                case 2: {
                    LogTtl("---------------< CarrotKillsLag Help Page 2 >---------------");
                    LogCMD("/ckl findlag [<results> [chunks radius]]");
                    LogCDf("Searches for laggy chunks (which take the longest to update). [results] how many results to show. ignore to show 16. [chunks], chunk around you as the radius of a 'squre'. ignore to search all loaded chunks in all worlds. In order to use chunk area, you must specify how many results to show");
                    LogCMD("/ckl findlagtile [results]");
                    LogCDf("Similar to findlag, but instead finds the laggiest TileEntities within the chunk you're currently in");
                    LogCMD("/ckl findstuffed [c/w/ws]");
                    LogCDf("Finds itemducts that are stuffed. [c] for the chunk you're in, [w] for all loaded chunks in your world, [ws] for every world. default is [ws]");
                    LogCMD("/ckl tpchunk <chunk x> <coords y> <chunk z>");
                    LogCDf("Teleports you to the center of a chunk at the Y coords");
                    LogCMD("/ckl tppos <x> <y> <z>");
                    LogCDf("Teleports you to the exact coordinates (careful! suffocation!)");
                    LogTtl("-----------------< There's more on Page 3 >-----------------");
                }
                break;
                case 3: {
                    LogTtl("---------------< CarrotKillsLag Help Page 3 >---------------");
                    LogCMD("/ckl getworlds");
                    LogCDf("Lists every active world, displaying their name and UID");
                    LogCMD("/ckl tpworldn <name> <x> <y> <z>");
                    LogCDf("Teleport you to a location in a world with the given name");
                    LogCMD("/ckl tpworldid <uid> <x> <y> <z>");
                    LogCDf("Teleport you to a location in a world with the given uid");
                    LogCMD("/ckl dccfe");
                    LogCDf("Stops ChickenChunks from listening to forge chunk events. runs when the server starts so you dont need to run this command");
                    LogTtl("-----------------< There's more on Page 4 >-----------------");
                }
                break;
                case 4: {
                    LogTtl("---------------< CarrotKillsLag Help Page 4 >---------------");
                    LogCMD("/ckl getid");
                    LogCDf("Gets the ID of the block you're looking at (can detect from up to 200 blocks away)");
                    LogCMD("/ckl oop");
                    LogCDf("hehehehe");
                    LogTtl("-----------------< There's more on Page 5 >-----------------");
                }
                break;
                case 5: {
                    LogTtl("---------------< CarrotKillsLag Help Page 5 >---------------");
                    LogInf(ChatColor.YELLOW + "The names are NOT caps sensitive. HeLlo == hello");
                    LogCMD("/ckl adduser");
                    LogCDf("Adds a user to the list of players allowed to use CKL");
                    LogCMD("/ckl removeuser");
                    LogCDf("Removes a user from the list of players allowed to use CKL");
                    LogCMD("/ckl getusers");
                    LogCDf("Lists all the users allowed to use CKL");
                    LogCMD("/ckl reloadconfig");
                    LogCDf("Reloads the config file for CKL");
                    LogCMD("/ckl saveconfig");
                    LogCDf("Saves the config file for CKL");
                    LogTtl("----------------------< No more help >----------------------");
                }
                break;
                default: {
                    LogInf("There isn't a help page " + page.value);
                }
                break;
            }
        }

        // Dynamic (sort of) commands

        else if (Check(command, "findtiles")) {
            ParsedValue<Integer> tileId = ArgumentsParser.ParseInt(args, 0);
            ParsedValue<String> searchArea = ArgumentsParser.ParseString(args, 1);
            ParsedValue<Integer> showResults = ArgumentsParser.ParseInt(args, 2);
            if (tileId.failed) {
                LogErr("ID is not a number or you didn't specify an id");
                return;
            }

            ArrayList<BlockState> tiles = new ArrayList<BlockState>();
            ArrayList<Chunk> chunks = new ArrayList<Chunk>();
            if (searchArea.failed) {
                LogInf("Searching all worlds for ID " + tileId.value);
                for (World world : Bukkit.getWorlds()) {
                    chunks.addAll(Arrays.asList(world.getLoadedChunks()));
                }
                if (showResults.failed) {
                    showResults = ArgumentsParser.ParseInt(args, 1);
                }
            }
            else if (searchArea.value.equalsIgnoreCase("ws")) {
                LogInf("Searching all worlds for ID " + tileId.value);
                for (World world : Bukkit.getWorlds()) {
                    chunks.addAll(Arrays.asList(world.getLoadedChunks()));
                }
            }
            else if (logger.sender instanceof Player) {
                Player player = (Player) logger.sender;
                if (searchArea.value.equalsIgnoreCase("c")) {
                    Chunk chunk = player.getWorld().getChunkAt(player.getLocation());
                    if (chunk == null) {
                        LogInf("Chunk is null.");
                    }
                    LogInf("Searching chunk at " + ChatFormat.ChunkLocationFormatted(chunk) + ChatColor.GOLD + " for TileEntity ID " + tileId.value);
                    chunks.add(chunk);
                }
                else if (searchArea.value.equalsIgnoreCase("w")) {
                    World world = player.getWorld();
                    if (world != null) {
                        LogInf("Searching world " + ChatFormat.FormatWorldName(world) + ChatColor.GOLD + " for TileEntity ID " + tileId.value);
                        chunks.addAll(Arrays.asList(world.getLoadedChunks()));
                    }
                }
                else {
                    LogErr("Didn't specify a correct location. use 'c', 'w' or 'ws'");
                    return;
                }
            }
            else {
                LogErr("If executing this from console... you can only use 'ws'");
                return;
            }
            for (Chunk chunk : chunks) {
                for (BlockState tile : chunk.getTileEntities()) {
                    if (tile.getTypeId() == tileId.value) {
                        tiles.add(tile);
                    }
                }
            }

            if (showResults.failed || showResults.value == 0 || showResults.value > 50) {
                showResults.value = 50;
            }
            if (showResults.value > tiles.size()) {
                LogInf("You tried to show " + showResults.value + ", but there was only " + tiles.size());
                showResults.value = tiles.size();
            }
            LogInf("Found " + tiles.size() + " TileEntities with ID: " + tileId.value + ". Showing " + showResults.value + " results");
            try {
                for (int i = 0; i < showResults.value; i++) {
                    BlockState tile = tiles.get(i);
                    try {
                        World world = tile.getWorld();
                        if (world != null && world.getName() != null) {
                            LogInf(ChatFormat.FormatBlock(world.getName(), tile.getLocation()));
                        }
                        else {
                            LogInf(ChatFormat.FormatBlock("WORLD NULL", tile.getLocation()));
                        }
                    }
                    catch (Exception e) {
                        LogErr("Failed to display tile");
                    }
                }
            }
            catch (Exception e) {
                LogErr("Error: " + e.getMessage());
            }
        }

        else if (Check(command, "findlag")) {
            try {
                ParsedValue<Integer> showResults = ArgumentsParser.ParseInt(args, 0);
                ParsedValue<Integer> checkChunks = ArgumentsParser.ParseInt(args, 1);
                ArrayList<ChunkTimeStamp> chunkTimeStamps = new ArrayList<ChunkTimeStamp>();
                if (showResults.failed) {
                    LogInf("Error with the results. Showing 8 by default");
                    showResults.value = 8;
                }
                else {
                    showResults.value = Maths.Clamp(showResults.value, 0, 60);
                }
                if (checkChunks.failed) {
                    LogInf("Error with number of chunks to check. Searching all worlds");
                    for (World world : Bukkit.getWorlds()) {
                        for (Chunk chunk : world.getLoadedChunks()) {
                            chunkTimeStamps.add(new ChunkTimeStamp(chunk));
                        }
                    }
                }
                else {
                    if (!(logger.sender instanceof Player)) {
                        LogInf("You're not a player");
                        return;
                    }
                    else {
                        Player player = (Player) logger.sender;
                        if (checkChunks.value <= 0) {
                            LogInf("really... Defaulting a radius of 1");
                            checkChunks.value = 1;
                        }
                        if (checkChunks.value > 5) {
                            LogInf("Too many chunks. Searching a radius of 5 (11x11, 121 chunks)");
                            checkChunks.value = 5;
                        }

                        int radius = checkChunks.value;

                        Location pos = player.getLocation();
                        Chunk chunk = pos.getChunk();
                        World world = player.getWorld();

                        for (int x = chunk.getX() - radius; x <= chunk.getX() + radius; x++) {
                            for (int z = chunk.getZ() - radius; z <= chunk.getZ() + radius; z++) {
                                Chunk chunkAt = world.getChunkAt(x, z);
                                if (chunkAt != null) {
                                    chunkAt.load();
                                    chunkTimeStamps.add(new ChunkTimeStamp(chunkAt));
                                }
                            }
                        }
                    }
                }

                long startTicks = System.nanoTime();

                for (ChunkTimeStamp chunkTimeStamp : chunkTimeStamps) {
                    try {
                        chunkTimeStamp.Update();
                    }
                    catch (Exception ignored) {
                    }
                }

                long endTicks = System.nanoTime();
                long differenceTime = endTicks - startTicks;

                Collections.sort(chunkTimeStamps);
                int count = chunkTimeStamps.size();
                if (showResults.value > count){
                    showResults.value = count;
                }

                LogInf("Finished Lag check");
                LogInf("In total, it took: " + ChatColor.GREEN + ChatFormat.NanoSecondsToMillis(differenceTime) + "ms" + ChatColor.GOLD + " to update all tile entities.");
                LogInf("The " + showResults.value + " laggiest chunks are (laggiest top to bottom): ");
                for (int i = 0; i < showResults.value; i++) {
                    if (i >= count) {
                        break;
                    }
                    ChunkTimeStamp chunkTimeStamp = chunkTimeStamps.get(i);
                    LogInf(ChatFormat.FormatChunkTimeStamp(chunkTimeStamp));
                }
            }
            catch (Exception e) {
                LogInf(ChatColor.RED + "Error findest laggiest chunk. Exception: " + e.getMessage());
            }
        }

        else if (Check(command, "findlagtile")) {
            try {
                if (!(logger.sender instanceof Player)) {
                    LogInf("You're not a player");
                    return;
                }
                ParsedValue<Integer> showResults = ArgumentsParser.ParseInt(args, 0);
                ArrayList<TileEntityTimeStamp> tileEntityTimeStamps = new ArrayList<TileEntityTimeStamp>();
                if (showResults.failed) {
                    LogInf("Error with the results. Showing 8 by default");
                    showResults.value = 8;
                }
                else {
                    showResults.value = Maths.Clamp(showResults.value, 0, 60);
                }

                Player player = (Player) logger.sender;
                Location pos = player.getLocation();
                Chunk chunk = pos.getChunk();
                World world = player.getWorld();

                for(BlockState tileEntity : chunk.getTileEntities()) {
                    if (tileEntity != null) {
                        tileEntityTimeStamps.add(new TileEntityTimeStamp(tileEntity));
                    }
                }

                long startTicks = System.nanoTime();

                for (TileEntityTimeStamp tileEntityTimeStamp : tileEntityTimeStamps) {
                    try {
                        tileEntityTimeStamp.Update();
                    }
                    catch (Exception ignored) {
                    }
                }

                long endTicks = System.nanoTime();
                long differenceTime = endTicks - startTicks;

                Collections.sort(tileEntityTimeStamps);
                int count = tileEntityTimeStamps.size();
                if (showResults.value > count){
                    showResults.value = count;
                }

                LogInf("Finished Lag check in this chunk");
                LogInf("In total, it took: " + ChatColor.GREEN + ChatFormat.NanoSecondsToMillis(differenceTime) + "ms" + ChatColor.GOLD + " to update all tile entities.");
                LogInf("The " + showResults.value + " laggiest tiles are (laggiest top to bottom): ");
                for (int i = 0; i < showResults.value; i++) {
                    if (i >= count) {
                        break;
                    }
                    TileEntityTimeStamp tileEntityTimeStamp = tileEntityTimeStamps.get(i);
                    LogInf(ChatFormat.FormatTileEntityTimeStamp(tileEntityTimeStamp));
                }
            }
            catch (Exception e) {
                LogInf(ChatColor.RED + "Error findest laggiest tile entities. Exception: " + e.getMessage());
            }
        }

        else if (Check(command, "findstuffed")) {
            ParsedValue<String> searchArea = ArgumentsParser.ParseString(args, 0);
            Chunk singleChunk = null;
            World singleWorld = null;
            int searchCode = 0;
            if (searchArea.failed) {
                LogInf("Searching all worlds for stuffed itemducts");
                searchCode = 3;
            }
            else if (searchArea.value.equalsIgnoreCase("ws")) {
                LogInf("Searching all worlds for stuffed itemducts");
                searchCode = 3;
            }
            else if (logger.sender instanceof Player) {
                Player player = (Player) logger.sender;
                if (searchArea.value.equalsIgnoreCase("c")) {
                    singleChunk = player.getWorld().getChunkAt(player.getLocation());
                    LogInf("Searching chunk at " + ChatFormat.ChunkLocationFormatted(singleChunk) + ChatColor.GOLD + " for stuffed itemducts");
                    searchCode = 1;
                }
                else if (searchArea.value.equalsIgnoreCase("w")) {
                    World world = player.getWorld();
                    LogInf("Searching world " + ChatFormat.FormatWorldName(world) + ChatColor.GOLD + " for stuffed itemducts");
                    singleWorld = world;
                    searchCode = 2;
                }
                else {
                    LogErr("Didn't specify a correct location. use 'c', 'w' or 'ws'");
                    return;
                }
            }
            else {
                LogErr("If executing this from console... you can only use 'ws'");
                return;
            }

            if (searchCode == 1 && singleChunk != null) {
                ArrayList<StuffedItemduct> stuffedItemducts = ItemductFinder.GetStuffedItemductsInChunk(singleChunk);
                if (stuffedItemducts.size() > 0) {
                    LogInf(ChatColor.RED + "" + stuffedItemducts.size() + " stuffed itemducts in " + ChatFormat.ChunkLocationFormatted(singleChunk));
                    try {
                        for (StuffedItemduct stuffed : stuffedItemducts) {
                            Location pos;
                            if (stuffed != null) {
                                if (stuffed.duct == null) {
                                    LogInf("Error getting itemduct location");
                                    continue;
                                }
                                else {
                                    pos = stuffed.duct.block.getBlock().getLocation();
                                }
                                String formatted = Formatter.FormatStuffedItemduct(pos, ItemductHelper.GetTotalStuffed(stuffed.stuffed));
                                LogInf(formatted);
                            }
                            else {
                                LogInf(ChatColor.UNDERLINE + "A big error has happened, but there was a stuffed itemduct. unknown where...");
                            }
                        }
                    }
                    catch (Exception e) {
                        LogInf("Exception: " + e.getMessage());
                    }
                }
                else {
                    LogInf("No stuffed itemducts found in " + ChatFormat.ChunkLocationFormatted(singleChunk));
                }
            }

            else if (searchCode == 2 && singleWorld != null) {
                ArrayList<StuffedItemduct> stuffedItemducts = ItemductFinder.GetStuffedItemductsInWorld(WorldHelper.GetWorldServer(singleWorld));
                if (stuffedItemducts.size() > 0) {
                    LogInf(ChatColor.RED + "" + stuffedItemducts.size() + " stuffed itemducts in " + ChatColor.GREEN + singleWorld.getName());
                    try {
                        for (StuffedItemduct stuffed : stuffedItemducts) {
                            Location pos;
                            if (stuffed != null) {
                                if (stuffed.duct == null) {
                                    LogInf("Error getting itemduct location");
                                    continue;
                                }
                                else {
                                    pos = stuffed.duct.block.getBlock().getLocation();
                                }
                                String formatted = Formatter.FormatStuffedItemduct(pos, ItemductHelper.GetTotalStuffed(stuffed.stuffed));
                                LogInf(formatted);
                            }
                            else {
                                LogInf(ChatColor.UNDERLINE + "A big error has happened, but there was a stuffed itemduct. unknown where...");
                            }
                        }
                    }
                    catch (Exception e) {
                        LogInf("Exception: " + e.getMessage());
                    }
                }
                else {
                    LogInf("No stuffed itemducts found in " + ChatFormat.WorldNameSafe(singleWorld));
                }
            }

            else if (searchCode == 3) {
                for (World world : Bukkit.getWorlds()) {
                    if (world == null){
                        LogErr("Error... World is null. Continuing with other worlds");
                        continue;
                    }
                    WorldServer worldServer = WorldHelper.GetWorldServer(world);
                    ArrayList<StuffedItemduct> stuffedItemducts = ItemductFinder.GetStuffedItemductsInWorld(worldServer);
                    if (stuffedItemducts.size() > 0) {
                        LogInf(ChatColor.RED + "" + stuffedItemducts.size() + " stuffed itemducts in " + ChatColor.GREEN + world.getName());
                        try {
                            for (StuffedItemduct stuffed : stuffedItemducts) {
                                Location pos;
                                if (stuffed != null) {
                                    if (stuffed.duct == null) {
                                        LogInf("Error getting itemduct location");
                                        continue;
                                    }
                                    else {
                                        pos = stuffed.duct.block.getBlock().getLocation();
                                    }
                                    String formatted = Formatter.FormatStuffedItemduct(pos, ItemductHelper.GetTotalStuffed(stuffed.stuffed));
                                    LogInf(formatted);
                                }
                                else {
                                    LogInf(ChatColor.UNDERLINE + "A big error has happened, but there was a stuffed itemduct. unknown where...");
                                }
                            }
                        }
                        catch (Exception e) {
                            LogInf("Exception: " + e.getMessage());
                        }
                    }
                    else {
                        LogInf("No stuffed itemducts found in " + ChatFormat.WorldNameSafe(world));
                    }
                }
            }
            else {
                LogErr("Critial error... how tf did this happen lmao");
            }
        }

        else if (Check(command, "tpchunk")) {
            ParsedValue<Integer> x = ArgumentsParser.ParseInt(args, 0);
            ParsedValue<Integer> y = ArgumentsParser.ParseInt(args, 1);
            ParsedValue<Integer> z = ArgumentsParser.ParseInt(args, 2);
            if (x.failed) {
                LogInf("Error with chunk's X coordinates");
                LogInf("Example usage: '/ckl tpchunk 4 66 -7' to teleport to 72 66 -104 (the center of a chunk. corner is 64 66 -112)");
                return;
            }
            if (y.failed) {
                LogInf("Error with chunk's Y coordinates");
                LogInf("Example usage: '/ckl tpchunk 4 66 -7' to teleport to 72 66 -104 (the center of a chunk. corner is 64 66 -112)");
                return;
            }
            if (z.failed) {
                LogInf("Error with chunk's Z coordinates");
                LogInf("Example usage: '/ckl tpchunk 4 66 -7' to teleport to 72 66 -104 (the center of a chunk. corner is 64 66 -112)");
                return;
            }
            if (logger.sender instanceof Player) {
                try {
                    Player player = (Player) logger.sender;
                    Location pos = new Location(player.getWorld(), (x.value * 16) + 8, y.value, (z.value * 16) + 8);
                    CoolEffects.SpawnLightning(player);
                    player.teleport(pos);
                    CoolEffects.SpawnLightning(player);
                }
                catch (Exception e) {

                }
            }
        }

        else if (Check(command, "tppos")) {
            ParsedValue<Integer> x = ArgumentsParser.ParseInt(args, 0);
            ParsedValue<Integer> y = ArgumentsParser.ParseInt(args, 1);
            ParsedValue<Integer> z = ArgumentsParser.ParseInt(args, 2);
            if (x.failed) {
                LogInf("Error with the X coordinates");
                LogInf("Example usage: '/ckl tppos 1337 69 -420'");
                return;
            }
            if (y.failed) {
                LogInf("Error with the Y coordinates");
                LogInf("Example usage: '/ckl tppos 1337 69 -420'");
                return;
            }
            if (z.failed) {
                LogInf("Error with the Z coordinates");
                LogInf("Example usage: '/ckl tppos 1337 69 -420'");
                return;
            }
            if (logger.sender instanceof Player) {
                try {
                    Player player = (Player) logger.sender;
                    Location pos = new Location(player.getWorld(), x.value + 0.5, y.value, z.value + 0.5);
                    CoolEffects.SpawnLightning(player);
                    player.teleport(pos);
                    CoolEffects.SpawnLightning(player);
                    LogInf("Poof... Your feet are now at the Y position");
                }
                catch (Exception e) {

                }
            }
        }

        else if (Check(command, "dccfe")) {
            AutomaticDCCFCE.DisableChickenChunksForceEvents();
        }

        else if (Check(command, "getworlds")) {
            LogInf("World Name - World UID'");
            for (World world : Bukkit.getWorlds()) {
                if (world != null) {
                    LogInf(ChatFormat.FormatWorldInformation(world));
                }
            }
        }

        else if (Check(command, "tpworldn")) {
            if (!(logger.sender instanceof Player)) {
                LogErr("You're not a player");
                return;
            }
            ParsedValue<String> worldName = ArgumentsParser.ParseString(args, 0);
            ParsedValue<Integer> x = ArgumentsParser.ParseInt(args, 1);
            ParsedValue<Integer> y = ArgumentsParser.ParseInt(args, 2);
            ParsedValue<Integer> z = ArgumentsParser.ParseInt(args, 3);
            if (worldName.failed) {
                LogErr("Error with world name.");
                return;
            }
            if (x.failed) {
                LogInf("Error with the X coordinates");
                LogInf("Example usage: '/ckl tpworldn world 1337 69 -420");
                return;
            }
            if (y.failed) {
                LogInf("Error with the Y coordinates");
                LogInf("Example usage: '/ckl tpworldn world 1337 69 -420");
                return;
            }
            if (z.failed) {
                LogInf("Error with the Z coordinates");
                LogInf("Example usage: '/ckl tpworldn world 1337 69 -420");
                return;
            }

            try {
                World world = WorldHelper.GetWorldFromName(worldName.value);

                if (world == null) {
                    LogErr("That world doesn't exist... or, it's unloaded");
                    return;
                }
                else {
                    world.loadChunk(x.value / 16, z.value / 16);
                }

                try {
                    Player player = (Player) logger.sender;
                    Location pos = new Location(world, x.value, y.value, z.value);
                    CoolEffects.SpawnLightning(player);
                    player.teleport(pos);
                    CoolEffects.SpawnLightning(player);
                }
                catch (Exception e) {
                    LogErr("Failed to teleport to world");
                }
            }
            catch (Exception e) {
                LogErr("Failed to get world. ");
            }
        }

        else if (Check(command, "tpworldid")) {
            if (!(logger.sender instanceof Player)) {
                LogErr("You're not a player");
                return;
            }
            ParsedValue<String> uuid = ArgumentsParser.ParseString(args, 0);
            ParsedValue<Integer> x = ArgumentsParser.ParseInt(args, 1);
            ParsedValue<Integer> y = ArgumentsParser.ParseInt(args, 2);
            ParsedValue<Integer> z = ArgumentsParser.ParseInt(args, 3);
            if (uuid.failed) {
                LogInf("Error with world UID.");
                return;
            }
            if (x.failed) {
                LogInf("Error with the X coordinates");
                LogInf("Example usage: '/ckl tpworldn DIM-1 1337 69 -420");
                return;
            }
            if (y.failed) {
                LogInf("Error with the Y coordinates");
                LogInf("Example usage: '/ckl tpworldn DIM-1 1337 69 -420");
                return;
            }
            if (z.failed) {
                LogInf("Error with the Z coordinates");
                LogInf("Example usage: '/ckl tpworldn DIM-1 1337 69 -420");
                return;
            }

            try {
                World world = Bukkit.getWorld(UUID.fromString(uuid.value));

                if (world == null) {
                    LogErr("That world doesn't exist or cannot be found");
                    return;
                }

                try {
                    Player player = (Player) logger.sender;
                    Location pos = new Location(world, x.value, y.value, z.value);
                    CoolEffects.SpawnLightning(player);
                    player.teleport(pos);
                    CoolEffects.SpawnLightning(player);
                }
                catch (Exception e) {
                    LogErr("Failed to teleport to world");
                }
            }
            catch (Exception e) {
                LogErr("Failed to get world.");
            }
        }

        //else if (Check(command, "loadworld")){
        //    ParsedValue<String> worldName = ArgumentsParser.ParseString(args, 0);
        //    if (worldName.failed){
        //        LogErr("Error with world name");
        //        return;
        //    }
        //    try {
        //        Bukkit.getServer().createWorld(new WorldCreator(worldName.value));
        //    }
        //    catch (Exception ignored){
        //        LogErr("Failed to load world");
        //    }
        //}

        else if (Check(command, "getid")) {
            if (logger.sender instanceof Player) {
                Player player = (Player) logger.sender;
                Block block = player.getTargetBlock(null, 200);
                LogInf("ID is '" + ChatColor.GREEN + block.getTypeId() + ChatColor.GOLD + "', ID Data: " + ChatColor.GREEN + block.getData() + ChatColor.GOLD +
                        "'. So " + ChatColor.GREEN + "" + block.getTypeId() + ":" + block.getData());
            }
        }

        else if (Check(command, "oop")) {
            if (logger.sender instanceof Player) {
                try {
                    Player player = (Player) logger.sender;
                    CoolEffects.SpawnDelayedLightningBats(player);
                }
                catch (Exception e) {

                }
            }
            else {
                LogInf("wut? console cant get oop'd");
            }
        }

        else if (Check(command, "adduser")) {
            if (IsSenderOP(sender)) {
                ParsedValue<String> name = ArgumentsParser.ParseString(args, 0);
                if (name.failed) {
                    LogInf("Error with the player's name.");
                    return;
                }
                String actualName = name.value.trim().toLowerCase();
                if (!CarrotPermissions.CheckPlayer(actualName)) {
                    CarrotPermissions.AddPlayer(actualName);
                    CarrotPermissions.SaveToConfig();
                    LogInf("Added '" + actualName + "' to the list");
                }
                else {
                    LogInf("That user is already in the list");
                }
            }
            else {
                LogErr("Only OPs can add/remove users. You toad :/");
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + ChatColor.RED + " tried to do something naughty!");
            }
        }

        else if (Check(command, "removeuser")) {
            if (IsSenderOP(sender)) {
                ParsedValue<String> name = ArgumentsParser.ParseString(args, 0);
                if (name.failed) {
                    LogInf("Error with the player's name.");
                    return;
                }
                String actualName = name.value.trim().toLowerCase();
                if (CarrotPermissions.CheckPlayer(actualName)) {
                    CarrotPermissions.RemovePlayer(actualName);
                    CarrotPermissions.SaveToConfig();
                    LogInf("Removed '" + actualName + "' from the list");
                }
                else {
                    LogInf("That user hasn't been added yet.");
                }
            }
            else {
                LogErr("Only OPs can add/remove users. You toad :/");
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + ChatColor.RED + " tried to do something naughty!");
            }
        }

        else if (Check(command, "getusers")) {
            if (IsSenderOP(sender)) {
                LogInf("Listing all players allowed to use CKL");
                for (String name : CarrotPermissions.AllowedPlayers) {
                    LogSuc(name);
                }
            }
            else {
                LogErr("Only OPs can get the users. You toad :/");
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + ChatColor.RED + " tried to do something naughty!");
            }
        }

        else if (Check(command, "reloadconfig")) {
            if (IsSenderOP(sender)) {
                try {
                    LogInf("Trying to reload config...");
                    ConfigManager.ReloadConfig();
                    CarrotPermissions.LoadFromConfig();
                    LogSuc("Successfully reloaded config!");
                }
                catch (Exception e){
                    LogErr("Failed to reload config");
                    e.printStackTrace();
                }
            }
            else {
                LogErr("Only OPs can reload the config. You toad :/");
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + ChatColor.RED + " tried to do something naughty!");
            }
        }

        else if (Check(command, "saveconfig")) {
            if (IsSenderOP(sender)) {
                try {
                    LogInf("Trying to save config...");
                    CarrotPermissions.SaveToConfig();
                    ConfigManager.SaveConfig();
                    LogSuc("Successfully saved config!");
                }
                catch (Exception e){
                    LogErr("Failed to save config");
                    e.printStackTrace();
                }
            }
            else {
                LogErr("Only OPs can reload the config. You toad :/");
                Bukkit.broadcastMessage(ChatColor.GREEN + sender.getName() + ChatColor.RED + " tried to do something naughty!");
            }
        }

        // Predefined or error commands

        else if (Check(command, "lolidk")) {
            LogInf(CoolEffects.RainbowText("helo " + sender.getName()));
        }

        else if (command.equals("intern_cmd_broken_rip_xd")) {
            LogErr("Critical error that should've been impossible to happen... what the...... o_o");
        }

        else {
            LogInf("The command " + ChatColor.GREEN + command + ChatColor.GOLD + " doesn't exist.");
        }
    }
}

// 1800 lines of code xd
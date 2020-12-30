package carrot.log;

import carrot.lagdetect.ChunkTimeStamp;
import carrot.lagdetect.TileEntityTimeStamp;
import carrot.str.Formatter;
import org.bukkit.ChatColor;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.UUID;

public final class ChatFormat {
    public static String WorldNameSafe(Chunk chunk) {
        if (chunk == null || chunk.getWorld().getName() == null){
            return  ChatColor.RED + "NULL WORLDNAME";
        }
        return chunk.getWorld().getName();
    }
    public static String WorldNameSafe(World world) {
        if (world == null || world.getName() == null){
            return  ChatColor.RED + "NULL WORLDNAME";
        }
        return world.getName();
    }
    public static String ChunkLocationFormatted(Chunk chunk) {
        if (chunk == null){
            return ChatColor.RED + "[NULL CHUNK]";
        }
        return Formatter.FormatXZ(chunk.getX(), chunk.getZ());
    }
    public static String FormatWorldName(World world){
        return ChatColor.GREEN + Bracket(WorldNameSafe(world));
    }
    public static String FormatWorldName(Chunk chunk){
        return ChatColor.GREEN + Bracket(WorldNameSafe(chunk));
    }
    public static String FormatLocation(Location pos){
        if (pos == null){
            return ChatColor.RED + "[NULL LOCATION]";
        }
        return Formatter.FormatXYZDouble(pos.getX(), pos.getY(), pos.getZ());
    }

    public static String FormatBlock(String world, Location pos) {
        return ChatColor.GREEN + Bracket(world) + ChatColor.GOLD + " at " + ChatColor.GREEN + Bracket(LocationToStrInt(pos));
    }

    public static String LocationToStr(Location pos){
        return "X: " + pos.getX() + ", Y: " + pos.getY() + ", Z: " + pos.getZ();
    }
    public static String LocationToStrInt(Location pos){
        return "X: " + (int)Math.floor(pos.getX()) + ", Y: " + (int)Math.floor(pos.getY()) + ", Z: " + (int)Math.floor(pos.getZ());
    }

    public static String Bracket(String content){
        return "[" + content + "]";
    }

    public static String FormatChunkTimeStamp(ChunkTimeStamp chunkTimeStamp){
        return FormatWorldName(chunkTimeStamp.chunk) + ChatColor.GOLD +
                " at " + ChatColor.GREEN  + ChunkLocationFormatted(chunkTimeStamp.chunk) +
                ChatColor.GOLD + " - Time: " + ChatColor.RED + NanoSecondsToMillis(chunkTimeStamp.updateTime) + "ms" + ChatColor.GOLD +
                " - " + ChatColor.GREEN + chunkTimeStamp.TileEntityCount() + ChatColor.GOLD + " tiles";
    }

    public static String FormatTileEntityTimeStamp(TileEntityTimeStamp tileEntityTS){
        return  ChatColor.GREEN  + FormatLocation(tileEntityTS.tileEntity.getLocation()) +
                ChatColor.GOLD + " - Time: " + ChatColor.RED + NanoSecondsToMillis(tileEntityTS.updateTime) + "ms";
    }

    public static String FormatWorldInformation(World world){
        if (world == null){
            return "WORLD NULL";
        }
        UUID uuid = world.getUID();
        String uuidString = "[UUID NULL]";
        if (uuid != null){
            uuidString = uuid.toString();
        }
        return ChatColor.GREEN + WorldNameSafe(world) + ChatColor.GOLD + " - " + ChatColor.YELLOW + uuidString;
    }

    public static double NanoSecondsToMillis(long nano){
        return round(((double) nano) / 1000000D, 3);
    }

    public static double round(double value, int places) {
        if (places < 0) {
            places = 0;
        }

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    public static double ParseSafe(String str){
        try {
            return Integer.parseInt(str);
        }
        catch (Exception e){
            return 0;
        }
    }
}

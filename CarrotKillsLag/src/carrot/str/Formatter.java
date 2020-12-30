package carrot.str;

import org.bukkit.ChatColor;
import org.bukkit.Location;

public final class Formatter {
    public static String Repeat(int repeatFor, char repeatCharacter) {
        String str = "";
        for (int i = 0; i < repeatFor; i++) {
            str += repeatCharacter;
        }
        return str;
    }

    public static String EnsureLength(String str, int maxLength) {
        int strlen = str.length();
        int extralen = maxLength - strlen;
        if (extralen > 0) {
            str += Repeat(extralen, ' ');
        }
        return str;
    }

    // Adds a space to coordinates if they're positive to line them up with the negative symbol on coordinates
    public static String FormatCoordinate(double coordinates) {
        if (coordinates < 0.0) {
            return String.valueOf(coordinates);
        }
        else {
            return " " + String.valueOf(coordinates);
        }
    }
    public static String FormatChunkCoordinate(int coordinate) {
        if (coordinate < 0) {
            return String.valueOf(coordinate);
        }
        else {
            return " " + String.valueOf(coordinate);
        }
    }

    public static String FormatLocation(Location pos) {
        String x = EnsureLength(FormatCoordinate(pos.getX()), 7);
        String y = EnsureLength(FormatCoordinate(pos.getY()), 7);
        String z = EnsureLength(FormatCoordinate(pos.getZ()), 7);
        return x + y + z;
    }
    public static String FormatXZ(int x, int z){
        return "[X: " + x + ", Z: " + z + "]";
    }
    public static String FormatXYZ(int x, int y, int z){
        return "[X: " + x + ", Y: " + y + ", Z: " + z + "]";
    }
    public static String FormatXYZDouble(double x, double y, double z){
        return "[X: " + x + ", Y: " + y + ", Z: " + z + "]";
    }

    public static String FormatStuffedItemduct(Location pos, int itemsStuffed) {
        return ChatColor.GOLD + FormatLocation(pos) + " - " + ChatColor.RED + "Stuffed: " + itemsStuffed;
    }
    public static String FormatPAFound(Location pos) {
        return ChatColor.GOLD + "Particle Accelerator at: " + FormatLocation(pos);
    }
}

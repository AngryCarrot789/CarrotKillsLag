package carrot.thermexpan.itemduct;

import carrot.bukkt.WorldHelper;
import carrot.thermexpan.itemduct.acnbt.NBTPart;
import carrot.thermexpan.itemduct.acnbt.NBTPartList;
import carrot.thermexpan.itemduct.acnbt.NBTPartStuffed;
import net.minecraft.server.v1_6_R3.*;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.BlockState;

import java.util.ArrayList;

public final class ItemductFinder {
    public static NBTPartList NBTGetConduitParts(NBTTagCompound conduitNBT){
        return new NBTPartList(conduitNBT.getList("parts"));
    }

    public static NBTPartStuffed NBTGetStuffedItemStacks(NBTTagCompound partConduitNBT){
        return new NBTPartStuffed(partConduitNBT.getList("StuffedItems"));
    }

    public static ArrayList<Itemduct> GetItemductsInChunk(Chunk chunk){
        ArrayList<Itemduct> itemducts = new ArrayList<Itemduct>();
        net.minecraft.server.v1_6_R3.World world = WorldHelper.GetWorldServer(chunk);
        try {
            for (BlockState state : chunk.getTileEntities()) {
                try {
                    if (state.getTypeId() == 1281) {
                        TileEntity tile = world.getTileEntity(state.getX(), state.getY(), state.getZ());
                        Itemduct duct = new Itemduct(tile, state);
                        itemducts.add(duct);
                    }
                }
                catch (Exception e) {

                }
            }
        }
        catch (Exception e){
        }
        return itemducts;
    }

    public static ArrayList<Itemduct> GetItemductsWithinWorld(World world) {
        ArrayList<Itemduct> itemducts = new ArrayList<Itemduct>();
        try{
            WorldServer worldServer = WorldHelper.GetWorldServer(world);
            for (Chunk chunk : world.getLoadedChunks()) {
                for (BlockState state : chunk.getTileEntities()) {
                    try{
                        if (state.getTypeId() == 1281) {
                            TileEntity tile = worldServer.getTileEntity(state.getX(), state.getY(), state.getZ());
                            Itemduct duct = new Itemduct(tile, state);
                            itemducts.add(duct);
                        }
                    }
                    catch (Exception e){

                    }
                }
            }
        }
        catch (Exception e){
        }
        return itemducts;
    }

    public static ArrayList<StuffedItemduct> GetStuffedItemductsInChunk(Chunk chunk){
        ArrayList<StuffedItemduct> stuffed = new ArrayList<StuffedItemduct>();
        try {
            // Structure is:
            // TileEntity -> NBT -> NBTList of Parts -> NBT Part -> NBTList of stuffed, traveling, etc
            ArrayList<Itemduct> itemducts = GetItemductsInChunk(chunk);
            // foreach itemduct
            for (Itemduct itemduct : itemducts) {
                try {
                    NBTPartList parts = ItemductFinder.NBTGetConduitParts(itemduct.nbt.nbt);
                    // foreach itemduct
                    for (int i = 0; i < parts.nbtList.size(); i++) {
                        try {
                            NBTPart part = new NBTPart((NBTTagCompound) parts.nbtList.get(i));
                            NBTPartStuffed partStuffed = ItemductFinder.NBTGetStuffedItemStacks(part.nbt);
                            if (partStuffed.nbtList.size() > 0) {
                                ArrayList<ItemStack> stuffedItems = new ArrayList<ItemStack>();
                                try {
                                    for (int a = 0; a < partStuffed.nbtList.size(); a++) {
                                        NBTTagCompound nbtItemstack = (NBTTagCompound) partStuffed.nbtList.get(a);
                                        ItemStack itemstack = ItemStack.createStack(nbtItemstack);
                                        stuffedItems.add(itemstack);
                                    }
                                }
                                catch (Exception e) {
                                }
                                stuffed.add(new StuffedItemduct(itemduct, stuffedItems));
                            }
                        }
                        catch (Exception e){
                        }
                    }
                }
                catch (Exception e) {
                }
            }
        }
        catch (Exception e){
        }
        return stuffed;
    }

    public static ArrayList<StuffedItemduct> GetStuffedItemductsInWorld(WorldServer world){
        ArrayList<StuffedItemduct> stuffed = new ArrayList<StuffedItemduct>();
        try {
            // Structure is:
            // TileEntity -> NBT -> NBTList of Parts -> NBT Part -> NBTList of stuffed, traveling, etc
            ArrayList<Itemduct> itemducts = GetItemductsWithinWorld(world.getWorld());
            // foreach itemduct
            for (Itemduct itemduct : itemducts) {
                try {
                    NBTPartList parts = ItemductFinder.NBTGetConduitParts(itemduct.nbt.nbt);
                    // foreach itemduct
                    for (int i = 0; i < parts.nbtList.size(); i++) {
                        try {
                            NBTPart part = new NBTPart((NBTTagCompound) parts.nbtList.get(i));
                            NBTPartStuffed partStuffed = ItemductFinder.NBTGetStuffedItemStacks(part.nbt);
                            if (partStuffed.nbtList.size() > 0) {
                                ArrayList<ItemStack> stuffedItems = new ArrayList<ItemStack>();
                                try {
                                    for (int a = 0; a < partStuffed.nbtList.size(); a++) {
                                        NBTTagCompound nbtItemstack = (NBTTagCompound) partStuffed.nbtList.get(a);
                                        ItemStack itemstack = ItemStack.createStack(nbtItemstack);
                                        stuffedItems.add(itemstack);
                                    }
                                }
                                catch (Exception e) {
                                }
                                stuffed.add(new StuffedItemduct(itemduct, stuffedItems));
                            }
                        }
                        catch (Exception e){
                        }
                    }
                }
                catch (Exception e) {
                }
            }
        }
        catch (Exception e){
        }
        return stuffed;
    }
}

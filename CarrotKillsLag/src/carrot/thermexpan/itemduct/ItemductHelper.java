package carrot.thermexpan.itemduct;

import net.minecraft.server.v1_6_R3.*;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;

import java.util.ArrayList;

public final class ItemductHelper {
    public static void DropStuffedItemsOnGround(World forgeWorld, Itemduct itemduct, boolean breakItemduct) {
        ArrayList<ItemStack> stacks = itemduct.GetStuffedItems();
        StrikeLightningAtItemducts(forgeWorld, itemduct);
        Location pos = itemduct.block.getLocation();
        for(ItemStack stack : stacks) {
            EntityItem items = new EntityItem(forgeWorld, pos.getX(), pos.getY(), pos.getZ(), stack);
            forgeWorld.addEntity(items);
        }
        itemduct.ClearStuffedItems();
        if (breakItemduct) {
            itemduct.block.getBlock().breakNaturally();
        }
    }

    public static void SetStuffedItemductAsChest(World forgeWorld, StuffedItemduct itemduct) {
        StrikeLightningAtItemducts(forgeWorld, itemduct.duct);
        ChestItemduct(itemduct);
    }

    public static void StrikeLightningAtItemducts(World forgeWorld, Itemduct itemduct){
        Location pos = itemduct.block.getLocation();
        forgeWorld.getWorld().strikeLightningEffect(pos);
    }

    public static org.bukkit.inventory.ItemStack ToNormalItemStack(ItemStack stack){
        return new org.bukkit.inventory.ItemStack(stack.id, stack.count);
    }

    public static void ChestItemduct(StuffedItemduct itemduct) {
        org.bukkit.block.BlockState itemductBlock = itemduct.duct.block;
        ArrayList<ItemStack> getStuffedItems = itemduct.stuffed;
        itemduct.duct.ClearStuffedItems();
        itemductBlock.getBlock().setType(Material.CHEST);
        Chest chest = (Chest) itemductBlock.getBlock().getState();
        Inventory chestInv = chest.getInventory();
        for (int i = 0; i < getStuffedItems.size(); i++) {
            ItemStack stack = getStuffedItems.get(i);
            if (i + 1 > 27)  return;
            chestInv.setItem(i + 1, ToNormalItemStack(stack));
        }
    }

    public static int GetTotalStuffed(ArrayList<ItemStack> stacks){
        if (stacks == null) return 0;
        int totalItemsStuffed = 0;
        for(ItemStack stack : stacks){
            totalItemsStuffed += stack.count;
        }
        return totalItemsStuffed;
    }
}

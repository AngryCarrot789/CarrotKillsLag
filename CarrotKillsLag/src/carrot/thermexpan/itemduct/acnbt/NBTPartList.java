package carrot.thermexpan.itemduct.acnbt;

import net.minecraft.server.v1_6_R3.NBTTagList;

// a list of conduits (aka "parts")
public class NBTPartList {
    public NBTTagList nbtList;

    public NBTPartList(NBTTagList _nbtList) {
        nbtList = _nbtList;
    }
}

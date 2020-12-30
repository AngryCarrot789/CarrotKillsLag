package carrot.lagdetect;

import carrot.maths.Maths;
import org.bukkit.block.BlockState;

public class TileEntityTimeStamp implements Comparable<TileEntityTimeStamp> {
    public BlockState tileEntity;
    public long start = 0, end = 0;
    public long updateTime = 0;

    public TileEntityTimeStamp(BlockState tileEntity) {
        this.tileEntity = tileEntity;
    }

    public void Update() {
        start = System.nanoTime();

        tileEntity.update();

        end = System.nanoTime();
        updateTime = end - start;
    }

    @Override
    public int compareTo(TileEntityTimeStamp o) {
        return Maths.CompareInt((int) o.updateTime, (int) updateTime);
    }
}

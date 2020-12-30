package carrot.lagdetect;

import carrot.maths.Maths;
import org.bukkit.Chunk;
import org.bukkit.block.BlockState;

public class ChunkTimeStamp implements Comparable<ChunkTimeStamp> {
    public Chunk chunk;
    public long start = 0, end = 0;
    public long updateTime = 0;

    public ChunkTimeStamp(Chunk c) {
        chunk = c;
    }

    public void Update() {
        start = System.nanoTime();

        for (BlockState tile : chunk.getTileEntities()) {
            tile.update();
        }

        end = System.nanoTime();
        updateTime = end - start;
    }

    public int TileEntityCount(){
        if (chunk != null){
            return chunk.getTileEntities().length;
        }
        return 0;
    }

    @Override
    public int compareTo(ChunkTimeStamp o) {
        return Maths.CompareInt((int)o.updateTime, (int)updateTime);
    }
}
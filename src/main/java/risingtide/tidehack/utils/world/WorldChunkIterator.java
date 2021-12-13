// fuck you rat go brrrrrrrr
package risingtide.tidehack.utils.world;

import risingtide.tidehack.utils.Utils;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.world.chunk.ChunkStatus;
import net.minecraft.world.chunk.WorldChunk;

import java.util.Iterator;

import static risingtide.tidehack.utils.Utils.mc;

public class WorldChunkIterator implements Iterator<WorldChunk> {
    private final int px, pz;
    private final int r;

    private int x, z;
    private WorldChunk chunk;

    public WorldChunkIterator() {
        px = ChunkSectionPos.getSectionCoord(mc.player.getBlockX());
        pz = ChunkSectionPos.getSectionCoord(mc.player.getBlockZ());
        r = Utils.getRenderDistance();

        x = px - r;
        z = pz - r;

        nextChunk();
    }

    private void nextChunk() {
        chunk = null;

        while (true) {
            z++;
            if (z > pz + r) {
                z = pz - r;
                x++;
            }

            if (x > px + r || z > pz + r) break;

            chunk = (WorldChunk) mc.world.getChunk(x, z, ChunkStatus.FULL, false);
            if (chunk != null) break;
        }
    }

    @Override
    public boolean hasNext() {
        return chunk != null;
    }

    @Override
    public WorldChunk next() {
        WorldChunk chunk = this.chunk;

        nextChunk();

        return chunk;
    }
}

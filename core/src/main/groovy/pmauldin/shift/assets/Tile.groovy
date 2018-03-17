package pmauldin.shift.assets

import groovy.transform.CompileStatic

@CompileStatic
enum Tile {
    GRASS(14, 1),
    WATER(13, 6, true),
    TREE(8,1, true),
    PLAYER(0, 8)

    static final int TILE_SIZE = 32
    int xOffset, yOffset
    boolean solid

    Tile(int xOffset, int yOffset, boolean solid = false) {
        this.xOffset = xOffset * TILE_SIZE
        this.yOffset = yOffset * TILE_SIZE
        this.solid = solid
    }
}
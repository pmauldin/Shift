package pmauldin.shift.assets

import groovy.transform.CompileStatic

@CompileStatic
enum Tile {
    GRASS(14, 1),
    PLAYER(0, 8)

    static final int TILE_SIZE = 32
    int xOffset, yOffset

    Tile(int xOffset, int yOffset) {
        this.xOffset = xOffset * TILE_SIZE
        this.yOffset = yOffset * TILE_SIZE
    }
}
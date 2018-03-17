package pmauldin.shift.assets

import groovy.transform.CompileStatic
import pmauldin.shift.assets.tiles.GrassTile

import pmauldin.shift.assets.tiles.TreeTile
import pmauldin.shift.assets.tiles.WaterTile
import pmauldin.shift.entities.Entity
import pmauldin.shift.entities.components.Resource

@CompileStatic
class Tiles {
	static Tile GRASS = new GrassTile()
	static Tile WATER = new WaterTile()
	static Tile TREE = new TreeTile()
}

@CompileStatic
abstract class Tile {
	Entity entity

	Tile(Entity entity) {
		this.entity = entity
	}

	boolean isSolid() {
		return false
	}

	boolean isResource() {
		return false
	}

	void buildResource(Resource resource) {
		return
	}
}

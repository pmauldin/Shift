package pmauldin.shift.assets

import groovy.transform.CompileStatic
import pmauldin.shift.assets.tiles.GrassTile
import pmauldin.shift.assets.tiles.RockTile
import pmauldin.shift.assets.tiles.TreeTile
import pmauldin.shift.assets.tiles.WaterTile
import pmauldin.shift.entities.Sprite
import pmauldin.shift.entities.components.ResourceComponent
import pmauldin.shift.entities.items.Resource

@CompileStatic
class Tiles {
	static Tile GRASS = new GrassTile()
	static Tile WATER = new WaterTile()
	static Tile TREE = new TreeTile()
	static Tile ROCK = new RockTile()
}

@CompileStatic
abstract class Tile {
	Sprite sprite

	Tile(Sprite sprite) {
		this.sprite = sprite
	}

	boolean isSolid() {
		return false
	}

	boolean isResource() {
		return false
	}

	void buildResource(ResourceComponent resourceComponent) {
		def resource = new Resource(type: resourceType(), tile: this)
		resourceComponent.resource = resource
	}

	protected String resourceType() {
		null
	}
}

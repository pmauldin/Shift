package pmauldin.shift.entities.items

import groovy.transform.CompileStatic
import pmauldin.shift.assets.Tile

@CompileStatic
class Resource {
	String type
	Tile tile

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		Resource resource = (Resource) o

		if (tile != resource.tile) return false
		if (type != resource.type) return false

		return true
	}

	int hashCode() {
		int result
		result = (type != null ? type.hashCode() : 0)
		result = 31 * result + (tile != null ? tile.hashCode() : 0)
		return result
	}
}

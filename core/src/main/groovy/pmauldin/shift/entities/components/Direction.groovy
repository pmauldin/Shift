package pmauldin.shift.entities.components

import com.artemis.Component
import groovy.transform.CompileStatic

@CompileStatic
class Direction extends Component {
	private static int[] validDirections = [-1, 0, 1]

	int x, y

	void setX(int x) {
		if (x in validDirections) {
			this.x = x
		} else {
			System.out.println("Invalid x direction $x")
		}
	}

	void setY(int y) {
		if (y in validDirections) {
			this.y = y
		} else {
			System.out.println("Invalid y direction $y")
		}
	}
}

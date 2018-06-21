package pmauldin.shift.automaton

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import groovy.transform.CompileStatic
import pmauldin.shift.Automaton

@CompileStatic
class Cell {
	enum State {
		ALIVE,
		DEAD
	}

	private static int CELL_SIZE = Automaton.cellSize

	int x, y
	State state
	State newState
	List<Cell> neighbors

	ShapeRenderer shapeRenderer

	Cell(int x, int y) {
		this.x = x
		this.y = y

		neighbors = new ArrayList<>(8)
		shapeRenderer = new ShapeRenderer()
	}


	void update(int count) {
		state = newState
		if (state == State.ALIVE) {
			if (count < 2 || count > 3) {
				newState = State.DEAD
			}
		} else if (count == 3) {
			newState = State.ALIVE
		}

		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
		shapeRenderer.rect(x * CELL_SIZE, y * CELL_SIZE, CELL_SIZE, CELL_SIZE)
		if (newState == State.ALIVE) {
			shapeRenderer.setColor(Color.WHITE)
		} else {
			shapeRenderer.setColor(Color.BLACK)
		}
		shapeRenderer.end()
	}

	boolean equals(o) {
		if (this.is(o)) return true
		if (getClass() != o.class) return false

		Cell cell = (Cell) o

		if (x != cell.x) return false
		if (y != cell.y) return false
		if (newState != cell.newState) return false
		if (state != cell.state) return false

		return true
	}

	int hashCode() {
		return 31 * x + y
	}
}

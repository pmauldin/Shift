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

	int x, y
	State state
	State newState
	private static int CELL_SIZE = Automaton.cellSize

	ShapeRenderer shapeRenderer

	Cell(int x, int y) {
		this.x = x
		this.y = y

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
}

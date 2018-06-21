package pmauldin.shift.automaton

import groovy.transform.CompileStatic

@CompileStatic
class CellMap {

	int width, height
	Cell[][] cells
	Random random

	CellMap(int width, int height) {
		this.width = width
		this.height = height
		random = new Random()
		cells = new Cell[width][height]

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Cell cell = new Cell(x, y)
				if (random.nextBoolean()) {
					cell.state = Cell.State.ALIVE
				} else {
					cell.state = Cell.State.DEAD
				}

				cells[x][y] = cell
			}
		}
	}

	void render() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				int count = aliveNeighbors(x, y)

				cells[x][y].update(count)
			}
		}
	}

	private int aliveNeighbors(int x, int y) {
		int count = 0

		for (int x1 = -1; x1 <= 1; x1++) {
			for (int y1 = -1; y1 <= 1; y1++) {
				int x0 = x + x1
				int y0 = y + y1
				if (x0 < 0 || x0 >= width || y0 < 0 || y0 >= height || (x1 == 0 && y1 == 0)) continue

				count += (cells[x0][y0].state == Cell.State.ALIVE) ? 1 : 0
			}
		}

		return count
	}
}

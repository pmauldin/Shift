package pmauldin.shift.automaton

import groovy.transform.CompileStatic

@CompileStatic
class CellMap {

	int width, height
	Cell[] cells
	HashSet<Cell> activeCells
	Random random

	CellMap(int width, int height) {
		this.width = width
		this.height = height
		random = new Random()
		cells = new Cell[width * height]
		activeCells = new HashSet<>()

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				Cell cell = new Cell(x, y)
				if (random.nextBoolean()) {
					cell.state = Cell.State.ALIVE
				} else {
					cell.state = Cell.State.DEAD
				}

				cells[y * width + x] = cell
			}
		}

		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				setNeighbors(getCell(x, y))
			}
		}
	}

	void render() {
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {\
				Cell cell = getCell(x, y)
				int count = aliveNeighbors(cell)

				cell.update(count)
			}
		}
	}

	private Cell getCell(int x, int y) {
		return cells[y * width + x]
	}

	private static int aliveNeighbors(Cell cell) {
		int count = 0

		for (Cell neighbor : cell.neighbors) {
			count += (neighbor.state == Cell.State.ALIVE) ? 1 : 0
		}

		return count
	}

	private void setNeighbors(Cell cell) {
		for (int x1 = -1; x1 <= 1; x1++) {
			for (int y1 = -1; y1 <= 1; y1++) {
				int x0 = cell.x + x1
				int y0 = cell.y + y1
				if (x0 < 0 || x0 >= width || y0 < 0 || y0 >= height || (x1 == 0 && y1 == 0)) continue

				cell.neighbors.add(getCell(x0, y0))
			}
		}
	}
}

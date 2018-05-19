package pmauldin.shift.entities.systems.core

import com.artemis.Aspect
import com.artemis.systems.IteratingSystem
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.Components
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.InputEvent.InputType

@CompileStatic
class InputSystem extends IteratingSystem implements LogicSystem {

	InputSystem() {
		super(Aspect.all(InputEvent))

		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			boolean keyDown(int keyCode) {
				createEvent(keyCode, InputType.PRESSED)
			}

			@Override
			boolean keyUp(int keyCode) {
				createEvent(keyCode, InputType.RELEASED)
			}

			private boolean createEvent(int keyCode, InputType type) {
				int entityId = EntityManager.create()
				def inputEvent = Components.mInputEvent.create(entityId)
				inputEvent.keyCode = keyCode
				inputEvent.type = type
				true
			}
		})
	}

	@Override
	protected void process(int entityId) {
		// clean up all unconsumed inputs
		def inputEvent = Components.mInputEvent.get(entityId)
		if (inputEvent.markedForRemoval) {
			EntityManager.delete(entityId)
		} else {
			inputEvent.markedForRemoval = true
		}
	}
}

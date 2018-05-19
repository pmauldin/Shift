package pmauldin.shift.entities.systems.core

import com.artemis.BaseSystem
import com.artemis.ComponentMapper
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.InputAdapter
import groovy.transform.CompileStatic
import pmauldin.shift.entities.EntityManager
import pmauldin.shift.entities.LogicSystem
import pmauldin.shift.entities.components.events.InputEvent
import pmauldin.shift.entities.components.events.InputEvent.InputType

@CompileStatic
class InputSystem extends BaseSystem implements LogicSystem {
	static ComponentMapper<InputEvent> mInputEvent

	InputSystem() {
		Gdx.input.setInputProcessor(new InputAdapter() {
			@Override
			boolean keyDown(int keyCode) {
				createEvent(keyCode, InputType.DOWN)
			}

			@Override
			boolean keyUp(int keyCode) {
				createEvent(keyCode, InputType.UP)
			}

			private boolean createEvent(int keyCode, InputType type) {
				int entityId = EntityManager.create()
				def inputEvent = mInputEvent.create(entityId)
				inputEvent.keyCode = keyCode
				inputEvent.type = type
				true
			}
		})
	}

	@Override
	protected void processSystem() {}

}

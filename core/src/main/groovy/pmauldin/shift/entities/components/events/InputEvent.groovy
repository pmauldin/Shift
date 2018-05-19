package pmauldin.shift.entities.components.events

import com.artemis.Component

class InputEvent extends Component {
	enum InputType {
		RELEASED,
		PRESSED
	}

	int keyCode
	InputType type

}

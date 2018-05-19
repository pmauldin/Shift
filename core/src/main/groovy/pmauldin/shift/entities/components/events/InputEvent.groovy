package pmauldin.shift.entities.components.events

import com.artemis.Component

class InputEvent extends Component {
	enum InputType {
		UP,
		DOWN
	}

	int keyCode;
	InputType type;

}

package pmauldin.shift.entities.components.core

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.Sprite
import groovy.transform.CompileStatic

@CompileStatic
class Renderable extends Component {
	List<Sprite> sprites
	Integer activeSprite
	Integer layer
}

package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.math.Vector2
import groovy.transform.CompileStatic

@CompileStatic
class Transform extends Component {
    float x, y = 0.0f

    void setPosition(Vector2 position) {
        this.x = position.x
        this.y = position.y
    }
}

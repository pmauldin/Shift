package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.math.Rectangle
import groovy.transform.CompileStatic

@CompileStatic
class LevelComponent extends Component {
    Rectangle bounds

    int[][] tileIds
}

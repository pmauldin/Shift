package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.math.Rectangle
import com.badlogic.gdx.utils.Array
import groovy.transform.CompileStatic

@CompileStatic
class Level extends Component {
    Rectangle bounds

    Array<Integer> tileIds
}

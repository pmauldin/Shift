package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic

@CompileStatic
class TextureComponent extends Component {
    TextureRegion texture = null
    Integer layer = null
}

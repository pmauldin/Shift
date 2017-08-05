package pmauldin.shift.entities.components

import com.artemis.Component
import com.badlogic.gdx.graphics.g2d.TextureRegion
import groovy.transform.CompileStatic

@CompileStatic
class RenderComponent extends Component {
    TextureRegion texture
    Integer layer
}

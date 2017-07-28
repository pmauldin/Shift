package pmauldin.shift.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import pmauldin.shift.Shift

class DesktopLauncher {

    static void main(String[] args) {
        def config = new LwjglApplicationConfiguration()
        config.title = "Shift"
        config.width = 800
        config.height = 480
        def application = new LwjglApplication(new Shift(), config)
    }
}

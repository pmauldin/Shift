package main.groovy.pmauldin.shift.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import pmauldin.shift.Shift

class DesktopLauncher {

    static void main(String[] args) {
        def config = new LwjglApplicationConfiguration()
        config.title = "Shift"
        config.width = 1280
        config.height = 768
        config.vSyncEnabled = false
        config.foregroundFPS = 0
        config.backgroundFPS = 0
        new LwjglApplication(new Shift(), config)
    }
}

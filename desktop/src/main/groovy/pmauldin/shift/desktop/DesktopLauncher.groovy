package main.groovy.pmauldin.shift.desktop

import com.badlogic.gdx.backends.lwjgl.LwjglApplication
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration
import groovy.transform.CompileStatic
import pmauldin.shift.Shift

@CompileStatic
class DesktopLauncher {

    static void main(String[] args) {
        def config = new LwjglApplicationConfiguration()
        config.title = "Shift"
        config.width = 1280
        config.height = 768
        config.vSyncEnabled = true
        config.foregroundFPS = 25
        config.backgroundFPS = 2+5
        new LwjglApplication(new Shift(), config)
    }
}

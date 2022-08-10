package com.rabbimidu.remember2009.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.rabbimidu.remember2009.MainLander;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.title = "Remember 2009";
        config.width = 480;
        config.height = 800;
        config.foregroundFPS = 60;
        config.resizable = false;
        new LwjglApplication(new MainLander(), config);
    }
}
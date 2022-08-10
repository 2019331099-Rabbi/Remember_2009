package com.rabbimidu.remember2009.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;
import com.rabbimidu.remember2009.MainLander;

public class HtmlLauncher extends GwtApplication {

    @Override
    public GwtApplicationConfiguration getConfig() {

        return new GwtApplicationConfiguration(480, 800);
    }

    @Override
    public ApplicationListener createApplicationListener() {
        return new MainLander();
    }
}
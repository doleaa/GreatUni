package com.bucha.wrestlers.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;

public class BackEndApplicationModule extends AbstractModule {
    protected  void configure() {}

    @Provides
    @Singleton
    String getAppName() { return "BackEndAppName"; }
}

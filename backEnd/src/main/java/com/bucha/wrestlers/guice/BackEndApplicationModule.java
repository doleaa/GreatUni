package com.bucha.wrestlers.guice;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import org.apache.http.impl.client.DefaultHttpClient;

public class BackEndApplicationModule extends AbstractModule {
    protected  void configure() {}

    @Provides
    @Singleton
    String getAppName() { return "BackEndAppName"; }

    @Provides
    @Singleton
    public DefaultHttpClient defaultHttpClient() {
        return new DefaultHttpClient();
    }
}

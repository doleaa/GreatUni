package com.bucha.wrestlers.server;

import com.bucha.wrestlers.dropwizard.BackEndApplication;

import java.io.File;

public class BackEndApplicationRunner {

    public static void main(String[] args) throws Exception {
        BackEndApplication.main(
                new String[] {
                        "server",
                        new File("backEnd/src/main/resources/Config.yaml").getAbsolutePath()
                }
        );
    }
}

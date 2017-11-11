package com.bucha.wrestlers.dropwizard;

import com.bucha.wrestlers.guice.BackEndApplicationModule;
import com.bucha.wrestlers.resources.BaseBackEndResource;
import com.bucha.wrestlers.util.PackageScanner;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import io.dropwizard.Application;
import io.dropwizard.Configuration;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import org.eclipse.jetty.servlets.CrossOriginFilter;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import java.util.EnumSet;
import java.util.List;

public class BackEndApplication extends Application<Configuration> {
    public static void main(String[] args) throws Exception {
        new BackEndApplication().run(args);
    }

    @Override
    public String getName() {
        return "SQLInterfaceBackEndApplication";
    }

    @Override
    public void initialize(Bootstrap<Configuration> bootstrap) {}

    @Override
    public void run(Configuration configuration,
                    final Environment environment) {
        configureCors(environment);
        Injector injector = Guice.createInjector(new BackEndApplicationModule());

        List<Class<?>> resourceClasses = PackageScanner.getClassesInPackage("com.bucha.wrestlers.resources");

        resourceClasses.stream()
                .filter(resourceClass -> resourceClass.getSuperclass().equals(BaseBackEndResource.class))
                .forEach(resourceClass -> environment.jersey().register(injector.getInstance(Key.get(resourceClass))));
    }

    private void configureCors(Environment environment) {
        final FilterRegistration.Dynamic cors =
                environment.servlets().addFilter("CORS", CrossOriginFilter.class);

        // Configure CORS parameters
        cors.setInitParameter(CrossOriginFilter.ALLOWED_ORIGINS_PARAM, "*");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_HEADERS_PARAM, "X-Requested-With,Content-Type,Accept,Origin,Authorization");
        cors.setInitParameter(CrossOriginFilter.ALLOWED_METHODS_PARAM, "OPTIONS,GET,PUT,POST,DELETE,HEAD");
        cors.setInitParameter(CrossOriginFilter.ALLOW_CREDENTIALS_PARAM, "true");

        // Add URL mapping
        cors.addMappingForUrlPatterns(EnumSet.allOf(DispatcherType.class), true, "/*");

    }

}


package root.bundles;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.jersey.setup.JerseyEnvironment;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import root.resources.HelloWorldResource;

public abstract class ResourceBundle<T extends Configuration> implements ConfiguredBundle<T> {
    public void run(T t, Environment environment) throws Exception {
        final JerseyEnvironment jersey = environment.jersey();

        jersey.register(new HelloWorldResource());
    }

    public void initialize(Bootstrap<?> bootstrap) {

    }
}

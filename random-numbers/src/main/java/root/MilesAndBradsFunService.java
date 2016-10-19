package root;

import io.dropwizard.Application;
import io.dropwizard.configuration.ConfigurationSourceProvider;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import root.bundles.ResourceBundle;

import java.io.IOException;
import java.io.InputStream;

public class MilesAndBradsFunService extends Application<MilesAndBradsFunServiceConfiguration> {

    public static final String NAME = "random-numbers";

    public static void main(String[] args) throws Exception {
        new MilesAndBradsFunService().run(args);
    }

    private final ResourceBundle<MilesAndBradsFunServiceConfiguration> resourceBundle = new ResourceBundle<MilesAndBradsFunServiceConfiguration>() {
    };

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void initialize(Bootstrap<MilesAndBradsFunServiceConfiguration> bootstrap){
        super.initialize(bootstrap);

        bootstrap.addBundle(resourceBundle);

    }

    public MilesAndBradsFunService() {
    }

    @Override
    public void run(MilesAndBradsFunServiceConfiguration milesAndBradsFunServiceConfiguration, Environment environment) throws Exception {

    }
}

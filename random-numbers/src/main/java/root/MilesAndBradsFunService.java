package root;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class MilesAndBradsFunService extends Application<MilesAndBradsFunServiceConfiguration> {

    public static final String NAME = "miles-and-brads-fun-service";

    public static void main(String[] args) throws Exception {
        new MilesAndBradsFunService().run(args);
    }

    //Bundles!!!

    public MilesAndBradsFunService() {
    }

    @Override
    public void run(MilesAndBradsFunServiceConfiguration milesAndBradsFunServiceConfiguration, Environment environment) throws Exception {

    }
}

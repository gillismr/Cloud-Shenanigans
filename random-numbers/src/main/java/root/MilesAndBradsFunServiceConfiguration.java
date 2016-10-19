package root;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.constraints.NotNull;

public class MilesAndBradsFunServiceConfiguration extends Configuration {

    @JsonProperty
    @NotNull
    String someKindOfURL;

    @JsonProperty
    public String getSomeKindOfURL() {
        return someKindOfURL;
    }

    @Override
    public String toString() {
        return "MilesAndBradsFunServiceConfiguration{" +
                "someKindOfURL='" + someKindOfURL + '\'' +
                '}';
    }
}

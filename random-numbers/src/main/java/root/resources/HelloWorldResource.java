package root.resources;


import com.google.common.base.Optional;
import root.dto.MessageDTO;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("hello/world")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorldResource {

    public HelloWorldResource() {
    }

    @GET
    public Optional<MessageDTO> helloWorld(){
        return Optional.of(new MessageDTO("Pepe was a good guy and your anime isn't trash.... also.... in case your still stuck on the issue... JET FUEL CAN NOT MELT STEEL BEAMS YA DINGUS"));
    }
}

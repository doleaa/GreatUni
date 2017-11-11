package com.bucha.wrestlers.resources;

import com.google.common.base.Optional;
import com.google.inject.Inject;
import lombok.RequiredArgsConstructor;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hi")
@RequiredArgsConstructor(onConstructor = @__(@Inject))
public class HelloWorldResource extends BaseBackEndResource {
    private final String name;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String sayHiAndGiveName(@QueryParam("name") Optional<String> userName) {
        if (userName.isPresent()) {
            return "Hi " + userName.get() + ". " +
                    "How are you today? " +
                    "My name is " + name + ". " +
                    "I hope we'll get to chat some more soon.;)";
        } else {
            return "Hi there. " +
                    "My name is " + name + ". " +
                    "What is yours??:D";
        }
    }
}

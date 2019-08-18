package broker;

import java.util.Map;
import org.json.simple.JSONObject;

import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/broker") 
@Singleton
public class Broker {

    TopicManager topic = new TopicManager();

    @GET
    @Path("/topic/{topicName}/{index}")
    public String getMessage(@PathParam("topicName") String topicName, @PathParam("index") int index) {
        return topic.getMessage(topicName, index).toString(); 
        
       
    }
    

    @POST
    @Path("/topic")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public boolean postMessage(@FormParam("topicName") String topicName, @FormParam("textMessage") String textMessage) {

        boolean result = topic.addMessage(topicName, textMessage);
        if (result) {
            return true;
        }
        return false;
    }
    
}

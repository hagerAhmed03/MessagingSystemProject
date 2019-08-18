package clients;

 
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.MediaType;

import javax.ws.rs.client.Entity;

public class Producer {

    String brokerIp;
    private Client client;
    private String REST_SERVICE_URL ;
    private static final String SUCCESS_RESULT = "<result>success</result>";
    private static final String PASS = "Added Successfully";
    private static final String FAIL = "Added failure";

    public Producer(String ip, String port) {
        this.client = ClientBuilder.newClient();
        this.REST_SERVICE_URL = "http://"+ip+":"+port+"/MessagingSystemProject/rest/broker/topic";
    }

    public void publish(String topicName, String textMessage) {
        Form form = new Form();
        form.param("topicName", topicName);
        form.param("textMessage", textMessage);
        String callResult = client.target(REST_SERVICE_URL).request(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.TEXT_PLAIN)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE), String.class);
        String result = FAIL;
        if (!SUCCESS_RESULT.equals(callResult)) {
            result = PASS;
        }
        System.out.println("AddMessage, Result: " + result);
    }

   
}

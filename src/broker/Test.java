package broker;

import org.glassfish.jersey.client.ClientResponse;
import org.json.simple.JSONObject;

//import com.mysql.cj.xdevapi.JsonParser;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

public class Test {

    private static void testDatabase() {
        DatabaseConnectionTwo db2 = new DatabaseConnectionTwo();
        db2.setDataSource();

        try (Connection connection = db2.getConnection();
                PreparedStatement pstmt = connection.prepareStatement("SELECT * FROM message");) {
            try (ResultSet resultSet = pstmt.executeQuery();) {
                while (resultSet.next()) {
                    System.out.println(
                            resultSet.getString(1) + "," + resultSet.getString(2) + "," + resultSet.getString(3));
                }
            } catch (Exception e) {
                connection.rollback();
                e.printStackTrace();
            }

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    private static void testgetManager() {
        TopicManager tb = new TopicManager();
        if (tb.getMessage("hrr", 1).get("status").equals(false))
            System.out.println(" " + tb.getMessage("hr", 2));

    }

    private static void testWebService() {
        Client client = ClientBuilder.newClient();
        String REST_SERVICE_URL = "http://localhost:8080/MessagingSystemProject/rest/broker/topic";
    
        String user = client.target(REST_SERVICE_URL)
                .path("/{topicName}")
                .resolveTemplate("topicName", "hr")
                .path("/{index}")
                .resolveTemplate("index", 1)
                .request(MediaType.APPLICATION_XML)
             // .accept(MediaType.APPLICATION_JSON_TYPE)
                .get(String.class);
        
        try {
            Object obj = new JSONParser().parse(user);
            JSONObject jsonObject = (JSONObject) obj;
            System.out.println(jsonObject.get("status"));
            
        } catch (ParseException e) {
            JSONObject obj = new JSONObject();
            obj.put("message", "json error ");
            obj.put("status", false);
            System.out.println(obj.get("status"));
        }
        

    }

    public static void main(String[] args) throws SQLException {

        // test database pool connection
        Test.testDatabase();

        // test topic manager specific getMessage function
        Test.testgetManager();

        // test web service get request
        Test.testWebService();

    }

}

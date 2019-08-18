package clients;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class Consumer {
	
	private String brokerIp;
	private String REST_SERVICE_URL;
	private String topicName;
	private int index;
	private String port;
	Client client= ClientBuilder.newClient();

	
	public Consumer(String brokerIp,String port,String topicName, int index) 
	{
		this.brokerIp=brokerIp;
		this.port=port;
		this.index =index;
		this.topicName = topicName;
		REST_SERVICE_URL ="http://"+this.brokerIp+":"+this.port+"/MessagingSystemProject/rest/broker/topic";
		    	
	}
	
	private JSONObject getMsg() 
	{
	    String res = client
		         .target(REST_SERVICE_URL)
		         .path("/{topicName}")
		         .resolveTemplate("topicName", topicName)
		         .path("/{index}")
		         .resolveTemplate("index", index)
		         .request(MediaType.APPLICATION_XML)
		         .get(String.class);
	    
	    try {
            Object obj = new JSONParser().parse(res);
            JSONObject jsonObject = (JSONObject) obj;
            return jsonObject;
            
        } catch (ParseException e) {
            JSONObject obj = new JSONObject();
            obj.put("message", "json error ");
            obj.put("status", false);
            return obj;
        }
	}
	
	public JSONObject getNext()
	{
		JSONObject msg =getMsg();
		
		if (msg.get("status").equals(true)) 
		{
			index = index+1;
		}
		return msg;
		
	}
	
	public JSONObject tryGetNext()
		{
		     
			
			JSONObject msg =getMsg();
				
			if (msg.get("status").equals(true)) 
			{
				index = index+1;
				return msg;
			}
			
			if (msg.get("message").equals("TOPIC NOT EXIST")) 
			{
				return msg;
			}
			else 
			{   
				try{
					TimeUnit.SECONDS.sleep(2);
					return tryGetNext();
				}
			    catch(Exception e){
			    	System.out.println(e);
			    	return msg ;
			    }
				
				
			}
			
			
		}
	
	}

   
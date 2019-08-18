package clients;


public class Test {

	
	private static void testConsumer() 
	{
		Consumer con1 = new Consumer("localhost","8080","hr",1);
		Consumer con2 = new Consumer("localhost","8080","hr",1);
		Consumer con3 = new Consumer("localhost","8080","hr",1);
		Consumer con4 = new Consumer("localhost","8080","hrr",1);
		
		// test all the message 
		for (int i=1;i<=2;i++)
			System. out.println(con1.getNext());
		System. out.println();
		
        // test out index 
		for (int i=1;i<=3;i++)
			System. out.println(con2.getNext());
		System. out.println();
		
		// test not exist topic  
        for (int i=1;i<=2;i++)
            System. out.println(con4.getNext());
        System. out.println();
		
        // test message with try 
		for (int i=1;i<=2;i++)
			System. out.println(con3.tryGetNext());

		
	}
	
	private static void tsetTopic(String topic,int index,int count) 
	{
        Consumer con = new Consumer("localhost","8080",topic,index);
        
        for (int i=1;i<=count;i++)
            System. out.println(con.getNext());
        System. out.println();
        
	    
	}
	public static void main(String[] args)
	{
	   
	    
	    // to test producer
        Producer test = new Producer("localhost","8080");
        //test.publish("engineer", "java developer haguuuur azza");
        
	    
		// test consumer 
		//Test.testConsumer();
		
       
        // test topic 
        Test.tsetTopic("engineer", 10,2);
			
	}


}

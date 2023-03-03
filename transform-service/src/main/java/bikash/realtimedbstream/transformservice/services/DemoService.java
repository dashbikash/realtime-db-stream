package bikash.realtimedbstream.transformservice.services;

import org.apache.kafka.common.protocol.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;

@Service
public class DemoService implements IDemoService {

	final String TOPIC="demo.topic";
	final String GROUPID="mygroup";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public void produce(String message) {
		JsonObject obj=new JsonObject();
		obj.addProperty("message",message);
		this.kafkaTemplate.send("demo.newtopic", obj.toString());
	}

	@KafkaListener(topics =  TOPIC,groupId = GROUPID)
	@Override
	public void consume(String message) {
		// TODO Auto-generated method stub
		System.out.println("Received Message:\n "+message);
	}

}

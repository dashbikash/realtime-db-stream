package bikash.realtimedbstream.translatorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class DemoService implements IDemoService {
	
	
	final String topic="demo.topic";
	final String groupId="group-id";
	
	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;
	
	@Override
	public void produce(String message) {
		this.kafkaTemplate.send(message, message);
	}

	@KafkaListener(topics = topic,groupId = groupId)
	@Override
	public void consume(String message) {
		// TODO Auto-generated method stub
		System.out.println("Received Message: "+message);
	}

}

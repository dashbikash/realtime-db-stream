package bikash.realtimedbstream.transformservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import bikash.realtimedbstream.transformservice.model.Employees;

@Component
public class TransformService {
	final String SOURCE_TOPIC = "mysql-localhost.employeedb.employees";
	final String TARGET_TOPIC = "mongo-localhost.employeedb.employees";
	final String GROUPID = "mygroup";
	final Gson gson = new GsonBuilder().create();

	@Autowired
	KafkaTemplate<String, String> kafkaTemplate;

	private void produce(String message) {
		System.out.println("Producing: " + message);
		this.kafkaTemplate.send(TARGET_TOPIC, message);
	}

	@KafkaListener(topics = SOURCE_TOPIC, groupId = GROUPID)
	public void consume(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message) {
		if (!message.isBlank() && !message.isEmpty()) {
			Gson gson = new Gson();
			JsonObject msgObj = gson.fromJson(message, JsonObject.class);
			String op = msgObj.get("payload").getAsJsonObject().get("op").getAsString();
			JsonObject newObj = new JsonObject();
			newObj.addProperty("op", op);
			if (op.equalsIgnoreCase("c")) {
				newObj.add("after", msgObj.get("payload").getAsJsonObject().get("after").getAsJsonObject());
			}
			if (op.equalsIgnoreCase("u")) {

				newObj.add("before", msgObj.get("payload").getAsJsonObject().get("before").getAsJsonObject());
				newObj.add("after", msgObj.get("payload").getAsJsonObject().get("after").getAsJsonObject());
			}
			if (op.equalsIgnoreCase("d")) {
				newObj.add("before", msgObj.get("payload").getAsJsonObject().get("before").getAsJsonObject());
			}
			String translated = newObj.toString();
			System.out.println("Translated:\n" + translated);
			this.produce(translated);
		}
	}
}

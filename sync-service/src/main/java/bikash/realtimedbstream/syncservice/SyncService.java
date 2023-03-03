package bikash.realtimedbstream.syncservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import bikash.realtimedbstream.syncservice.model.Employees;

@Component
public class SyncService {
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@KafkaListener(topics = "mongo-localhost.employeedb.employees", groupId = "mygroup")
	public void consume(@Header(KafkaHeaders.RECEIVED_TOPIC) String topic, @Payload String message) {
		Gson gson = new Gson();
		JsonObject msgObj = gson.fromJson(message, JsonObject.class);
		String op = msgObj.getAsJsonObject().get("op").getAsString();
		JsonObject newObj = new JsonObject();
		newObj.addProperty("op", op);
		if (op.equalsIgnoreCase("c")) {
			Employees newEmp = gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
			mongoTemplate.insert(newEmp);
		}
		if (op.equalsIgnoreCase("u")) {
			Employees existingEmp = gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
			Employees newEmp = gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
			Employees emp = mongoTemplate.findOne(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())),
					Employees.class);
			mongoTemplate.findAndReplace(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())), newEmp);
		}
		if (op.equalsIgnoreCase("d")) {
			Employees existingEmp = gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
			mongoTemplate.remove(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())), Employees.class);
		}
		System.out.println(topic + " : " + message);
	}
}

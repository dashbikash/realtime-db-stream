package bikash.realtimedbstream.consumerservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import bikash.realtimedbstream.consumerservice.model.Employees;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class ConsumerserviceApplication implements CommandLineRunner{
	@Autowired
	private MongoTemplate mongoTemplate;
	
	@GetMapping
	private Mono<String> index(){
		return Mono.just("Hello ! this is consumer service");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(ConsumerserviceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sample="\n"
				+ "{\n"
				+ "	\"op\":\"d\",\n"
				+ "      \"before\": {\n"
				+ "        \"empid\": 2,\n"
				+ "        \"fname\": \"bikash\",\n"
				+ "        \"lname\": \"dash\",\n"
				+ "        \"dept\": \"it\",\n"
				+ "        \"email\": \"bikash@wipro.com\",\n"
				+ "        \"dob\": 521078400000\n"
				+ "      },\n"
				+ "      \"after\": {\n"
				+ "        \"empid\": 2,\n"
				+ "        \"fname\": \"bikash\",\n"
				+ "        \"lname\": \"dash\",\n"
				+ "        \"dept\": \"it\",\n"
				+ "        \"email\": \"bikashprakashdash@wipro.com\",\n"
				+ "        \"dob\": 521078400000\n"
				+ "      }\n"
				+ "	}\n"
				+ "";

		
		Gson gson=new Gson();
		JsonObject msgObj=gson.fromJson(sample,JsonObject.class);
		String op=msgObj.getAsJsonObject().get("op").getAsString();
		JsonObject newObj=new JsonObject();
		newObj.addProperty("op",op);
		if(op.equalsIgnoreCase("c")) {
			Employees newEmp=gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
			mongoTemplate.insert(newEmp);
		}
		if(op.equalsIgnoreCase("u")) {
			Employees existingEmp=gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
			Employees newEmp=gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
			Employees emp= mongoTemplate.findOne(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())), Employees.class);
			mongoTemplate.findAndReplace(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())),newEmp);
		}
		if(op.equalsIgnoreCase("d")) {
			Employees existingEmp=gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
			mongoTemplate.remove(Query.query(Criteria.where("_id").is(existingEmp.getEmpid())), Employees.class);
		}
	}

}

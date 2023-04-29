package bikash.realtimedbstream.syncservice;

import java.util.Date;
import java.util.Properties;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import bikash.realtimedbstream.syncservice.model.Employees;
import bikash.realtimedbstream.syncservice.model.UnixTimestampAdapter;
import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class SyncServiceApplication implements CommandLineRunner {
	@GetMapping
	private Mono<String> index() {
		return Mono.just("Hello ! this is consumer service");
	}

	public static void main(String[] args) {
		SpringApplication.run(SyncServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String sample="\n"
				+ "{\n"
				+ "	\"op\":\"c\",\n"
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
				+ "	}";

		Properties properties = new Properties();
		// properties.setProperty("ssl", "true");
		// properties.setProperty("sslmode", "NONE"); // NONE to trust all servers; STRICT for trusted only
		
		Gson gson = new GsonBuilder()
                .registerTypeAdapter(Date.class, new UnixTimestampAdapter())
                .create();
		JsonObject msgObj=gson.fromJson(sample,JsonObject.class);
		String op=msgObj.getAsJsonObject().get("op").getAsString();
		JsonObject newObj=new JsonObject();
		newObj.addProperty("op",op);
		if(op.equalsIgnoreCase("c")) {
			Employees newEmp=gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
			System.out.println(newEmp.getDob().toGMTString());
			
		}
		if(op.equalsIgnoreCase("u")) {
			Employees existingEmp=gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
			Employees newEmp=gson.fromJson(msgObj.get("after").getAsJsonObject(), Employees.class);
		}
		if(op.equalsIgnoreCase("d")) {
			Employees existingEmp=gson.fromJson(msgObj.get("before").getAsJsonObject(), Employees.class);
		}	
		ClickHouseUtil.initDB();
	}
}

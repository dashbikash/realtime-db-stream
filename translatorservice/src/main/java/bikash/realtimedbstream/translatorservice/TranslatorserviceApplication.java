package bikash.realtimedbstream.translatorservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@SpringBootApplication
@RestController
public class TranslatorserviceApplication {
	
	@GetMapping
	private Mono<String> index(){
		return Mono.just("Hello ! this is translator service");
	}
	
	public static void main(String[] args) {
		SpringApplication.run(TranslatorserviceApplication.class, args);
	}

}

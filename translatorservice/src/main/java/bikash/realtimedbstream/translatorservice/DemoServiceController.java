package bikash.realtimedbstream.translatorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import bikash.realtimedbstream.translatorservice.services.DemoService;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/demo")
public class DemoServiceController {
	@Autowired
	DemoService service;
	
	@GetMapping("/produce")
	private Mono<String> produce(@RequestParam("message")String message){
		this.service.produce(message);
		return Mono.just("Your Sent Message: "+message);
	}
}

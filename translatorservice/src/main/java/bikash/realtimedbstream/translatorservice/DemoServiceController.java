package bikash.realtimedbstream.translatorservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/kafka")
public class DemoServiceController {
	@Autowired
	DemoService service;
	
	@PostMapping("/produce")
	private Mono<String> produce(@RequestParam("message")String message){
		return Mono.just("Your Sent Message: "+message);
	}
}

package bikash.realtimedbstream.transformservice.services;

public interface IDemoService {
	void produce(String message);
	void consume(String message);
}

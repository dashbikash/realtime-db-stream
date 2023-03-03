package bikash.realtimedbstream.translatorservice.services;

public interface IDemoService {
	void produce(String message);
	void consume(String message);
}

package bikash.realtimedbstream.translatorservice;


public interface IDemoService
{
	void produce(String message);
	void consume(String message);
}
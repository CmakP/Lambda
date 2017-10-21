package examples;

public class TargetType {

	public interface Runnable {
	    void run();
	}

	public interface Callable<V> {
	    V call();
	}

	static void invoke(Runnable r) {
	    r.run();
	}

	static <T> T invoke(Callable<T> c) {
	    return c.call();
	}
	
	public static void main(String... args) {
		String s = invoke(() -> "done");
		System.out.println(s);
	}
}

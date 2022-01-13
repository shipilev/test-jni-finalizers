public class Test {

	static {
		System.loadLibrary("test");
		Thread t = new Thread(() -> {
			while(true) {
				System.gc();
				System.runFinalization();
				try {
					Thread.sleep(0, 1);
				} catch (Exception e) {};
			}
		});
		t.setDaemon(true);
		t.start();
	}

	static final Object O = new Object();

	static class Carrier {
		Object x;
		Carrier(Object x) { this.x = x; }
		protected void finalize() throws Throwable {
			super.finalize();
			x = null;
		}
	}

	static void javaM() {
		Carrier c = new Carrier(O);
		Object x = callJava(c);
		if (x == null) {
			throw new IllegalStateException();
		}
	}

	static void nativeM() {
		Carrier c = new Carrier(O);
		Object x = callNative(c);
		if (x == null) {
			throw new IllegalStateException();
		}
	}

	static Object callJava(Carrier c) {
	   return c.x;
	}

	static native Object callNative(Carrier c);

	static void javaTest() {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < 10_000) {
			for (int c = 0; c < 1_000_000; c++) {
				javaM();
			}
		}
	}

	static void nativeTest() {
		long start = System.currentTimeMillis();
		while (System.currentTimeMillis() - start < 10_000) {
			for (int c = 0; c < 1_000_000; c++) {
				nativeM();
			}
		}
	}

	public static void main(String... args) {
		if (args[0].equals("java")) {
			javaTest();
		} else if (args[0].equals("native")) {
			nativeTest();
		} else {
			throw new RuntimeException(args[0]);
		}
	}

}

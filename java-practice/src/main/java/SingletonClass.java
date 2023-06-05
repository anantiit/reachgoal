
public class SingletonClass {
	private static final SingletonClass singletonClass;

	private SingletonClass() {
	}

	public static SingletonClass getInstance() {
		if (singletonClass == null) {
			synchronized (SingletonClass.class) {
				if (singletonClass == null) {
					singletonClass = new SingletonClass();
				}
				// return singletonClass;
			}
		}
		return singletonClass;
	}
}

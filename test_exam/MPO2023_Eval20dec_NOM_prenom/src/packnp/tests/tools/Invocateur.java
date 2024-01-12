package packnp.tests.tools;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @version 2023_10_19 sans Thread.stop()
 */
public class Invocateur implements Callable<Integer>{
	private Method methode;
 
	public Invocateur(Method methode) {
		this.methode = methode;
	}
 
	@Override
	public Integer call() throws Exception {
		try {
			return (Integer)(methode.invoke(null));
		} catch (Exception e) {
			if (e instanceof java.lang.reflect.InvocationTargetException) {
				e.getCause().printStackTrace();
			} else {
				System.out.println("Votre code a provoque un TIME OUT en demandant plus de temps qu'alloue");
				e.printStackTrace();
			}
		}
		return null;
	}
}

package exceptions;

/**
 * <i>BadEntryException</i> is raised when some <i>ISocialNetwork</i> method is called
 * with a non-correct parameter value.
 * 
 */

public class BadEntryException extends Exception {

	public BadEntryException(String message) {
		super(message);
	}
}

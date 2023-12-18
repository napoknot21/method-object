package exceptions;

/**
 * <i>NotItemException</i> is raised when some item can not be found in the
 * <i>ISocialNetwork</i>
 * 
 */
public class NotItemException extends Exception {

	public NotItemException(String message) {
		super(message);
	}
}

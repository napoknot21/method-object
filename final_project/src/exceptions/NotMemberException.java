package exceptions;

/**
 * <i>NotMemberException</i> is raised when some member can not be found in the
 * <i>ISocialNetwork</i>
 * 
 */
public class NotMemberException extends Exception {

	public NotMemberException(String message) {
		super(message);
	}

}

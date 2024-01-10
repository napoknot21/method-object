package exceptions;

/**
 * <i>MemberAlreadyExistsException</i> is raised when trying to add a new member whereas
 * a member with the same pseudo is already registered in the
 * <i>ISocialNetwork</i> (same pseudo : not case-sensitive and leadings/trailings
 * blanks are not taken into account)
 * 
 */
public class MemberAlreadyExistsException extends Exception {

}

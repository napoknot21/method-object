package exceptions;

/**
 * <i>ItemFilmAlreadyExistsExceptiopn</i> is raised when trying to add a new film whereas
 * a film with the same name is already registered in the <i>ISocialNetwork</i>
 * (same name : not case-sensitive and leadings/trailings blanks are not taken
 * into account)
 * 
 */
public class ItemFilmAlreadyExistsException extends Exception {

}

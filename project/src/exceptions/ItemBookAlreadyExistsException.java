package exceptions;

/**
 * <i>ItemBookAlreadyExistsException</i> is raised when trying to add a new book whereas
 * a book with the same name is already registered in the <i>ISocialNetwork</i>
 * (same name : not case-sensitive and leadings/trailings blanks are not taken
 * into account)
 * 
 */
public class ItemBookAlreadyExistsException extends Exception {

}

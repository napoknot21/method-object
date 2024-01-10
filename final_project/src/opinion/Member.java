package opinion;

import java.util.LinkedList;

/**
 * Represents a member in the social network.
 * This class contains personal details of a member and their associated social network.
 */
public class Member {

    /**
     * Username (pseudonym) of the member.
     */
    private String login;

    /**
     * Password of the member.
     */
    private String password;

    /**
     * A brief description of the member.
     */
    private String description;

    /**
     * The associated social network of the member.
     */
    private SocialNetwork socialNetwork;


    /**
     * Constructs a new Member with specified details.
     * 
     * @param login          Username of the member.
     * @param password       Password of the member.
     * @param description    A brief description of the member.
     * @param socialNetwork  The associated social network of the member.
     */
    public Member (String login, String password, String description, SocialNetwork socialNetwork) {

        this.login = login;
        this.password = password;
        this.description = description;
        this.socialNetwork = socialNetwork;

    }


    /**
     * Retrieves the username of the member.
     * 
     * @return The username of the member.
     */
    public String getLogin () {

        return this.login;

    }


    /**
     * Retrieves the password of the member.
     * 
     * @return The password of the member.
     */
    public String getPassword () {

        return this.password;

    }


    /**
     * Retrieves the description of the member.
     * 
     * @return The description of the member.
     */
    public String getDescription () {

        return this.description;

    }


    /**
     * Retrieves the associated social network of the member.
     * 
     * @return The social network associated with the member.
     */
    public SocialNetwork getSocialNetwork () {

        return this.socialNetwork;

    }

    
    /**
     * Updates the username of the member.
     * If the new username is already taken or the same as the current one, it prints an appropriate message.
     * 
     * @param newLogin The new username of the member.
     */
    public void setLogin (String newLogin) {

        if (newLogin.equalsIgnoreCase(this.getLogin())) {

            SocialNetwork sn = this.getSocialNetwork();

            for (Member m : sn.getMembers()) {

                if (m.getLogin().equalsIgnoreCase(newLogin)) {

                    System.out.println("New Login is already taken. Login not changed");

                }

            }

            this.login = newLogin;
            return;

        }
        System.out.println("The new Login is the same. Login not changed");
    }


    /**
     * Updates the password of the member.
     * Changes the password only if the new password is different from the current one.
     * 
     * @param newPassword The new password of the member.
     */
    public void setPassword (String newPassword) {

        if (newPassword.equals(this.getPassword())) this.password = newPassword;

    }


    /**
     * Updates the description of the member.
     * Changes the description only if the new description is different from the current one.
     * 
     * @param newDescription The new description of the member.
     */
    public void setDescription (String newDescription) {

        if (!newDescription.equals(this.getDescription())) this.description = newDescription;

    }


    /**
     * Updates the profile of the member including login, password, and description.
     * Changes are made only if the new values are non-null and not empty.
     * 
     * @param newLogin        The new username of the member.
     * @param newPassword     The new password of the member.
     * @param newDescription  The new description of the member.
     */
    public void updateProfile (String newLogin, String newPassword, String newDescription) {

        if (newLogin != null && newLogin.trim().length() != 0) this.setLogin(newLogin);
        if (newPassword != null && newPassword.trim().length() != 0) this.setPassword(newPassword);
        if (newDescription!= null && newDescription.trim().length()!= 0) this.setDescription(newDescription);

    }


}

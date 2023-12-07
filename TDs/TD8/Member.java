public class Member {


    private String login;


    public Member (String login) throw BadEntryException {

        if (login == null) {

            throw new BadEntryException ("Le login n'est pas instancié");
        
        }

        if (login.trim().length() < 1) {

            throw new BadEntryException("Erreur longuer pour le login");

        }

        this.login = login.trim();
    }


    public String getLogin() { return this.login; }


    public boolean equals (Object object) {

        if (!(object instanceof Member)) {
            
            return false;

        }
        
        Member m = (Member) objet;

        return this.login.equalsIgnoreCase(m.login);
    }






}

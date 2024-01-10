package opinion;

import exceptions.BadEntryException;

public class Member {
	private String login;
	private String password;
	private String profile;

	public Member(String login , String password , String profile) throws BadEntryException{
		this.login=login;
		this.password=password;
		this.profile=profile;
		if (login == null){
			throw new BadEntryException("Le login n'est pas instancié");
			}
			if (login.trim().length() < 1){
			throw new BadEntryException("Erreur longueur pour le login");
			}
			this.login = login.trim();
	}
	
	public String getLogin() {
		return this.login;
	}
	public String getPassword() {
		return this.password;
	}
	
	public String getProfile() {
		return this.profile;
	}
	
	public boolean equals(Object o) {
		if (!(o instanceof Member)) {return false;}
		return true;
	}
	public boolean checkpassword(String password) { return this.password.equals(password);}
	
	
	
}

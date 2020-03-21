package persistance.session;

import mediatek2020.items.Utilisateur;

public class MediaSession {
	
	private Utilisateur user;
	private String info;
	
	public MediaSession(Utilisateur user) {
		this.user = user;
		this.info = "";
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public Utilisateur getUser() {
		return user;
	};
	

}

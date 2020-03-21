package persistance.session.users;

import mediatek2020.items.Utilisateur;

public class Biblioth�caire implements Utilisateur{

	private int numUtilisateur;
    private String nomUtilisateur, adresseIP;
	
    public Biblioth�caire(int numUtilisateur, String nomUtilisateur, String adresseIP) {
        this.numUtilisateur = numUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
    }


    public String name() {
        return nomUtilisateur;
    }

    @Override
    public boolean isBibliothecaire() {
        return true;
    }

    @Override
    public Object[] data() {
        return new Object[] {numUtilisateur,nomUtilisateur,adresseIP};
    }

}
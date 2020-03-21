package persistance.session.users;

import mediatek2020.items.Utilisateur;

public class Abonné implements Utilisateur {

    private int numUtilisateur;
    private String nomUtilisateur, adresseIP;

    public Abonné(int numUtilisateur, String nomUtilisateur, String adresseIP){
        this.numUtilisateur = numUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.adresseIP = adresseIP;
    }

    public String name() {
        return nomUtilisateur;
    }

    @Override
    public boolean isBibliothecaire() {
        return false;
    }

    @Override
    public Object[] data() {
        return new Object[] {numUtilisateur,nomUtilisateur,adresseIP};
    }

}

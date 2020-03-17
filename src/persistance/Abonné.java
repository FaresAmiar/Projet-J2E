package persistance;

import mediatek2020.items.Utilisateur;

public class Abonn� implements Utilisateur {

    private int numUtilisateur;

    public Abonn�(int numUtilisateur, String nomUtilisateur){
        this.numUtilisateur = numUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
    }

    private String nomUtilisateur;

    public String name() {
        return nomUtilisateur;
    }

    @Override
    public boolean isBibliothecaire() {
        return false;
    }

    @Override
    public Object[] data() {
        return new Object[] {numUtilisateur,nomUtilisateur};
    }

}

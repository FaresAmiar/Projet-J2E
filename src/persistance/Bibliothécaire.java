package persistance;

import mediatek2020.items.Utilisateur;

public class Bibliothécaire implements Utilisateur{

    public Bibliothécaire(int numUtilisateur, String nomUtilisateur) {
        this.numUtilisateur = numUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
    }

    private int numUtilisateur;
    private String nomUtilisateur;

    public String name() {
        return nomUtilisateur;
    }

    @Override
    public boolean isBibliothecaire() {
        return true;
    }

    @Override
    public Object[] data() {
        return new Object[] {numUtilisateur,nomUtilisateur};
    }

}
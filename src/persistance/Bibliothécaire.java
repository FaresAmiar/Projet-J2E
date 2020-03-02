package persistance;

import mediatek2020.items.Utilisateur;

public class Bibliothécaire implements Utilisateur {

    public Bibliothécaire(int numUtilisateur, String nomUtilisateur, boolean isBibliothecaire) {
        this.numUtilisateur = numUtilisateur;
        this.nomUtilisateur = nomUtilisateur;
        this.isBibliothecaire = isBibliothecaire;
    }

    private int numUtilisateur;
    private String nomUtilisateur;
    private boolean isBibliothecaire;

    @Override
    public String name() {
        return nomUtilisateur;
    }

    @Override
    public boolean isBibliothecaire() {
        return isBibliothecaire;
    }

    @Override
    public Object[] data() {
        return new Object[0];
    }
}

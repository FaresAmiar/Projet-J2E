package persistance.docs;

public class Livre extends AbstractDocument {

    public Livre(int numDoc, String titreDoc, String auteurDoc) {
        super(numDoc,titreDoc,auteurDoc);
    }

    public Livre(int numDoc, int numUtilisateur, String titreDoc, String auteurDoc, String statutDoc) {
        super(numDoc,numUtilisateur,titreDoc,auteurDoc, statutDoc);
    }

}
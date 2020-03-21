package persistance.docs;

public class CD extends AbstractDocument {

    public CD(int numDoc, String titreDoc, String auteurDoc) {
        super(numDoc,titreDoc,auteurDoc);
    }

    public CD(int numDoc, int numUtilisateur, String titreDoc, String auteurDoc, String statutDoc) {
        super(numDoc,numUtilisateur,titreDoc,auteurDoc, statutDoc);
    }

}
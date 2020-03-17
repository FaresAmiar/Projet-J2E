package persistance;

public class DVD extends AbstractDocument {

    public DVD(int numDoc, String titreDoc, String auteurDoc) {
        super(numDoc,titreDoc,auteurDoc);
    }

    public DVD(int numDoc, int numUtilisateur, String titreDoc, String auteurDoc, String statutDoc) {
        super(numDoc,numUtilisateur,titreDoc,auteurDoc, statutDoc);
    }

}
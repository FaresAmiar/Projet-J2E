package persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mediatek2020.*;
import mediatek2020.items.*;

// classe mono-instance  dont l'unique instance est injectï¿½e dans Mediatheque
// via une auto-dï¿½claration dans son bloc static

public class MediathequeData implements PersistentMediatheque {
// Jean-Franï¿½ois Brette 01/01/2018

	private Connection co;

	static {
		Mediatheque.getInstance().setData(new MediathequeData());
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","etudiant","ETUDIANT");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private MediathequeData() {
	}

	// renvoie la liste de tous les documents de la bibliothï¿½que
	@Override
	public List<Document> tousLesDocuments() {
		List<Document> documents = new ArrayList<>();
		try {

			String requete = "Select * from Document";
			Statement st = co.createStatement();
			ResultSet rs = (ResultSet) st.executeQuery(requete);
			while(rs.next()) {
				int numDoc = rs.getInt("NumDoc"), typeDoc = rs.getInt("TypeDoc");
				String titreDoc = rs.getString("TitreDoc"), auteurDoc = rs.getString("AuteurDoc");
				Document doc = null;
				switch(typeDoc) {
					case 0 : doc = new Livre(numDoc,titreDoc,auteurDoc);
						break;
					case 1 : doc = new DVD(numDoc,titreDoc,auteurDoc);
						break;
					case 2 : doc = new CD(numDoc,titreDoc,auteurDoc);
						break;
				}
				documents.add(doc);
			}
			co.close();
		}catch(SQLException s) {
			s.printStackTrace();
		}
		return documents;
	}

	// va rï¿½cupï¿½rer le User dans la BD et le renvoie
	// si pas trouvï¿½, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		String requete = "Select * from Utilisateur Where loginUtilisateur = ? AND passwordUtilisateur = ? ";
		PreparedStatement pstd = co.prepareStatement(requete);
		pstd.setString(0, login);
		pstd.setString(1, password);
		ResultSet rs = (ResultSet) pstd.executeQuery();
		Utilisateur user = null;
		while(rs.next()) {
			if(rs.getInt("typeUtilisateur")==0) {
				user = new Abonné(rs.getInt("numUtilisateur"),rs.getString("nomUtilisateur"),true);
			}else {
				user = new Bibliothécaire(rs.getInt("numUtilisateur"),rs.getString("nomUtilisateur"),false);
			}
		}
		return user;
	}

	// va rï¿½cupï¿½rer le document de numï¿½ro numDocument dans la BD
	// et le renvoie
	// si pas trouvï¿½, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		String requete = "Select * from Document Where numDoc = ?";
		PreparedStatement pstd = co.prepareStatement(requete);
		pstd.setInt(0, numDocument);
		ResultSet rs = (ResultSet) pstd.executeQuery();
		Document doc = null;
		while(rs.next()) {
			switch (rs.getInt("TypeDoc")) {
			case 0:
				doc = new Livre(rs.getInt("NumDoc"),rs.getString("TitreDoc"),rs.getString("AuteurDoc"));
				break;
			case 1:
				doc = new DVD(rs.getInt("NumDoc"),rs.getString("TitreDoc"),rs.getString("AuteurDoc"));
				break;
			case 2:
				doc = new CD(rs.getInt("NumDoc"),rs.getString("TitreDoc"),rs.getString("AuteurDoc"));
				break;
			default:
				break;
			}
		}
		return user;	
	}

	@Override
	public void nouveauDocument(int type, Object... args) {
		// args[0] -> le titre
		// args [1] --> l'auteur
		// etc...
	}

}

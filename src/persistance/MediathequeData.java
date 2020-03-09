package persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mediatek2020.*;
import mediatek2020.items.*;

// classe mono-instance  dont l'unique instance est injectée dans Mediatheque
// via une auto-déclaration dans son bloc static

public class MediathequeData implements PersistentMediatheque {
// Jean-François Brette 01/01/2018

	private static Connection co;

	static {
		Mediatheque.getInstance().setData(new MediathequeData());
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","etudiant","ETUDIANT");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	private MediathequeData() {
	}

	// renvoie la liste de tous les documents de la bibliothèque
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
		PreparedStatement pstd;
		try {
			pstd = co.prepareStatement(requete);
			pstd.setString(1, login);
			pstd.setString(2, password);
			ResultSet rs = (ResultSet) pstd.executeQuery();
			Utilisateur user = null;
			while(rs.next()) {
				if(rs.getInt("typeUtilisateur")==0) {
					user = new Abonné(rs.getInt("numUtilisateur"),rs.getString("loginUtilisateur"));
				}else {
					user = new Bibliothécaire(rs.getInt("numUtilisateur"),rs.getString("loginUtilisateur"));
				}
			}
			return user;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	// va récupérer le document de numéro numDocument dans la BD
	// et le renvoie
	// si pas trouvé, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		try {
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
		return doc;	
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void nouveauDocument(int type, Object... args) {
		// args[0] -> le titre
				// args [1] --> l'auteur
				// etc...
				Document doc = null;
				int numDoc = (int) args[0];
				String titreDoc = (String) args[1], auteurDoc = (String) args[2];
				String requete = "Insert into Document(TitreDoc,AuteurDoc,TypeDoc) values (seq_Document.nextVal,?,?,?)";
				try {
					PreparedStatement ptstmt = co.prepareStatement(requete);
					ptstmt.setString(2,titreDoc); ptstmt.setString(3,auteurDoc); ptstmt.setInt(4,type);
					ptstmt.executeUpdate();
				} catch (SQLException e) {
					e.printStackTrace();
				}
	}

}

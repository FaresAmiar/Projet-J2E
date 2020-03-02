package persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mediatek2020.*;
import mediatek2020.items.*;

// classe mono-instance  dont l'unique instance est inject�e dans Mediatheque
// via une auto-d�claration dans son bloc static

public class MediathequeData implements PersistentMediatheque {
// Jean-Fran�ois Brette 01/01/2018

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

	// renvoie la liste de tous les documents de la biblioth�que
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

	// va r�cup�rer le User dans la BD et le renvoie
	// si pas trouv�, renvoie null
	@Override
	public Utilisateur getUser(String login, String password) {
		return null;
	}

	// va r�cup�rer le document de num�ro numDocument dans la BD
	// et le renvoie
	// si pas trouv�, renvoie null
	@Override
	public Document getDocument(int numDocument) {
		return null;
	}

	@Override
	public void nouveauDocument(int type, Object... args) {
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

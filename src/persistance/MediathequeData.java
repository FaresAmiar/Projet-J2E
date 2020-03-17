package persistance;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import mediatek2020.*;
import mediatek2020.items.*;


public class MediathequeData implements PersistentMediatheque {

	private static Connection co;

	static {
		Mediatheque.getInstance().setData(new MediathequeData());
	}

	private MediathequeData() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","root");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// renvoie la liste de tous les documents de la bibliothèque
	@Override
	public List<Document> tousLesDocuments() {
		List<Document> documents = new ArrayList<>();
		Integer numUtilisateur = null;
		try {
			
			String requete = "Select * from Document";
			Statement st = co.createStatement();
			ResultSet rs = (ResultSet) st.executeQuery(requete);
			while(rs.next()) {
				numUtilisateur = rs.getInt("numUtilisateur");
				int numDoc = rs.getInt("NumDoc"), typeDoc = rs.getInt("TypeDoc");
				String titreDoc = rs.getString("TitreDoc"), auteurDoc = rs.getString("AuteurDoc"), statutDoc = rs.getString("statutDoc");
				Document doc = null;
				switch(typeDoc) {
					case 0 : doc = numUtilisateur == null ? new Livre(numDoc,titreDoc,auteurDoc) :
						new Livre(numDoc, numUtilisateur,titreDoc,auteurDoc,statutDoc);
						break;
					case 1 : doc = numUtilisateur == null ? new DVD(numDoc,titreDoc,auteurDoc) :
						new DVD(numDoc, numUtilisateur,titreDoc,auteurDoc,statutDoc);
						break;
					case 2 : doc = numUtilisateur == null ? new CD(numDoc,titreDoc,auteurDoc) :
						new CD(numDoc, numUtilisateur,titreDoc,auteurDoc,statutDoc);
						break;
				}
				documents.add(doc);
			}
		}catch(SQLException s) {
			s.printStackTrace();
		}
		return documents;
	}

	// va récupérer le User dans la BD et le renvoie
	// si pas trouvé, renvoie null
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
		Integer numUtilisateur = null;
		try {
			String requete = "Select * from Document Where numDoc = ?";
			PreparedStatement pstd = co.prepareStatement(requete);
			pstd.setInt(1, numDocument);
			ResultSet rs = (ResultSet) pstd.executeQuery();
			Document doc = null;
			while(rs.next()) {
				numUtilisateur = rs.getInt("numUtilisateur");
				int numDoc = rs.getInt("numDoc"), typeDoc = rs.getInt("typeDoc");
				String titreDoc = rs.getString("titreDoc"),
						auteurDoc = rs.getString("auteurDoc"), statutDoc = rs.getString("statutDoc");
				switch (typeDoc) {
				case 0:
					doc = numUtilisateur == null ? new Livre(numDoc,titreDoc,auteurDoc) :
						new Livre(numDoc,numUtilisateur,titreDoc,auteurDoc,statutDoc);
					break;
				case 1:
					doc = numUtilisateur == null ? new DVD(numDoc,titreDoc,auteurDoc) :
						new DVD(numDoc,numUtilisateur,titreDoc,auteurDoc,statutDoc);
					break;
				case 2:
					doc = numUtilisateur == null ? new CD(numDoc,titreDoc,auteurDoc) :
						new CD(numDoc,numUtilisateur,titreDoc,auteurDoc,statutDoc);
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
				String titreDoc = (String) args[1], auteurDoc = (String) args[2];
				String requete = "Insert into Document(numDoc,TitreDoc,AuteurDoc,TypeDoc,statutDoc) values (seq_Document.nextVal,?,?,?,'disponible')";
				try {
					PreparedStatement ptstmt = co.prepareStatement(requete);
					ptstmt.setString(1,titreDoc); 
					ptstmt.setString(2,auteurDoc); ptstmt.setInt(3,type);
					ptstmt.executeUpdate();

				} catch (SQLException e) {
					e.printStackTrace();
				}
	}

}
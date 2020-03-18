package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import mediatek2020.items.Document;
import mediatek2020.items.EmpruntException;
import mediatek2020.items.ReservationException;
import mediatek2020.items.RetourException;
import mediatek2020.items.Utilisateur;

public class AbstractDocument implements Document {
	
	private int numDoc,numUtilisateur;
	private String titreDoc,auteurDoc, statutDoc;
	private Connection co;
	
	public AbstractDocument(int numDoc, int numUtilisateur, String titreDoc, String auteurDoc, String statutDoc) {
		this.numDoc = numDoc;
		this.numUtilisateur = numUtilisateur;
		this.titreDoc = titreDoc;
		this.auteurDoc = auteurDoc;
		this.statutDoc = statutDoc;
		
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
	
	public AbstractDocument(int numDoc, String titreDoc, String auteurDoc) {
		this.numDoc = numDoc;
		this.titreDoc = titreDoc;
		this.auteurDoc = auteurDoc;
		this.statutDoc = "disponible";
	}

	@Override
	public Object[] data() {
		return new Object[] {numDoc,numUtilisateur,titreDoc,auteurDoc,statutDoc};
	}

	@Override
	public void emprunter(Utilisateur utilisateur) throws EmpruntException {
		// TODO Auto-generated method stub
		synchronized(this) {
			try {
					if(!statutDoc.equals("disponible"))
						throw new EmpruntException();
			
					String requete = "Update Document Set statutDoc = 'emprunté', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setInt(1,Integer.parseInt((String) utilisateur.data()[0]));
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
				}
			catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}

	@Override
	public void rendre(Utilisateur utilisateur) throws RetourException {
		// TODO Auto-generated method stub
		synchronized(this) {
			try {
					if(statutDoc.equals("disponible"))
						throw new RetourException();
					
					String requete = "Update Document Set statutDoc = 'disponible', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setInt(1,Integer.parseInt((String) utilisateur.data()[0]));
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
				}
				catch (SQLException e) {
					e.printStackTrace();
			}
		}
	}

	@Override
	public void reserver(Utilisateur utilisateur) throws ReservationException {
		// TODO Auto-generated method stub
		synchronized(this) {
			try {
					if(!statutDoc.equals("disponible"))
						throw new ReservationException();
					
					String requete = "Update Document Set statutDoc = 'réservé', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setInt(1,Integer.parseInt((String) utilisateur.data()[0]));
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
				}catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
	}

}

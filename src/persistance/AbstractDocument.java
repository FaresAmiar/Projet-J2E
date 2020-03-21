package persistance;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mediatek2020.items.Document;
import mediatek2020.items.EmpruntException;
import mediatek2020.items.ReservationException;
import mediatek2020.items.RetourException;
import mediatek2020.items.Utilisateur;

public class AbstractDocument implements Document {
	
	private int numDoc;
	private Integer numUtilisateur;
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
		} catch (ClassNotFoundException e) {
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
	public String toString() {
		return this.getClass().getSimpleName() + " n°" + numDoc + " Titre :" + titreDoc + " Auteur : " + auteurDoc;
		
	}

	@Override
	public void emprunter(Utilisateur utilisateur) throws EmpruntException {
		// TODO Auto-generated method stub
		synchronized(this) {
			try {
					co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","root");
				
					String requete = "Select numUtilisateur,statutDoc from Document Where numDoc = ?";
					PreparedStatement pstd = co.prepareStatement(requete);
					pstd.setInt(1, this.numDoc);
					ResultSet rs = (ResultSet) pstd.executeQuery();
					if(rs.next()) {
						String statut = rs.getString("statutDoc");
						int numUser = rs.getInt("numUtilisateur");
						
						if(!statut.equals("disponible"))
							throw new EmpruntException();
					}
					
			
					requete = "Update Document Set statutDoc = 'emprunte', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setInt(1,(int) utilisateur.data()[0]);
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
					co.close();
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
				co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","root");
				
				String requete = "Select statutDoc from Document Where numDoc = ?";
				PreparedStatement pstd = co.prepareStatement(requete);
				pstd.setInt(1, this.numDoc);
				ResultSet rs = (ResultSet) pstd.executeQuery();
				if(rs.next()) {
					String statut = rs.getString("statutDoc");
					if(statut.equals("disponible"))
						throw new RetourException();
				}
					
					
					requete = "Update Document Set statutDoc = 'disponible', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setNull(1, java.sql.Types.INTEGER);
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
					co.close();
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
					co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","root");

					String requete = "Select statutDoc from Document Where numDoc = ?";
					PreparedStatement pstd = co.prepareStatement(requete);
					pstd.setInt(1, this.numDoc);
					ResultSet rs = (ResultSet) pstd.executeQuery();
					if(rs.next()) {
						String statut = rs.getString("statutDoc");
						if(!statut.equals("disponible"))
							throw new ReservationException();
					}
					
					requete = "Update Document Set statutDoc = 'reserve', numUtilisateur = ? Where numDoc = ?";
					PreparedStatement ptstmtReserver = co.prepareStatement(requete);
					ptstmtReserver.setInt(1,(int) utilisateur.data()[0]);
					ptstmtReserver.setInt(2, numDoc);
					ptstmtReserver.executeQuery();
					co.close();
				}catch (SQLException e) {
					e.printStackTrace();
			}
		}
		
	}

}

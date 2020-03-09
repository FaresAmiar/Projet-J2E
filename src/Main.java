import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import mediatek2020.Mediatheque;
import mediatek2020.items.Document;
import mediatek2020.items.Utilisateur;
import persistance.Abonné;
import persistance.Bibliothécaire;
import persistance.CD;
import persistance.DVD;
import persistance.Livre;
import persistance.MediathequeData;
import services.ServletConnexion;

public class Main {

	public static void main(String[] args) {
		Connection co = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			co = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","etudiant","ETUDIANT");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		
		// TODO Auto-generated method stub
		//Abonné abo = new Abonné(0, null);
		//Bibliothécaire b = new Bibliothécaire(1, "");
		//CD cd = new CD(0, null, null);
		//DVD de = new DVD(0, null, null);
		//Livre li = new Livre(0, null, null);
		Mediatheque md = Mediatheque.getInstance();
		ServletConnexion s = new ServletConnexion();
		LoadOnInitServlet l = new LoadOnInitServlet();
		String requete = "Select * from Utilisateur Where loginUtilisateur = ? AND passwordUtilisateur = ? ";
		PreparedStatement pstd;
		try {
			pstd = co.prepareStatement(requete);
			pstd.setString(1, "yanisdz");
			pstd.setString(2, "213");
			ResultSet rs = (ResultSet) pstd.executeQuery();
			Utilisateur user = null;
			while(rs.next()) {
				if(rs.getInt("typeUtilisateur")==0) {
					user = new Abonné(rs.getInt("numUtilisateur"),rs.getString("loginUtilisateur"));
				}else {
					user = new Bibliothécaire(rs.getInt("numUtilisateur"),rs.getString("loginUtilisateur"));
				}
			}
			System.out.println("********************" +user.name() + "******************************************************");
			System.out.println(user);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
 
}

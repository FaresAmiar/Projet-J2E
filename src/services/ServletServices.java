package services;

import java.io.IOException;

import mediatek2020.Mediatheque;
import mediatek2020.items.EmpruntException;
import mediatek2020.items.RetourException;
import mediatek2020.items.Utilisateur;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class ServletServices
 */
@WebServlet("/services")
public class ServletServices extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletServices() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
		Mediatheque md = Mediatheque.getInstance();
		String[] nums = null;
		HttpSession session = req.getSession();
		Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
		
		if(req.getParameter("emprunter") != null) {
			nums = req.getParameterValues("emprunts");
			for(String d : nums) {
				int numDoc = Integer.parseInt(d);
				try {
					md.getDocument(numDoc).emprunter(user);
				} catch (EmpruntException e) {
					
				}
			}	
		}
		
		if(req.getParameterValues("retourner") != null) {
			nums = req.getParameterValues("retours");
			for(String d : nums) {
				int numDoc = Integer.parseInt(d);
				try {
					md.getDocument(numDoc).rendre(user);
				} catch (RetourException e) {
					
				}
			}	
		}
		
		if(req.getParameter("ajouter") != null) {
			String typeString = req.getParameter("typeDoc"), titreDoc = req.getParameter("titreDoc"), auteurDoc = req.getParameter("auteurDoc");
			int type = typeString.toUpperCase().equals("LIVRE") ? 0 : (typeString.toUpperCase().equals("DVD") ? 1 : 2); 
			md.nouveauDocument(type, titreDoc,auteurDoc);
		}
		
	}

}

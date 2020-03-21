package services;

import java.io.IOException;

import mediatek2020.Mediatheque;
import mediatek2020.items.Document;
import mediatek2020.items.EmpruntException;
import mediatek2020.items.RetourException;
import mediatek2020.items.Utilisateur;
import persistance.session.MediaSession;

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
		String num = null;
		HttpSession session = req.getSession();
		MediaSession medSession = (MediaSession) session.getAttribute("session");
		Utilisateur user = (Utilisateur) medSession.getUser();
		Document doc = null;
		
		if(req.getParameter("emprunter") != null) {
			num = req.getParameter("emprunter");
				int numDoc = Integer.parseInt(num);
				try {
					doc = md.getDocument(numDoc);
					md.emprunter(doc,user);
					medSession.setInfo(user.name() + ", Le " + doc.toString() + " à bien été emprunté");
				} catch (EmpruntException e) { medSession.setInfo("déja emprunté frero");}
				
		}
		
		if(req.getParameterValues("retourner") != null) {
			num = req.getParameter("retourner");
				int numDoc = Integer.parseInt(num);
				try {
					doc = md.getDocument(numDoc);
					md.rendre(doc,user);
					medSession.setInfo(user.name() + ", Le " + doc.toString() + " à bien été retourné");
				} catch (RetourException e) { medSession.setInfo(e.toString());}
				
		}
		
		if(req.getParameter("réserver") != null) {
			num = req.getParameter("réserver");
				int numDoc = Integer.parseInt(num);
				try {
					md.reserver(md.getDocument(numDoc),user);
				} catch (EmpruntException e) { medSession.setInfo(e.toString()); }
		}
		
		if(req.getParameter("ajouter") != null) {
			String typeString = req.getParameter("typeDoc"), titreDoc = req.getParameter("titreDoc"), auteurDoc = req.getParameter("auteurDoc");
			int type = typeString.toUpperCase().equals("LIVRE") ? 0 : (typeString.toUpperCase().equals("DVD") ? 1 : 2); 
			md.nouveauDocument(type, titreDoc,auteurDoc);
			medSession.setInfo(user.name() + ", Le nouveau document à bien été ajouté");
		}

		rep.sendRedirect("accueil.jsp");
		
		
	}

}

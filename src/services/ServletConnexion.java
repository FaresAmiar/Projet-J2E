package services;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import mediatek2020.Mediatheque;
import mediatek2020.items.Utilisateur;
import persistance.session.MediaSession;

/**
 * Servlet implementation class ServletConnexion
 */
@WebServlet("/connexion")
public class ServletConnexion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletConnexion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse rep) throws ServletException, IOException {
		
		boolean connexion = true; //Connexion réussie
		String login = req.getParameter("login");
        String password = req.getParameter("password");


        Mediatheque md = Mediatheque.getInstance();
        
        Utilisateur user = null;
        
        user = md.getUser(login, password);
       
        if(user == null) //Utilisateur non trouvé
        	connexion = false;
        
        
        if(connexion) {
	        MediaSession mdSession = new MediaSession(user); //Nouvelle session pour 
	        HttpSession session = req.getSession(true);
	        session.setAttribute("session", mdSession);
        }
        
        rep.sendRedirect(connexion ? "accueil.jsp" : "connexion.jsp"); //Renvoie vers la page de connexion si l'user n'est pas trouvé
     	
	}

}

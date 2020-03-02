package services;

import persistance.MediathequeData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ServletConnexion extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        httpServletResponse.setContentType("text/html");
        PrintWriter out = httpServletResponse.getWriter();

        out.println("<html>");
        out.println("<head>");

        String title = "Connexion à la Bibliothèk";

        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        out.println("<h1>" + title + "</h1>");
        out.println("<form method = 'POST' action = './connexion'");
        out.println("<input type = 'text' name = 'login' >Nom d'utilisateur</input>");
        out.println("<input type = 'text' name = 'password' >Mot de Passe</input>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }

    public void doPost(HttpServletRequest req, HttpServletResponse rep) throws Exception {

        String login = req.getParameter("login");
        String password = req.getParameter("password");

        if(login == "" || password == "")
            throw new Exception("Parametre manquant");

        MediathequeData md = new MediathequeData();
        rep.setContentType("text/html");
        PrintWriter out = rep.getWriter();

        out.println("<html>");
        out.println("<head>");

        String title = "Connexion à la Bibliothèk";

        out.println("<title>" + title + "</title>");
        out.println("</head>");
        out.println("<body bgcolor=\"white\">");

        out.println("<h1>" + title + "</h1>");
        out.println("<form method = 'POST' action = './connexion'");
        out.println("<input type = 'text' name = 'login' >Login</input>");
        out.println("<input type = 'text' name = 'password' >Mot de Passe</input>");
        out.println("</form>");
        out.println("</body>");
        out.println("</html>");
    }
}
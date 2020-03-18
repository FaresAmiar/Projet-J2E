<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "mediatek2020.items.Utilisateur" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "mediatek2020.items.Document" %>
    
    <% Utilisateur user = (Utilisateur) session.getAttribute("utilisateur");
       ArrayList<Document> documents = (ArrayList) session.getAttribute("documents");
       String html = "";
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Accueil Médiathèque</title>
</head>
<body>

	<h1>Bienvenue <%= user.name() %> </h1>
	
	<form method = 'POST' action = 'services'>
	<tr>
	<% for (Document d : documents) { %>
		<% if(d.data()[4].equals("disponible")) { %>
		
		<td> <%= d.getClass().getSimpleName() %>  </td>
		<td> <%= d.data()[2] %> </td>
		<td> <%= d.data()[3] %> </td>
		<td> <input type="checkbox" name="emprunts" value="<%= d.data()[0] %>"> </td>
		<% } %>
	<% } %>
	</tr>
	<button type = 'submit' name = "emprunter">Emprunter</button>
	</form>
	
	
	
	<form method = 'POST' action = 'services'>
	<tr>
	<% for (Document d : documents) { %>
		<% if (d.data()[1] == user.data()[0]) { %>
		
		<td> <%= d.getClass().getSimpleName() %>  </td>
		<td> <%= d.data()[2] %> </td>
		<td> <%= d.data()[3] %> </td>
		<td> <input type="checkbox" name="retours" value="<%= d.data()[0] %>"> </td>
		
		<% } %>
	<% } %>
	</tr>
	<button type = 'submit' name = "retourner">Retourner</button>
	</form>
	
	<% if(user.isBibliothecaire()) { %>
			<form method = 'POST' action = 'services'> 
					Type<input type = 'text' name = 'typeDoc' />
					Titre<input type = 'text' name = 'titreDoc'  />
					Auteur<input type = 'text' name = 'auteurDoc'  />
					<button type = 'submit' name = 'ajouter'>Ajouter</button>
			</form>
	<% } %>

</body>
</html>
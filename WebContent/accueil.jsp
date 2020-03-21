<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "mediatek2020.items.Utilisateur" %>
<%@ page import = "java.util.List" %>
<%@ page import = "mediatek2020.items.Document" %>
<%@ page import = "mediatek2020.Mediatheque" %>
<%@ page import = "persistance.session.MediaSession" %>

    <% MediaSession medSession = (MediaSession) session.getAttribute("session");
       Utilisateur user = medSession.getUser();
       List<Document> documents = Mediatheque.getInstance().tousLesDocuments();
       String info = (String) medSession.getInfo();
    %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Accueil Médiathèque</title>
<link rel = 'stylesheet' type = 'text/css' href = './accueil.css'>
</head>
<body>

	<h1>Bienvenue <%= user.name() %> </h1>
	
	
	<h3 style = "color : blue;"><%= info %></h3>
	<% medSession.setInfo(""); %>
	
	<div id = 'formulaires'>

	<div class = 'form'>

	<form method = 'POST' action = 'services'>
			<div id = 'form-doc'>
				<tr class = 'row-doc'>
				<% for (Document d : documents) { %>
					<% if(d.data()[4].equals("disponible")) { %>

					<td> <%= d.getClass().getSimpleName() %>  </td>
					<td> <%= d.data()[2] %> </td>
					<td> <%= d.data()[3] %> </td>
					<td> <button type = 'submit' name = "emprunter" value = "<%= d.data()[0] %>">Emprunter</button> </td>
					<% } %>
				<% } %>
				</tr>
			</div>
	</form>

	</div>
	
	<div class = 'form'>

	<form method = 'POST' action = 'services'>
		<div id = 'form-doc'>
			<tr class = 'row-doc'>
			<% for (Document d : documents) { %>
				<% if (d.data()[1] == user.data()[0] && !d.data()[4].equals("disponible")) { %>

				<td> <%= d.getClass().getSimpleName() %>  </td>
				<td> <%= d.data()[2] %> </td>
				<td> <%= d.data()[3] %> </td>
				<td> <button type = 'submit' name = "retourner" value = "<%= d.data()[0] %>">Retourner</button> </td>

				<% } %>
			<% } %>
			</tr>
		</div>
	</form>

	</div>

	<% if(user.isBibliothecaire()) { %>

	<div class = 'form'>
			<form method = 'POST' action = 'services'>
					Type<input type = 'text' name = 'typeDoc' />
					Titre<input type = 'text' name = 'titreDoc'  />
					Auteur<input type = 'text' name = 'auteurDoc'  />
					<button type = 'submit' name = 'ajouter'>Ajouter</button>
			</form>
	</div>
	<% } %>

	</div>
	
</body>
</html>
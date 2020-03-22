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

<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" integrity="sha384-HSMxcRTRxnN+Bdg0JdbxYKrThecOKuH5zCYotlSAcp1+c8xmyTe9GYg1l9a69psu" crossorigin="anonymous">

</head>
<body>

	<h1>Bienvenue <%= user.name() %> </h1>
	
	
	<h3 style = "color : blue;"><%= info %></h3>
	<% medSession.setInfo(""); %>
	
	<div id = 'formulaires'>

		<div class = 'form'>
			<h5>Documents à Emprunter</h5>
			<form method = 'POST' action = 'services'>
					<div>
						<table class = 'table'>
							<thead>
								<tr>
									<th>Type</th>
									<th>Titre</th>
									<th>Auteur</th>
								</tr>
							<thead>
							<tbody>
								<% for (Document d : documents) { %>
									<% if(d.data()[4].equals("disponible")) { %>
									<tr>
				
										<td> <%= d.getClass().getSimpleName() %>  </td>
										<td> <%= d.data()[2] %> </td>
										<td> <%= d.data()[3] %> </td>
										<td> <button type = 'submit' name = "emprunter" value = "<%= d.data()[0] %>">Emprunter</button> </td>
									</tr>
									<% } %>
								<% } %>
							</tbody>
						</table>
					</div>
			</form>
	
		</div>
		
		<div class = 'form'>
			<h5>Documents à Retourner</h5>
			<form method = 'POST' action = 'services'>
				<div>
					<table class = 'table'>
							<thead>
								<tr>
									<th>Type</th>
									<th>Titre</th>
									<th>Auteur</th>
								</tr>
							<thead>
							<tbody>
						<% for (Document d : documents) { %>
							<% if (d.data()[1] == user.data()[0] && !d.data()[4].equals("disponible")) { %>
								<tr>
									<td> <%= d.getClass().getSimpleName() %>  </td>
									<td> <%= d.data()[2] %> </td>
									<td> <%= d.data()[3] %> </td>
									<td> <button type = 'submit' name = "retourner" value = "<%= d.data()[0] %>">Retourner</button> </td>
								</tr>
							<% } %>
						<% } %>
							</tbody>
					</table>
				</div>
			</form>
	
		</div>
	
		<% if(user.isBibliothecaire()) { %>
	
		<div class = 'form'>
				<h5>Documents à Ajouter</h5>
				<form method = 'POST' action = 'services'>
					<tr>
						<th>Type</th>
						<th>Titre</th>
						<th>Auteur</th>
					</tr>
					<tr>
						<td> Type <input type = 'text' name = 'typeDoc' /> </td>
						<td> Titre <input type = 'text' name = 'titreDoc'  /> </td>
						<td> Auteur <input type = 'text' name = 'auteurDoc'  /> </td>
						<td> <button type = 'submit' name = 'ajouter'> Ajouter </button> </td>
					</tr>
				</form>
		</div>
		<% } %>

	</div>
	
	
	
</body>
</html>
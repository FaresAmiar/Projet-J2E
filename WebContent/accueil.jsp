<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import = "mediatek2020.items.Utilisateur" %>
    
    <% Utilisateur user = (Utilisateur) session.getAttribute("utilisateur"); %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Accueil Médiathèque</title>
</head>
<body>

	<h1>Bienvenue <%= user.name() %> </h1>

</body>
</html>
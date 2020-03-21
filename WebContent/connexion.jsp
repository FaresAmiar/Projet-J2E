<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Connexion à  la Mediatheque</title>
<link rel = 'stylesheet' type = 'text/css' href = './connexion.css'>
</head>
<body>
	
	<h1>Connexion à la médiathèque</h1>
	
	
	
	<form method = 'POST' action = 'connexion'>
		<div class = 'container'>
			Nom d'utilisateur<input type = 'text' name = 'login' />
			Mot de Passe<input type = 'password' name = 'password'  />
			<button type = 'submit' >Confirmer</button>
		</div>
	</form>

</body>
</html>
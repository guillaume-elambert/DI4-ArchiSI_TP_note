<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>EStore</title>
</head>
<body>
	<table border="1">
		<tr>
			<th>Catégorie</th>
			<th>Produit</th>
			<th>Prix (EUR)</th>
			<th>Stock</th>
		</tr>
		<c:forEach var="product" items="${requestScope.PRODUCTS}">
			<tr>
				<td>${product.category}</td>
				<td>${product.label}</td>
				<td>${product.priceInEuro}</td>
				<td>${product.stock}</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>
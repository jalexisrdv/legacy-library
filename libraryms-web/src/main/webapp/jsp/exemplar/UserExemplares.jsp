<%@page import="com.jardvcode.web.dto.ExemplarDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<main>
		<% List<ExemplarDTO> exemplares = (List<ExemplarDTO>) request.getAttribute("exemplares"); %>
		
		<table>
		    <thead>
		        <tr>
		            <th>ISBN</th>
		            <th>EXEMPLAR ID</th>
		            <th>TITLE</th>
		            <th>STATE</th>
		        </tr>
		    </thead>
		    <tbody>
			    <% for(ExemplarDTO entity : exemplares) { %>
					<tr>
			            <td><%= entity.getIsbn() %></td>
			            <td><%= entity.getExemplarId() %></td>
			            <td><%= entity.getTitle() %></td>
			            <td><%= entity.getState() %></td>
		        	</tr>
				<% } %>
		    </tbody>
		</table>
	
	</main>
</body>
</html>
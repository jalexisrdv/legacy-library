<%@page import="com.jardvcode.web.shared.domain.ParameterError"%>
<%@page import="java.util.Map"%>
<%@page import="com.jardvcode.web.dto.BookDTO"%>
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
		<% Long pageNumber = (Long) request.getAttribute("pageNumber"); %>
		<% List<BookDTO> books = (List<BookDTO>) request.getAttribute("books"); %>
		<% Map<String, ParameterError> validationErrors = (Map<String, ParameterError>) request.getAttribute("validationErrors"); %>
		<% String titleParameter = (String) request.getAttribute("titleParameter"); %>
		
		<form method="GET">
			<label id="title" >TITLE:</label>
			<input type="text" id="title" name="title" placeholder="title" />
			<% if(validationErrors.containsKey("title")) { %>
				<span style="color: red;"><%= validationErrors.get("title").getErrorMessage() %></span>
			<% } %>
			<input type="submit" value="SEARCH">
		</form>
		
		<table>
		    <thead>
		        <tr>
		            <th>ISBN</th>
		            <th>TITLE</th>
		            <th>AUTHOR</th>
		            <th>PAGES</th>
		            <th>STOCK</th>
		        </tr>
		    </thead>
		    <tbody>
			    <% for(BookDTO entity : books) { %>
					<tr>
			            <td><%= entity.getIsbn() %></td>
			            <td><%= entity.getTitle() %></td>
			            <td><%= entity.getAuthor() %></td>
			            <td><%= entity.getPages() %></td>
			            <td><%= entity.getStock() %></td>
		        	</tr>
				<% } %>
		    </tbody>
		</table>
		
		<div>
			<ul>
				<% for(int pageIndex = 1; pageIndex <= pageNumber; pageIndex++) { %>
					<li><a href="books?title=<%= titleParameter %>&page=<%= pageIndex %>" ><%= pageIndex %></a></li>
				<% } %>
			</ul>
		</div>
		
		<div>
			<% if(validationErrors.containsKey("page")) { %>
				<p><%= validationErrors.get("page").getErrorMessage() %></p>
			<% } %>
		</div>
	
	</main>
</body>
</html>
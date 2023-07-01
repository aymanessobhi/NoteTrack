<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.List, estm.dsic.models.Note" %>
    <%@ page import="estm.dsic.services.NoteService" %>
    <%@ page import="java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome to the Application</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
  <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.7.0/css/all.css" integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ" crossorigin="anonymous">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="mystyle.css">
</head>

<body style="background-color:#e6e2dd">
<div class="container-fluid">
		<jsp:useBean id="user" class="estm.dsic.models.User" scope="session"/>
		 <%SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		  List<Note> notesa = (List<Note>) session.getAttribute("notes"); %>
		<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
		  <a class="navbar-brand" href="#">My Application</a>
		  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
		    <span class="navbar-toggler-icon"></span>
		  </button>
		  <div class="collapse navbar-collapse" id="navbarNav">
		    <ul class="navbar-nav ml-auto">
		      <li class="nav-item active">
		        <a class="nav-link" href="#">Welcome, <%=user.getName()%> <span class="sr-only">(current)</span></a>
		      </li>
		      <li class="nav-item">
		        <a class="nav-link" href="#">Number of notes : <%= (notesa == null ? 0 : notesa.size()) %></a>
		      </li>
		      <li class="nav-item">
		        <form method="get" action="AuthServlet">
		          <button type="submit" name="action" value="saveNotes" class="btn btn-info">save</button>
		        </form>
		      </li>&nbsp;&nbsp;&nbsp;&nbsp;
		      <li class="nav-item">
		        <form method="post" action="AuthServlet">
		          <input type="hidden" name="id" value="<%=user.getId()%>">
		          <button type="submit" name="action" value="logoutNotes" class="btn btn-info">Logout</button>
		        </form>
		      </li>
		    </ul>
		  </div>
		</nav><br>
		<form method="GET" action="AuthServlet" class="form-inline my-2 my-lg-0">
		  <div class="input-group">
		    <input type="text" name="search" class="form-control" placeholder="Search">
		    <div class="input-group-append">
		      <button name="action" value="searchNote" class="btn btn-info" type="submit">
		        <i class="fa fa-search"></i>
		      </button>
		    </div>
		  </div>
		  <button type="button" class="btn btn-outline-info ml-3" data-toggle="modal" data-target="#createNoteModal">
		    <i class="fa fa-plus"></i> Create Note
		  </button>
		</form>
		<div class="modal fade" id="createNoteModal" tabindex="-1" role="dialog" aria-labelledby="createNoteModalLabel" aria-hidden="true">
		  <div class="modal-dialog" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="createNoteModalLabel">Create Note</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        <!-- Your form goes here -->
		        <form action="AuthServlet" method="get">
		          <input type="hidden" name="id" value="<%=user.getId()%>" >
		          <div class="form-group">
		            <label for="title">Title:</label>
		            <input type="text" class="form-control" id="title" name="title">
		          </div>
		          <div class="form-group">
		            <label for="content">Content:</label>
		            <textarea class="form-control" id="content" name="content" rows="5"></textarea>
		          </div>
		          <button name="action" value="createNote" type="submit" class="btn btn-primary">Create Note</button>
		        </form>
		      </div>
		    </div>
		  </div>
		</div>
		 <%
		 		String searchQuery = request.getParameter("search");
			    NoteService note = new NoteService();
			    List<Note> notes = note.getAllNotes(searchQuery,user.getId());
			%>
		<%
		    int pageSize = 8; // Number of notes to display per page
		    int totalNotes = notes.size(); // Total number of notes
		    int totalPages = (int) Math.ceil((double) totalNotes / pageSize); // Calculate the total number of pages
		    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1; // Get the current page from the request parameter, default to 1
		    int startNote = (currentPage - 1) * pageSize; // Calculate the starting note index for the current page
		    int endNote = Math.min(startNote + pageSize, totalNotes); // Calculate the ending note index for the current page
		%>
		<div class="row" style="margin-top:10px;">
		<% for (int i = startNote; i < endNote; i++) { %>
		   <div class="col-sm-3">
			  <div class="card mb-2 shadow-lg rounded">
			    <div class="card-header" style="background-color: #2c2e31; color: #fff;">
			      <h5 class="card-title"><%= notes.get(i).getTitle() %></h5>
			    </div>
			    <div class="card-body">
			      <p class="card-text text-muted"><%= notes.get(i).getContent() %></p>
			    </div>
			    <div class="card-footer" style="background-color: #fff;">
			      <form method="get" action="AuthServlet">
			        <input type="hidden" name="id" value="<%=notes.get(i).getId()%>">
			        <button type="submit" name="action" value="deleteNote" class="btn btn-link">
			          <i class="fas fa-trash-alt" style="color: #dc3545;"></i>
			        </button>
			        <button type="button" class="btn btn-link" data-toggle="modal" data-target="#editModal-<%=notes.get(i).getId()%>">
			          <i class="fas fa-edit" style="color: #007bff;"></i>
			        </button>
			        <small class="text-muted"><%= sdf.format(notes.get(i).getUpdatedAt()) %></small>
			      </form>
			    </div>
			  </div>
			</div>
		    <div class="modal fade" id="editModal-<%=notes.get(i).getId()%>" tabindex="-1" role="dialog" aria-labelledby="editModalLabel" aria-hidden="true">
			  <div class="modal-dialog" role="document">
			    <div class="modal-content">
			     <div class="card-header bg-info text-white shadow-sm">
					  <h5 class="card-title"><%= notes.get(i).getTitle() %></h5>
					</div>
			      <div class="modal-body">
			        <form method="get" action="AuthServlet">
			            <input type="hidden" name="id" value="<%=notes.get(i).getId()%>">
			          <div class="form-group">
			            <label for="noteTitle">Title:</label>
			            <input type="text" name="title" class="form-control" id="noteTitle" value="<%= notes.get(i).getTitle() %>">
			          </div>
			          <div class="form-group">
			            <label for="noteContent">Content:</label>
			            <textarea name="content" class="form-control" id="noteContent" rows="5"><%= notes.get(i).getContent() %></textarea>
			          </div>
			          <button name="action" value="editNote" type="submit" class="btn btn-info">Save changes</button>
			        </form>
			      </div>
			    
			    </div>
			  </div>
			</div>
		  <% } %>
		</div>
		</div>
		<nav aria-label="Page navigation">
		  <ul class="pagination justify-content-center">
		    <% if (currentPage > 1) { %>
		    <li class="page-item">
		      <a class="page-link" style="background-color: #2c2e31; color: #fff;" href="app.jsp?page=<%= currentPage - 1 %>" aria-label="Previous">
		        <span aria-hidden="true">&laquo;</span>
		        <span class="sr-only">Previous</span>
		      </a>
		    </li>
		    <% } %>
		    <% for (int i = 1; i <= totalPages; i++) { %>
		    <li class="page-item <% if (i == currentPage) { %>active<% } %>">
		      <a class="page-link" style="background-color: #2c2e31; color: #fff;" href="app.jsp?page=<%= i %>"><%= i %></a>
		    </li>
		    <% } %>
		    <% if (currentPage < totalPages) { %>
		    <li class="page-item">
		      <a class="page-link" style="background-color: #2c2e31; color: #fff;" href="app.jsp?page=<%= currentPage + 1 %>" aria-label="Next">
		        <span aria-hidden="true">&raquo;</span>
		        <span class="sr-only">Next</span>
		      </a>
		    </li>
		    <% } %>
		  </ul>
		</nav>
</body>
</html>
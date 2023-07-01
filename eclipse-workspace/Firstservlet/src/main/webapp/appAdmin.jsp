<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.List, estm.dsic.models.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Admin</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body style=" background-color: #fffff ;">
<jsp:useBean id="user" class="estm.dsic.models.User" scope="session"/>
<div class="container-fluid">
 <h2>Welcome Admin <%=user.getName()%></h2>
 <% List<User> users = (List<User>)request.getAttribute("list");%>
 <form method="post" action="AuthServlet">
    <input type="hidden" name="id" value="<%=user.getId()%>">
	<button type="submit" name="action" value="logout" class="btn btn-info">Logout</button>
 </form><br>
 <div class="row">
    <div class="col-md-6">
        <form action="AuthServlet" method="get">
            <div class="input-group mb-3">
                <input type="text" class="form-control" placeholder="Search by name or email" name="q">
                <div class="input-group-append">
                    <button name="action" value="search" class="btn btn-outline-secondary" type="submit">Search</button>
                </div>
            </div>
        </form>
    </div>
    <div class="col-md-6">
        <% 
        String query = request.getParameter("q");
        if (query != null) {
            out.println("<p>Search results for \"" + query + "\": " + users.size() + "</p>");
        }
        %>
    </div>
</div>
    <table class="table">
        <thead class="table-dark">
            <tr>
                <th>Name</th>
                <th>Email</th>
                <th>Password</th>
                <th>is Admin</th>
                <th>Action</th>
            </tr>
        </thead>
 				<% 
			         
			         System.out.println("users: " + users); // debug statement
			         for(User usera: users){
     			 %>
        <tbody>
            <tr>
                <td><%=usera.getName()%></td>
                <td><%=usera.getEmail()%></td>
                <td><%=usera.getPassword()%></td>
                <td><%=usera.getIsAdmin()%></td>
                 <td><div class="btn-group" role="group" aria-label="Basic example">
						 <form action="AuthServlet" method="get">
					        <input type="hidden" name="id" value="<%=usera.getId()%>">
					        <button type="submit" name="action" value="delete" class="btn btn-outline-info">delete</button>
					     </form>
					     <button type="button" class="btn btn-outline-info" data-toggle="modal" data-target="#edit-modal-<%=usera.getId()%>">
						    Edit
						</button>
						<div class="modal fade" id="edit-modal-<%=usera.getId()%>" tabindex="-1" role="dialog" aria-labelledby="edit-modal-label-<%=usera.getId()%>" aria-hidden="true">
						    <div class="modal-dialog" role="document">
						        <div class="modal-content">
						            <div class="modal-header">
						                <h5 class="modal-title" id="edit-modal-label-<%=usera.getId()%>">Edit User</h5>
						                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
						                    <span aria-hidden="true">&times;</span>
						                </button>
						            </div>
						            <div class="modal-body">
						                <form action="AuthServlet" method="get">
						                    <input type="hidden" name="id" value="<%=usera.getId()%>">
						                    <input type="hidden" name="action" value="update">
						                    <div class="form-group">
						                        <label for="name-<%=usera.getId()%>">Name</label>
						                        <input type="text" class="form-control" id="name-<%=usera.getId()%>" name="name" value="<%=usera.getName()%>">
						                    </div>
						                    <div class="form-group">
						                        <label for="email-<%=usera.getId()%>">Email</label>
						                        <input type="email" class="form-control" id="email-<%=usera.getId()%>" name="email" value="<%=usera.getEmail()%>">
						                    </div>
						                    <div class="form-group">
						                        <label for="password-<%=usera.getId()%>">Password</label>
						                        <input type="password" class="form-control" id="password-<%=usera.getId()%>" name="password">
						                    </div>
						                    <div class="form-group form-check">
						                        <input type="checkbox" class="form-check-input" id="isAdmin-<%=usera.getId()%>" name="isAdmin" <% if(usera.getIsAdmin()){ %> checked <% } %>>
						                        <label class="form-check-label" for="isAdmin-<%=usera.getId()%>">Is Admin</label>
						                    </div>
						                    <script>
											  $(document).ready(function() {
											    $('#isAdmin-<%=usera.getId()%>').change(function() {
											      if ($(this).is(':checked')) {
											        // If the checkbox is checked, set the "value" attribute to "true"
											        $(this).attr('value', 'true');
											      } else {
											        // If the checkbox is unchecked, remove the "value" attribute
											        $(this).removeAttr('value');
											      }
											    });
											  });
											</script>
						                    <button type="submit" class="btn btn-info"">Update</button>
						                </form>
						            </div>
						        </div>
						    </div>
						</div>
						</div>
					    </td>    
			            </tr>
			            <% } %>
			        </tbody>
			    </table>
</div>
</body>
</html>
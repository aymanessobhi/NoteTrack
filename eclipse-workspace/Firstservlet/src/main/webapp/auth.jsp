<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html lang="en">

<head>

    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <!-- Bootstrap v5.1.3 CDNs -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>

    <!-- CSS File -->
    <link rel="stylesheet" href="style.css">

</head>

<body>

    <div class="login">

        <h1 class="text-center">WELCOME LOGIN</h1>
        <hr>
        <form method="post" action="AuthServlet" class="needs-validation">
            <div class="form-group">
                <div class="form-group">
			    <label for="name">Name</label>
			    <input type="name" name="name" class="form-control" id="name"  placeholder="Username">
			  </div>
            </div>
            <div class="form-group">
                <label for="password">Password</label>
		<input type="password" name="password" class="form-control" id="password" placeholder="Password">
                <div class="invalid-feedback">
                    Please enter your password
                </div>
            </div>
            <div class="form-group">
                <a href="/Firstservlet/signup.jsp" class="text-decoration">To Signup</a>
            </div>
	    <button name="action" value="login" type="submit" class="btn btn-outline-info">Login</button>
        </form>

    </div>

</body>

</html>
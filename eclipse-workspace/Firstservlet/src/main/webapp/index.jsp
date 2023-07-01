<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html lang="en" class="antialiased">

<head>
 <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css">
 <link rel="stylesheet" href="style.css">
  <script src="https://cdn.jsdelivr.net/npm/jquery@3.6.3/dist/jquery.slim.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"></script>
  <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"></script></head>
<body>
	<section class="">
  <div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center">
      <div class="col-md-9 col-lg-6 col-xl-5">
        <img src="https://mdbcdn.b-cdn.net/img/Photos/new-templates/bootstrap-login-form/draw2.webp"
          class="img-fluid" alt="Sample image">
      </div>
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
    </div>
</section>

</body>

</html>


<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="ie6 ielt8"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="ie7 ielt8"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="ie8"> <![endif]-->
<!--[if (gte IE 9)|!(IE)]><!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8">
    <title>Login</title>
    <style>
     <%@ include file="../css/login_style.css"%>
	</style>
</head>

<body>
<div class="container">
	<section id="content">
		<form method="POST" action="<c:url value="/j_spring_security_check" />">
			<h1>Login Form</h1>	
   			<div>	
            	<input type="text" placeholder="Username" required="" id="username" name="j_username" />
            </div>	
       		<div>
       			<input type="password" placeholder="Password" required="" id="password" name="j_password" />
       		</div>
           <div>
           		<input type="submit" value="Log in" />
           		<a href="buying_user_registration.jsp">Register</a>
				<a href="#">Lost your password?</a><br/><br/><br/>
				<c:if test="${not empty param.error}">
    			<font color="red">
        			Login error. Reason : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
    			</font>
				</c:if>
			</div>
		</form><!-- form -->
		<div class="button">
		</div><!-- button --> 	
    </section><!-- content -->
</div><!-- container -->
</body>
</html>

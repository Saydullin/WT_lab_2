<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
<head>
    <title>Sign-in page</title>
    <style><%@include file="/WEB-INF/view/css/register_style.css"%></style>
</head>
<body>
<form action="signin">
  <div class="container">
    <h1>Sign in</h1>
    <p>Please fill in this form to enter your account.</p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" required pattern=[a-z0-9._%+-]+@[a-z0-9.-]+/.[a-z]{2,4}>

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required$>
    <hr>

    <c:if test="${code!=null}">
            <c:choose>
                <c:when test="${code==1}">
                    <p class="invisible">You do not have an account</p>
                </c:when>
                <c:when test="${code==2}">
                    <p class="invisible">Incorrect password</p>
                </c:when>
            </c:choose>
        </c:if>

    <button type="submit" class="registerbtn">Sign in</button>
  </div>

  <div class="container signin">
    <p>Have no account? <a href="welcome">Register</a>.</p>
  </div>
</form>
</body>
</html>
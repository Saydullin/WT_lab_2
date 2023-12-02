<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html lang="en">
<head>
    <title>Registration page</title>
    <style><%@include file="/WEB-INF/view/css/register_style.css"%></style>
    <meta content="text/html; charset=UTF-8" http-equiv="Content-Type">
</head>
<body>
<form action="registration">
  <div class="container">
    <h1>Register</h1>
    <p>Please fill in this form to create an account.</p>
    <hr>

    <label for="email"><b>Email</b></label>
    <input type="text" placeholder="Enter Email" name="email" required pattern="[^@\s]+@[^@\s]+\.[^@\s]+" oninvalid="this.setCustomValidity('Invalid email')" oninput="this.setCustomValidity('')">

    <label for="psw"><b>Password</b></label>
    <input type="password" placeholder="Enter Password" name="psw" required minlength="8" oninvalid="this.setCustomValidity('Minimal password length is 8')" oninput="this.setCustomValidity('')">

    <label for="psw-repeat"><b>Repeat Password</b></label>
    <input type="password" placeholder="Repeat Password" name="psw-repeat" required minlength="8" oninvalid="this.setCustomValidity('Minimal password length is 8')" oninput="this.setCustomValidity('')">
    <hr>

    <c:if test="${code!=null}">
        <c:choose>
            <c:when test="${code==1}">
                <p class="invisible">Invalid email</p>
            </c:when>
            <c:when test="${code==2}">
                <p class="invisible">Passwords do not match</p>
            </c:when>
            <c:when test="${code==3}">
                <p class="invisible">Too simple password</p>
            </c:when>
            <c:when test="${code==4}">
                 <p class="invisible">You already have an account</p>
            </c:when>
        </c:choose>
    </c:if>
    <button type="submit" class="registerbtn">Register</button>
  </div>

  <div class="container signin">
    <p>Already have an account? <a href="signin">Sign in</a>.</p>
  </div>
</form>
</body>
</html>

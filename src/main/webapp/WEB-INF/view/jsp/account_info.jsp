<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Account info</title>
        <style><%@include file="/WEB-INF/view/css/header_footer.css"%></style>
        <style><%@include file="/WEB-INF/view/css/page_content.css"%></style>
    </head>
    <body>
        <div class="header">
          <a href="list" class="logo">Researcher</a>
          <div class="header-right">
            <a href="list">Research list</a>
                <c:if test="${cookie.userrole.value == 'admin'}">
                    <a href="research">Add research</a>
                </c:if>
                <c:if test="${cookie.userrole.value == 'user'}">
                    <a href="statistics">Statistics</a>
                </c:if>
            <a class="active" href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
            <form action="account">
                <div class="container">
                    <a class="signout-link" href="account?signout=true">Sign out</a>
                    <h1>Registration details</h1>
                    <p>Please fill in this form to change registration data.</p>
                    <hr>

                        <label for="email"><b>Email</b></label>
                        <input type="text" value="<c:out value="${login}" />" name="email" required pattern="[^@\s]+@[^@\s]+\.[^@\s]+" oninvalid="this.setCustomValidity('Invalid email')" oninput="this.setCustomValidity('')">

                        <label for="psw"><b>Password</b></label>
                        <input type="password" value="<c:out value="${password}" />" name="psw" required minlength="8" oninvalid="this.setCustomValidity('Minimal password length is 8')" oninput="this.setCustomValidity('')">

                        <label for="psw-repeat"><b>Repeat Password</b></label>
                        <input type="password" value="<c:out value="${password}" />" name="psw-repeat" required minlength="8" oninvalid="this.setCustomValidity('Minimal password length is 8')" oninput="this.setCustomValidity('')">
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
                                 <p class="invisible">This email already exists</p>
                            </c:when>
                        </c:choose>
                    </c:if>

                    <button type="submit" class="btn">Change data</button>

                </div>
            </form>
        </div>

        <footer>
            <small>
              Copyright © 2023, "Researcher". All Rights Reserved.
            </small>
        </footer>
    </body>
<html>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>New research</title>
        <style><%@include file="/WEB-INF/view/css/header_footer.css"%></style>
        <style><%@include file="/WEB-INF/view/css/page_content.css"%></style>
    </head>
    <body>
        <div class="header">
          <a href="list" class="logo">Researcher</a>
          <div class="header-right">
            <a href="list">Research list</a>
            <a class="active" href="research">Add research</a>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <form action="research">
          <div class="container">
            <h3>New research:</h3>
            <p>Please fill in this form to create new research.</p>
            <hr>

            <label for="name"><b>Research name</b></label>
            <input type="text" placeholder="Enter research name" name="name" required maxlength="45">
            <hr>

            <button type="submit" class="btn">Create research</button>
            <button type="reset" class="btn">Clean form</button>
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
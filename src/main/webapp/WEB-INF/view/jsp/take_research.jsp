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
            <a class="active" href="list">Research list</a>
            <c:if test="${cookie.userrole.value == 'admin'}">
                <a href="research">Add research</a>
            </c:if>
            <c:if test="${cookie.userrole.value == 'user'}">
                <a href="statistics">Statistics</a>
            </c:if>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <h2><c:out value="${research_name}"/></h2>
        <form action="take_research">
          <div class="container">
            <h3>Questions</h3>
            <p>Please fill in this form to participate in the research.</p>
            <hr>
            <c:set var="count" value="1" scope="page"/>
            <input type="hidden" name="research" value="<c:out value="${research_name}"/>">
            <c:forEach var="mapItem" items="${questions}">
                <label for="<c:out value = "${mapItem.key}"/>"><b><c:out value = "${mapItem.key}"/>  (Topic: <c:out value = "${mapItem.value}"/>)</b></label>
                <input type="text" placeholder="Enter your answer" name="<c:out value = "${mapItem.key}"/>" required maxlength="200" oninvalid="this.setCustomValidity('This is required question')" oninput="this.setCustomValidity('')">
            </c:forEach>
            <hr>

            <button type="submit" class="btn">Send answers</button>
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
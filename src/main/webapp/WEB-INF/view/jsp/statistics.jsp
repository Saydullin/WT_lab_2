<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Question info</title>
        <style><%@include file="/WEB-INF/view/css/header_footer.css"%></style>
        <style><%@include file="/WEB-INF/view/css/page_content.css"%></style>
    </head>
    <body>
        <div class="header">
          <a href="list" class="logo">Researcher</a>
          <div class="header-right">
            <a href="list">Research list</a>
            <c:if test="${cookie.userrole.value == 'admin'}">
                <a class="active" href="research">Add research</a>
            </c:if>
            <c:if test="${cookie.userrole.value == 'user'}">
                <a class="active" href="statistics">Statistics</a>
            </c:if>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <h3>Your statistics:</h3>
        <h4>You took part in <c:out value="${research_count}"/> researches</h4>
        <h4>You have answered <c:out value="${question_count}"/> questions in the following topics:</h4>
        <ul>
            <c:forEach var="mapItem" items="${topics_count}">
                <li>
                    <p class="answer_item"><c:out value="${mapItem.key}"/>: <c:out value="${mapItem.value}"/></p>
                </li>
            </c:forEach>
        </ul>
        </div>

        <footer>
            <small>
              Copyright © 2023, "Researcher". All Rights Reserved.
            </small>
        </footer>
    </body>
<html>
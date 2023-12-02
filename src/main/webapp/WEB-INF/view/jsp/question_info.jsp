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
            <a class="active" href="list">Research list</a>
            <a href="research">Add research</a>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <a class="return-link" href = "research_info?item=<c:out value = "${research}"/>">Return</a>
        <h3>
            Answers to question: <c:out value="${question}"/>
        </h3>
        <ul>
            <c:forEach var="mapItem" items="${answers}">
                <li>
                    <p class="answer_item"><c:out value="${mapItem.key}"/></p>
                    <p>User: <c:out value="${mapItem.value}"/></p>
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
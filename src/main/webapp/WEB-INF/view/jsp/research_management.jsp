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
            <a href="research">Add research</a>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <h2><c:out value="${research_name}"/></h2>
        <h4>Status: <c:out value="${status}"/></h4>
        <form action="research_info">
            <div>
                <input type="hidden" name="changed_research" value="<c:out value="${research_name}"/>">
                <c:set var = "research_status" value = "${status}"/>
                <input type="radio" id="status1" name="research_status" value="paused" <c:if test="${research_status == 'paused'}"><c:out value="${'checked'}"/></c:if> />
                <label for="status1">Paused</label>
                <input type="radio" id="status2" name="research_status" value="running" <c:if test="${research_status == 'running'}"><c:out value="${'checked'}"/></c:if> />
                <label for="status2">Running</label>
                <button class="smallbtn" type="submit">Change status</button>
            </div>
        </form>
        <h3>Questions:</h3>
            <ol>
                <c:forEach var="mapItem" items="${questions}">
                    <li>
                        <a class="question_link" href="question_info?item=<c:out value="${mapItem.key}"/>&research=<c:out value="${research_name}"/>"><c:out value="${mapItem.key}"/></a>
                        <p>Topic: <c:out value="${mapItem.value}"/></p>
                    </li>
                </c:forEach>
            </ol>
        <form action="research_info">
          <div class="container">
            <h3>New question</h3>
            <p>Please fill in this form to add new question to the research.</p>
            <hr>
            <input type="hidden" name="research" value="<c:out value="${research_name}"/>">
            <label for="topic"><b>Choose topic</b></label>
                <select name="topic">
                    <c:forEach var="topicItem" items="${topics}">
                        <option value="<c:out value="${topicItem}"/>"> <c:out value="${topicItem}"/> </option>
                    </c:forEach>
                </select>
            <label for="question"><b>Question</b></label>
            <input type="text" placeholder="Enter your question" name="question" required maxlength="200">
            <hr>

            <button type="submit" class="btn">Add question</button>
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
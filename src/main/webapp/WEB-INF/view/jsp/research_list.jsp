<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html>
    <head>
        <title>Research list</title>
        <style><%@include file="/WEB-INF/view/css/header_footer.css"%></style>
        <style><%@include file="/WEB-INF/view/css/page_content.css"%></style>
    </head>
    <body>
        <div class="header">
          <a href="list" class="logo">Researcher</a>
          <div class="header-right">
            <a class="active" href="list">Research list</a>
            <c:choose>
                <c:when test="${cookie.userrole.value == 'admin'}">
                    <a href="research">Add research</a>
                </c:when>
                <c:when test="${cookie.userrole.value == 'user'}">
                    <a href="statistics">Statistics</a>
                </c:when>
                <c:otherwise>
                     <c:if test="${user_role == 'admin'}">
                         <a href="research">Add research</a>
                     </c:if>
                     <c:if test="${user_role == 'user'}">
                         <a href="statistics">Statistics</a>
                     </c:if>
                </c:otherwise>
            </c:choose>
            <a href="account">Account info</a>
          </div>
        </div>

        <div class="page_content">
        <h3>
            Research list:
        </h3>
        <ul>
            <c:forEach var="listItem" items="${researches}">
                <c:choose>
                    <c:when test="${cookie.userrole.value == 'admin'}">
                        <li>
                            <a class="research_link" href="research_info?item=<c:out value="${listItem}"/>"><c:out value="${listItem}"/></a>
                        </li>
                    </c:when>
                    <c:when test="${cookie.userrole.value == 'user'}">
                        <li>
                            <a class="research_link" href="take_research?item=<c:out value="${listItem}"/>"><c:out value="${listItem}"/></a>
                        </li>
                    </c:when>
                    <c:otherwise>
                         <c:if test="${user_role == 'admin'}">
                             <li>
                                 <a class="research_link" href="research_info?item=<c:out value="${listItem}"/>"><c:out value="${listItem}"/></a>
                             </li>
                         </c:if>
                         <c:if test="${user_role == 'user'}">
                             <li>
                                 <a class="research_link" href="take_research?item=<c:out value="${listItem}"/>"><c:out value="${listItem}"/></a>
                             </li>
                         </c:if>
                    </c:otherwise>
                </c:choose>
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
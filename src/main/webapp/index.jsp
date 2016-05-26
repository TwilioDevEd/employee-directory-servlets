<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Employee Directory App</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet" integrity="sha256-MfvZlkHCEqatNoGiOXveE8FIwMzZg4W85qfrfIFBfYc= sha512-dTfge/zgoMYpP7QbHy4gWMEGsbsdZeCXz7irItjcC3sPUFtf0kuFbDz/ixG7ArTxmDjLXDmezHubeNikyKGVyQ==" crossorigin="anonymous">
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.4.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha256-k2/8zcNbxVIh5mnQ52A0r3a6jAgMGxFJFE2707UxGCk= sha512-ZV9KawG2Legkwp3nAlxLIVFudTauWuBpC10uEafMHYL0Sarrz5A7G79kXh5+5+woxQ5HM559XX2UZjMJ36Wplg==" crossorigin="anonymous">
    <link rel="stylesheet" href="css/site.css">
</head>
<body>
<div class="container">
    <div class="page-header">
        <h1>Employee Directory App</h1>
    </div>
    <c:if test="${firstEmployee.present}">
        <div>
        <label>First Employee:</label>
        <span><c:out value="${firstEmployee.get().fullName}"/></span>
        </div>
    </c:if>
    <div>
        <form method="post">
            <input type="text" name="query" placeholder="query some superheroe" value="<c:out value="${query}"/>"/>
            <input type="hidden" name="from" value="+15555555555" />
            <input type="submit" name="submit" value="Send"/>
            <c:if test="${employeeMatch.present}">
                <c:choose>
                    <c:when test="${employeeMatch.get().isSingleEmployeeFound()}">
                        <input type="hidden" name="selectedEmployee" value="<c:out value="${employeeMatch.get().foundEmployee.id}"/>" />
                    </c:when>
                    <c:otherwise>
                        <input type="hidden" name="selectedEmployee" value="-1" />
                    </c:otherwise>
                </c:choose>
                <p class="response">
                    <c:out value="${employeeMatch.get().message}"/>
                </p>
            </c:if>
        </form>
    </div>
</div>
<footer class="footer">
    <div class="container">
        <p class="text-muted">
            Made with <i class="fa fa-heart"></i> by your pals
            <a href="http://www.twilio.com">@twilio</a>
        </p>
    </div>
</footer>
</body>
</html>
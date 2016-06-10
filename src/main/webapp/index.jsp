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
    <link rel="stylesheet" href="css/main.css">
    <script src="//cdn.front.to/libs/jquery/2.2.4/jquery.min.js"></script>
    <script src="scripts/main.js"></script>
</head>
<body>
<div class="container">
    <section class="page-header">
        <h1>Employee Directory</h1>
    </section>
    <section class="body-content">
        <form method="post"  class="form-inline">
            <input type="text" id="txt-search" required name="<c:out
            value="${query_param}"/>" placeholder="Search your superheroe"
            value="<c:out value="${query}"/>" class="form-control"/>
            <input type="submit" name="submit" value="Send" class="btn btn-primary"/>
            <c:if test="${employeeMatch != null}">
                <p class="response">
                    <c:out value="${employeeMatch.message}"/>
                </p>
            </c:if>
        </form>
    </section>
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

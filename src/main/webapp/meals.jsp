<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<jsp:useBean id="mealServlet" class="ru.javawebinar.topjava.web.MealServlet"/>
<html>
<style>
    .green {
        color: green
    }

    .red {
        color: red
    }

    table, th, td {
        border: 1px solid black;
    }
</style>
<head>
    <title>Meals</title>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>
<hr>
<h2>Meals</h2>
<table>
    <tr>
        <th>Date</th>
        <th>Description</th>
        <th>Calories</th>
        <th></th>
        <th></th>
    </tr>
    <c:forEach items="${requestScope.mealsList}" var="mealTo">
        <tr class= ${mealTo.excess ? 'red':'green'}>
            <td>${requestScope.localDateTimeFormatter.format(mealTo.dateTime)}</td>
            <td>${mealTo.description}</td>
            <td>${mealTo.calories}</td>
            <td><a href="meals?action=edit&id=<c:out value="${mealTo.id}"/>">Update</a></td>
            <td><a href="meals?action=delete&id=<c:out value="${mealTo.id}"/>">Delete</a>
            </td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=insert">Add Meal</a></p>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <td>
        <form action="meals" method="get">
            <input type="hidden" name="action" value="create">
            <c:if test="${param.filter=='filter'}">
                <input type="hidden" name="filter" value="${param.filter}">
                <input type="hidden" name="dateStart" value="${param.dateStart}">
                <input type="hidden" name="dateEnd" value="${param.dateEnd}">
                <input type="hidden" name="timeStart" value="${param.timeStart}">
                <input type="hidden" name="timeEnd" value="${param.timeEnd}">
            </c:if>
            <p><input type="submit" value="Add Meal"></p>
        </form>
    </td>
    <br><br>
    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Date</th>
            <th>Description</th>
            <th>Calories</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <form action="meals" method="get">
                        <input type="hidden" name="action" value="update">
                        <input type="hidden" name="id" value="${meal.id}">
                        <c:if test="${param.filter=='filter'}">
                            <input type="hidden" name="filter" value="${param.filter}">
                            <input type="hidden" name="dateStart" value="${param.dateStart}">
                            <input type="hidden" name="dateEnd" value="${param.dateEnd}">
                            <input type="hidden" name="timeStart" value="${param.timeStart}">
                            <input type="hidden" name="timeEnd" value="${param.timeEnd}">
                        </c:if>
                        <p><input type="submit" value="Update"></p>
                    </form>
                </td>
                <td>
                    <form action="meals" method="get">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="id" value="${meal.id}">
                        <c:if test="${param.filter=='filter'}">
                            <input type="hidden" name="filter" value="${param.filter}">
                            <input type="hidden" name="dateStart" value="${param.dateStart}">
                            <input type="hidden" name="dateEnd" value="${param.dateEnd}">
                            <input type="hidden" name="timeStart" value="${param.timeStart}">
                            <input type="hidden" name="timeEnd" value="${param.timeEnd}">
                        </c:if>
                        <p><input type="submit" value="Delete"></p>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
    <form action="${pageContext.request.contextPath}/meals">
        <input type="hidden" name="filter" value="filter"><br>
        Start date: <input type="date" value="${param.dateStart}" name="dateStart">
        End date: <input type="date" value="${param.dateEnd}" name="dateEnd"><br><br>
        Start time: <input type="time" value="${param.timeStart}" name="timeStart">
        End time: <input type="time" value="${param.timeEnd}" name="timeEnd">
        <p><input type="submit" value="Filter"></p>
    </form>
</section>
</body>
</html>


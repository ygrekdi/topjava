<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<head>
    <title>Edit meal</title>
</head>
<body>
<h2>Edit meals</h2>
<h3>
    <a href="index.html">Home</a>
</h3>
<form method="POST" action='meals' name="frmAddMeal">
    <input type="hidden" readonly="readonly" name="id"
           value="<c:out value="${meal.id}" />"/>
    Description : <input style=position:absolute;left:5%;
                         type="text" name="description"
                         value="<c:out value="${meal.description}" />" size="50"/> <br/> <br/>
    Calories : <input style=position:absolute;left:5%;
                      type="text" name="calories"
                      value="<c:out value="${meal.calories}" />" size="27"/> <br/> <br/>
    DateTime : <input style=position:absolute;left:5%;
                      type="datetime-local" name="date"
                      value="<c:out value="${meal.dateTime}" />"/> <br/> <br/>
    <input type="submit" value="Save"/>
    <a href="meals">
        <button type="button">Cancel</button>
    </a>
</form>
</body>
</html>

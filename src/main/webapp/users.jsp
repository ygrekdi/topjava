<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="ru">
<head>
    <title>Users</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Users</h2>
<form action="${pageContext.request.contextPath}/users">
    <p><select name="userId">
        <option value="1">1</option>
        <option value="2">2</option>
    </select></p>
    <p><input type="submit" value="Change user"></p>
</form>
</body>
</html>
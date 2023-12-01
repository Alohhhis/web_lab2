<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c3" %>
<c3:set var="error" value="${applicationScope.error}"/>

<html lang="ru">
<head>
  <title>lab2</title>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">

</head>
<body>
<div class="header">
  <h1>
    Ошибка
  </h1>
</div>
<h2>Error message: ${error}</h2>
<button class="return-button" onclick="redirectToIndex()">Вернуться на главную</button>
<script src="js/redirect.js"></script>

</body>
</html>

<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.web_lab2.model.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа №2</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
<%
    //Todo брать нужное значение общего объекта
    // List<Data> table = (List<Data>) session.getAttribute("table");
%>
<div id="header" class="main_field">
        <span id="header_text">
            Докшина Алёна P3221, вариант  №2178
        </span>
</div>
<div id="wrapper" class="main_field">
    <h2 class="center">Таблица параметров</h2>
    <table id="result_table" rules="all">
        <thead>
        <th>Значение X</th>
        <th>Значение Y</th>
        <th>Значение R</th>
        <th>Время выполнения</th>
        <th>Текущее время выполнения</th>
        <th>Результат</th>
        </thead>
        <tbody>
        <c:forEach var="result" items="${resultList}">
            <tr>
                <td>${result.x}</td>
                <td>${result.y}</td>
                <td>${result.r}</td>
                <td>${result.calculatedAt}</td>
                <td>${result.res ? 'попал' : 'мимо'}</td>
                <td>${result.calculationTime}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <form id="forms" class="center" action="index.jsp">
        <button>Вернуться на главную страницу</button>
    </form>
</div>

</body>
</html>
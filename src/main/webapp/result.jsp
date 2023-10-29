<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Лабораторная работа №2</title>
    <link rel="stylesheet" type="text/css" href="resources/style.css">
</head>
<body>
<%
    List<RequestData> table = (List<RequestData>) session.getAttribute("table");
%>
<div id="header" class="main_field">
        <span id="header_text">
            Аллаяров Игорь, P32121, Вариант №1101
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
        <% for(RequestData requestData: table) { %>
        <tr class='columns'>
            <td><%=requestData.getArea().getX()%></td>
            <td><%=requestData.getArea().getY()%></td>
            <td><%=requestData.getArea().getR()%></td>
            <td><%=requestData.getExecutionTime()%></td>
            <td><%=requestData.getCurrentTime()%></td>
            <td><%=requestData.isResult()%></td>
        </tr>
        <% } %>
        </tbody>
    </table>
    <form id="forms" class="center" action="index.jsp">
        <button>Вернуться на главную страницу</button>
    </form>
</div>
<script src="js/result/dot.js"></script>
</body>
</html>
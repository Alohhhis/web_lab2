<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Результаты</title>
</head>
<body>
<div id="header" class="blurred-container round-container margin">
    <h1>Результат: ${result.res ? 'Убил' : 'Мимо'}</h1>
</div>
<div id="result-table-container" class="blurred-container margin">
    <table id="result_table" >
        <thead>
        <th>Значение X</th>
        <th>Значение Y</th>
        <th>Значение R</th>
        <th>Время выполнения</th>
        <th>Текущее время</th>
        <th>Результат</th>
        </thead>
        <tbody>
        <c:forEach var="result" items="${resultList}">
            <tr>
                <td>${result.x}</td>
                <td>${result.y}</td>
                <td>${result.r}</td>
                <td>${result.calculatedAt}</td>
                <td>${result.calculationTime}</td>
                <td>${result.res ? 'убил' : 'мимо'}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
<div class="blurred-container round-container fit-content-container margin">
    <form id="forms" class="center" action="index.jsp">
        <button>Вернуться на главную страницу</button>
    </form>
</div>
</body>
</html>

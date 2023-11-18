<%@ page import="java.util.List" %>
<%@ page import="com.example.web_lab2.model.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <script src="js/redirect.js"></script>

    <title>Result Page</title>
    <style>
        body {
            background-color: #282c34;
            color: #abb2bf;
            font-family: 'Arial', sans-serif;
            margin: 20px;
        }

        table {
            width: 80%;
            margin-top: 20px;
            border: 2px solid #61dafb;
            border-collapse: collapse;
            background-color: #282c34;
            color: #abb2bf;
        }

        th, td {
            border: 2px solid #61dafb;
            padding: 12px;
            text-align: center;
            color: #abb2bf;
        }

        th {
            background-color: #1d1f21;
            color: #61dafb;
        }

        h2, p {
            color: #61dafb;
        }

        .return-button {
            background-color: #1d1f21;
            color: #61dafb;
            border: 2px solid #61dafb;
            padding: 8px;
            border-radius: 5px;
            margin-top: 20px;
            cursor: pointer;
        }

        .return-button:hover {
            background-color: #61dafb;
            color: #282c34;
        }
    </style>
</head>
<body>

<h2>Result Page</h2>

<%
    List<Data> resultList = (List<Data>) request.getServletContext().getAttribute("resultList");
%>

<% if (resultList != null && !resultList.isEmpty()) { %>
<table>
    <thead>
    <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Result</th>
        <th>Calculation Time (ns)</th>
        <th>Calculated At</th>
    </tr>
    </thead>
    <tbody>
    <% for (Data data : resultList) { %>
    <tr>
        <td><%= data.getX() %></td>
        <td><%= data.getY() %></td>
        <td><%= data.getR() %></td>
        <td><%= data.getRes() %></td>
        <td><%= data.getCalculationTime() %></td>
        <td><%= data.getCalculatedAt() %></td>
    </tr>
    <% } %>
    </tbody>
</table>
<button class="return-button" onclick="redirectToIndex()">На главную</button>
<% } else { %>
<p>Тут чёта не то (значения не те, надо заново попробовать)</p>
<button class="return-button" onclick="redirectToIndex()">На главную</button>
<% } %>

</body>
</html>

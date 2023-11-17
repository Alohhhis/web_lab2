<%@ page import="java.util.List" %>
<%@ page import="com.example.web_lab2.model.Data" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Result Page</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        th, td {
            border: 1px solid black;
            padding: 8px;
            text-align: left;
        }

        th {
            background-color: #f2f2f2;
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
<% } else { %>
<p>No results available.</p>
<% } %>

</body>
</html>

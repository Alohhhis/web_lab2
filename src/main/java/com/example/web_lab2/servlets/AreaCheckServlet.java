package com.example.web_lab2.servlets;

import com.example.web_lab2.model.Data;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;


import static com.example.web_lab2.view.Result.renderView;


@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {
    public List<Data> getResultList() {
        return resultList;
    }

    private List<Data> resultList;
    //private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    //private final List<Double> validRValues = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);

    @Override
    public void init() throws ServletException {
        resultList = new LinkedList<>();
        getServletContext().setAttribute("resultList", resultList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            final long startExec = System.nanoTime();
            final String ctx = this.getServletContext().getContextPath();
            String strX = request.getParameter("x");
            String strY = request.getParameter("y");
            String strR = request.getParameter("r");


            // Парсинг значений
            int x = Integer.parseInt(strX);
            double y = Double.parseDouble(strY);
            double r = Double.parseDouble(strR);
            try {
                x = Integer.parseInt(strX);
                y = Double.parseDouble(strY);
                r = Double.parseDouble(strR);
            } catch (NumberFormatException | NullPointerException e) {
                response.sendError(400);
                return;
            }
            final boolean result = checkout(x, y, r);
            final long endExec = System.nanoTime();
            final long executionTime = endExec - startExec;
            final LocalDateTime executedAt = LocalDateTime.now();

            final Data data = new Data();
            data.setX(x);
            data.setY(y);
            data.setR(r);
            data.setRes(result);
            data.setCalculationTime(executionTime);
            data.setCalculatedAt(executedAt);

            synchronized (resultList) {
                resultList.add(0, data);
            }
            renderView(response, ctx, data);
        } catch (Exception e) {
            getServletContext().setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private boolean checkout(int x, double y, double r) {
        // Проверка внутри круга
        boolean insideCircle = (x >= 0 && y >= 0 && Math.sqrt(x*x + y*y) <= r);

        // Проверка внутри прямоугольника
        boolean insideRectangle = (x <= 0 && x >= -r && y <= 0 && y >= -r/2);

        // Проверка внутри треугольника
        boolean insideTriangle = (x >= 0 && y <= 0 && x + 2 * y >= 0 && x*x + y*y <= (r/2)*(r/2));

        return insideCircle || insideRectangle || insideTriangle;
    }


}

/*
//import static com.example.lab_2.view.Result.renderView;

@WebServlet(name = "AreaCheckServlet", value="/AreaCheckServlet")
public class AreaCheckServlet extends HttpServlet {
    public List<Data> getResultList() {
        return resultList;
    }

    private List<Data> resultList;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        final String ctx = this.getServletContext().getContextPath();
        final long start = System.nanoTime();
        final String x = req.getParameter("X");
        final String y = req.getParameter("Y");
        final String r = req.getParameter("R");
        final int dx;
        final double dy;
        final double dr;
        try {
            dx = Integer.parseInt(x);
            dy = Double.parseDouble(y);
            dr = Double.parseDouble(r);
        } catch (NumberFormatException | NullPointerException e) {
            resp.sendError(400);
            return;
        }

        final boolean res = checkArea(dx, dy, dr);
        final long end = System.nanoTime();
        final long execTime = end - start;
        final LocalDateTime execAt = LocalDateTime.now();

        final Data result = new Data();
        result.setX(dx);
        result.setY(dy);
        result.setR(dr);
        result.setRes(res);
        result.setCalculationTime(execTime);
        result.setCalculatedAt(execAt);

        synchronized (resultList) {
            resultList.add(result);
        }

        renderView(resp, ctx, result);
    }
    public boolean checkSquare(double x, double y, double r) {
        return (x <= 0 && y >= 0 && Math.abs(x) <= r && y <= r);
    }

    public boolean checkCircle(double x, double y, double r) {
        return (x >= 0 && y >= 0 && (Math.pow(x, 2) + Math.pow(y, 2) <= Math.pow(r, 2)));
    }

    public boolean checkTriangle(double x, double y, double r) {
        return (x >= 0 && y <= 0 && Math.abs(x) + Math.abs(y) <= r);
    }

    public boolean checkArea(double x, double y, double r) {
        return checkCircle(x, y, r) || checkTriangle(x, y, r) || checkSquare(x, y, r);
    }

    @Override
    public void init() throws ServletException {
        resultList = new LinkedList<>();
        getServletContext().setAttribute("resultList", resultList);
    }

}

 */
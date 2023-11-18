package com.example.web_lab2.servlets;

import com.example.web_lab2.model.Data;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;

import java.util.List;
import java.util.logging.Logger;


@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {
    public List<Data> getResultList() {
        return resultList;
    }
    private List<Data> resultList;
    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    public void init() throws ServletException {
        resultList = new ArrayList<>();
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

            LOGGER.info("Values set in AreaCheck: " + strR + strX + strY);
            System.out.println("x: " + strX);
            System.out.println("y: " + strY);
            System.out.println("r: " + strR);

            // Парсинг значений
            double x = Double.parseDouble(strX);
            double y = Double.parseDouble(strY);
            double r = Double.parseDouble(strR);
            try {
                x = Double.parseDouble(strX);
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
            LOGGER.info("Values set: " + data);
            RequestDispatcher dispatcher = request.getRequestDispatcher("/result.jsp");
            dispatcher.forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            getServletContext().setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private boolean checkout(double x, double y, double r) {
        // Проверка внутри круга
        boolean insideCircle = (x >= 0 && y >= 0 && Math.sqrt(x*x + y*y) <= r);

        // Проверка внутри прямоугольника
        boolean insideRectangle = (x <= 0 && x >= -r && y <= 0 && y >= -r/2);

        // Проверка внутри треугольника
        boolean insideTriangle = (x >= 0 && y <= 0 && x + 2 * y >= 0 && x*x + y*y <= (r/2)*(r/2));

        return insideCircle || insideRectangle || insideTriangle;
    }
}


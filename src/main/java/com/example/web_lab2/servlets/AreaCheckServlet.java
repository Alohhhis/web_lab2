package com.example.web_lab2.servlets;

import com.example.web_lab2.model.Data;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-HH.mm.ss");

    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    public void init() throws ServletException {
        List<Data> dataList = new ArrayList<>();
        getServletContext().setAttribute("dataList", dataList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            LOGGER.info("Received coordinates: x=" + x + ", y=" + y + ", r=" + r);

            boolean result = checkout(x, y, r);

            long startExec = System.nanoTime();
            long endExec = System.nanoTime();
            long executionTime = endExec - startExec;

            LocalDateTime executedAt = LocalDateTime.now();
            String formattedExecutedAt = executedAt.format(formatter);

            double roundedX = Math.round(x * 100.0) / 100.0;
            double roundedY = Math.round(y * 100.0) / 100.0;

            Data data = new Data();
            data.setX(roundedX);
            data.setY(roundedY);
            data.setR(r);
            data.setRes(result);
            data.setCalculationTime(executionTime);
            data.setCalculatedAt(formattedExecutedAt);

            ServletContext application = getServletContext();

            List<Data> dataList = (List<Data>) application.getAttribute("dataList");

            if (dataList == null) {
                dataList = new ArrayList<>();
            }

            dataList.add(0, data);

            application.setAttribute("dataList", dataList);

            LOGGER.info("Values set: " + data);

            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
            getServletContext().setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private boolean checkout(double x, double y, double r) {
        // Проверка внутри круга
        boolean insideCircle = (x >= 0 && y >= 0 && Math.sqrt(x * x + y * y) <= r);

        // Проверка внутри прямоугольника
        boolean insideRectangle = (x <= 0 && x >= -r && y <= 0 && y >= -r / 2);

        // Проверка внутри треугольника
        boolean insideTriangle = (x >= 0 && x <= r && y >= -r / 2 && y <= 0 && y >= (0.5 * x - r / 2));

        return insideCircle || insideRectangle || insideTriangle;
    }
}

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
import java.util.List;

@WebServlet(name = "AreaCheckServlet", value = "/check")
public class AreaCheckServlet extends HttpServlet {
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    private final List<Double> validRValues = Arrays.asList(1.0, 1.5, 2.0, 2.5, 3.0);

    @Override
    public void init() throws ServletException {
        // Инициализация списка результатов в контексте приложения
        List<Data> resultList = new ArrayList<>();
        getServletContext().setAttribute("resultList", resultList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String strX = request.getParameter("x");
            String strY = request.getParameter("y");
            String strR = request.getParameter("r");

            // Проверка на длину строк
            if (!(strX.length() <= 10 && strY.length() <= 10 && strR.length() <= 10)) {
                return;
            }

            // Парсинг значений
            int x = Integer.parseInt(strX);
            double y = Double.parseDouble(strY);
            double r = Double.parseDouble(strR);

            // Валидация
            if (isValidX(x) && isValidY(y) && isValidR(r)) {
                long startTime = System.nanoTime();

                String result = checkout(x, y, r) ? "убил" : "мимо";
                LocalTime time = LocalTime.now();
                String currentTime = time.format(formatter);
                String scriptTime = String.format("%.2f", (double) (System.nanoTime() - startTime) * 0.0001);

                // Создание объекта Data и добавление в список результатов
                Data data = new Data();
                data.setX(x);
                data.setY(y);
                data.setR(r);
                data.setRes(checkout(x, y, r));
                data.setCalculationTime(System.nanoTime() - startTime);
                data.setCalculatedAt(LocalDateTime.from(LocalTime.now()));

                List<Data> resultList = (List<Data>) getServletContext().getAttribute("resultList");
                resultList.add(data);

                // Обновление атрибута в контексте приложения
                getServletContext().setAttribute("resultList", resultList);
            } else {
                // В случае невалидных данных, перенаправляем на страницу с ошибкой
                getServletContext().setAttribute("error", "Невалидные данные");
                request.getRequestDispatcher("/error.jsp").forward(request, response);
            }
        } catch (Exception e) {
            getServletContext().setAttribute("error", e.getMessage());
            request.getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }

    private boolean isValidX(int x) {
        return x >= -5 && x <= 3;
    }

    private boolean isValidY(double y) {
        return y >= -3 && y <= 5;
    }

    private boolean isValidR(double r) {
        return validRValues.contains(r);
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

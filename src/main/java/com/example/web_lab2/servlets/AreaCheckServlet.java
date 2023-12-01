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
        // Инициализация списка данных при старте приложения
        List<Data> dataList = new ArrayList<>();
        getServletContext().setAttribute("dataList", dataList);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Получаем параметры запроса
            double x = Double.parseDouble(request.getParameter("x"));
            double y = Double.parseDouble(request.getParameter("y"));
            double r = Double.parseDouble(request.getParameter("r"));

            LOGGER.info("Received coordinates: x=" + x + ", y=" + y + ", r=" + r);

            // Выполняем проверку
            boolean result = checkout(x, y, r);

            // Засекаем время выполнения
            long startExec = System.nanoTime();
            long endExec = System.nanoTime();
            long executionTime = endExec - startExec;

            // Получаем текущую дату и время
            LocalDateTime executedAt = LocalDateTime.now();
            String formattedExecutedAt = executedAt.format(formatter);

            double roundedX = Math.round(x * 100.0) / 100.0;
            double roundedY = Math.round(y * 100.0) / 100.0;

            // Создаем объект Data и устанавливаем значения
            Data data = new Data();
            data.setX(roundedX);
            data.setY(roundedY);
            data.setR(r);
            data.setRes(result);
            data.setCalculationTime(executionTime);
            data.setCalculatedAt(formattedExecutedAt);

            // Получаем контекст приложения
            ServletContext application = getServletContext();

            // Получаем текущий список данных из контекста приложения
            List<Data> dataList = (List<Data>) application.getAttribute("dataList");

            // Если список еще не создан, создаем новый
            if (dataList == null) {
                dataList = new ArrayList<>();
            }

            // Добавляем новый объект Data в список
            dataList.add(0, data);

            // Сохраняем обновленный список в контексте приложения
            application.setAttribute("dataList", dataList);

            LOGGER.info("Values set: " + data);

            // Переходим к отображению результатов
            request.getRequestDispatcher("/result.jsp").forward(request, response);
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());

            // Сохраняем сообщение об ошибке в контексте приложения
            getServletContext().setAttribute("error", e.getMessage());

            // Переходим к отображению страницы ошибки
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

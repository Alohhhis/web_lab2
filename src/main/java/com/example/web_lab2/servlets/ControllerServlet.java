package com.example.web_lab2.servlets;

import com.example.web_lab2.model.Data;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.*;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Создаем экземпляр Data и устанавливаем значения из запроса
            Data data = new Data();
            data.setX(Integer.parseInt(request.getParameter("x")));
            data.setY(Double.parseDouble(request.getParameter("y")));
            data.setR(Double.parseDouble(request.getParameter("r")));

            // Добавляем data в атрибуты запроса
            request.setAttribute("data", data);

            // Перенаправляем запрос для обработки
            getServletContext().getRequestDispatcher("/check").forward(request, response);
        } catch (Exception e) {
            // В случае ошибки перенаправляем на страницу ошибки
            getServletContext().setAttribute("error", e.getMessage());
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        }
    }
}

package com.example.web_lab2.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.example.web_lab2.model.Data;

import java.io.IOException;
import java.util.logging.Logger;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    double[] rArray = {1, 1.5, 2, 2.5, 3};
    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Entering ControllerServlet doGet method.");

        // Валидация точки
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        System.out.println("Received parameters in ControllerServlet: x=" + x + ", y=" + y + ", r=" + r);
        LOGGER.info("Values in Controller set: " + x + y + r);
        String forwardPath;
        try {
            if (x != null && y != null && r != null && validateCoordinates(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(r))) {
                // Если параметры валидны, перенаправляем на AreaCheckServlet
                RequestDispatcher dispatcher = request.getRequestDispatcher("/check");
                dispatcher.forward(request, response);
            } else {
                // Если параметры не валидны, перенаправляем на index.jsp
                forwardPath = this.getServletContext().getContextPath() + "/index.jsp";
                response.sendRedirect(forwardPath);
            }
        } catch (Exception e) {
            LOGGER.severe("Error: " + e.getMessage());
        }
    }

    private boolean validateCoordinates(double x, double y, double r) {
        boolean validX = -5 <= x && x <= 5;
        boolean validY = -4 <= y && y <= 4;
        boolean validR = false;

        for (int i = 0; i < rArray.length; i++) {
            if (rArray[i] == r) {
                validR = true;
                break;
            }
        }
        return validX && validY && validR;
    }
}

package com.example.web_lab2.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.example.web_lab2.model.Data;
import org.apache.commons.lang3.ArrayUtils;

import java.io.IOException;
import java.util.logging.Logger;
/*
@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    double[] rArray = {1, 1.5, 2, 2.5, 3};
    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");

        LOGGER.info("Values in Controller set: x=" + x + ", y=" + y + ", r=" + r);

        //TODO Убрать лишние логи
        String forwardPath;
        try {
            if (x != null && y != null && r != null && validateCoordinates(Double.parseDouble(x), Double.parseDouble(y), Double.parseDouble(r))) {
                RequestDispatcher dispatcher = request.getRequestDispatcher("/check");
                dispatcher.forward(request, response);
            } else {
                forwardPath = this.getServletContext().getContextPath() + "/index.jsp";
                response.sendRedirect(forwardPath);
            }
        } catch (Exception e) {
            getServletContext().setAttribute("error", e.getMessage());
            request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
            LOGGER.severe("Error: " + e.getMessage());
        }
    }
    //TODO Ловить ошибки нормально

    private boolean validateCoordinates(double x, double y, double r) {
        boolean validX = -5 <= x && x <= 3;
        boolean validY = -3 <= y && y <= 5;
        boolean validR = false;

        for (int i = 0; i < rArray.length; i++) {
            if (rArray[i] == r) {
                validR = true;
                break;
            }
        }
        return validX && validY && validR;
    }
    //TODO Валидация на чиселки буковки шнягу
}*/
@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    private static final double[] VALID_R_VALUES = {1, 1.5, 2, 2.5, 3};
    private static final double MIN_X = -5;
    private static final double MAX_X = 3;
    private static final double MIN_Y = -3;
    private static final double MAX_Y = 5;

    private static final Logger LOGGER = Logger.getLogger(AreaCheckServlet.class.getName());

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String xParam = request.getParameter("x");
        String yParam = request.getParameter("y");
        String rParam = request.getParameter("r");

        LOGGER.info("Values in Controller set: x=" + xParam + ", y=" + yParam + ", r=" + rParam);

        try {
            double x = parseAndValidateDouble(xParam, MIN_X, MAX_X);
            double y = parseAndValidateDouble(yParam, MIN_Y, MAX_Y);
            double r = parseAndValidateR(rParam);

            RequestDispatcher dispatcher = request.getRequestDispatcher("/check");
            dispatcher.forward(request, response);
        } catch (ValidationException | NumberFormatException e) {
            handleValidationFailure(request, response, e.getMessage());
        }
    }

    private double parseAndValidateDouble(String value, double minValue, double maxValue) throws ValidationException {
        if (value == null) {
            throw new ValidationException("Value cannot be null.");
        }

        try {
            double parsedValue = Double.parseDouble(value);
            if (parsedValue < minValue || parsedValue > maxValue) {
                throw new ValidationException("Value must be in the range [" + minValue + ", " + maxValue + "].");
            }
            return parsedValue;
        } catch (NumberFormatException e) {
            throw new ValidationException("Invalid numeric format.");
        }
    }

    private double parseAndValidateR(String value) throws ValidationException {
        double parsedR = parseAndValidateDouble(value, 1, 3);
        if (!ArrayUtils.contains(VALID_R_VALUES, parsedR)) {
            throw new ValidationException("Invalid value for parameter 'r'.");
        }
        return parsedR;
    }

    private void handleValidationFailure(HttpServletRequest request, HttpServletResponse response, String errorMessage)
            throws ServletException, IOException {
        getServletContext().setAttribute("error", errorMessage);
        request.getServletContext().getRequestDispatcher("/error.jsp").forward(request, response);
        LOGGER.severe("Validation Error: " + errorMessage);
    }

    private static class ValidationException extends Exception {
        public ValidationException(String message) {
            super(message);
        }
    }
}

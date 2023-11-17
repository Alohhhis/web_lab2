package com.example.web_lab2.servlets;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

import com.example.web_lab2.model.Data;

import java.io.IOException;

@WebServlet(name = "ControllerServlet", value = "/ControllerServlet")
public class ControllerServlet extends HttpServlet {
    double[] rArray = {1, 1.5, 2, 2.5, 3};
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forwardPath = getServletContext().getContextPath();
        // Валидация точки
        String x = request.getParameter("x");
        String y = request.getParameter("y");
        String r = request.getParameter("r");
        if (x != null && y != null && r != null
                && validateCoordinates(Integer.parseInt(x), Double.parseDouble(y),Double.parseDouble(r)))
        {
            forwardPath = this.getServletContext().getContextPath() + "/check?x=" + request.getParameter("x")
                    + "&y=" + request.getParameter("y") + "&r=" + request.getParameter("r");
        }
        response.sendRedirect(forwardPath);
        }
    private boolean validateCoordinates(int x, double y, double r){
        boolean validX = -5 <= x && x <= 5;
        boolean validY = -4 <= y && y <= 4;
        boolean validR = false;
        for (int i = 0; i < rArray.length; i++){
            if (rArray[i] == r){
                validR = true;
                break;
            }
        }
        return validX && validY && validR;
    }
}

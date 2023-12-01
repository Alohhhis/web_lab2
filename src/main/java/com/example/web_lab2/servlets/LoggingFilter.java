package com.example.web_lab2.servlets;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Logger;

/**
 * Фильтр логгирует информацию о времени начала запроса, конца запроса
 * и его времени выполнения в целом
 */
@WebFilter("/ControllerServlet")
public class LoggingFilter implements Filter {
    private static final Logger LOGGER = Logger.getLogger(LoggingFilter.class.getName());
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm:ss:SSS");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("init filter");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        if (request.getAttribute("loggingFilterApplied") == null) {
            request.setAttribute("loggingFilterApplied", true);

            LocalDateTime startTime = LocalDateTime.now();
            String formattedStartTime = startTime.format(TIME_FORMATTER);

            LOGGER.info("Request received at: " + formattedStartTime);

            chain.doFilter(request, response);

            LocalDateTime endTime = LocalDateTime.now();
            String formattedEndTime = endTime.format(TIME_FORMATTER);

            Duration duration = Duration.between(startTime, endTime);
            long millis = duration.toMillis();

            LOGGER.info("Request processed in: " + millis + " milliseconds.\n End Time: " + formattedEndTime);
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {
        System.out.println("destroying filter");
    }
}

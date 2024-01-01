package com.langlang;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class HelloServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = "hello -- my framework";
        req.setAttribute("name", name);
        req.getRequestDispatcher("WEB-INF/jsp/hello.jsp").forward(req, resp);
    }
}

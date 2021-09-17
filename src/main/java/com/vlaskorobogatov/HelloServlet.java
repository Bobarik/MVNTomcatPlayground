package com.vlaskorobogatov;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/hello-servlet/*"})
public class HelloServlet extends HttpServlet {

    @Override
    public void init() throws ServletException {
        super.init();
        log("Method init");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String result = formatParams(req);
        resp.getWriter().print("\nMethod doGet");
        resp.getWriter().print("\nMy URI is:\n" + uri +
                "\nMy parameters are:\n" + result + "\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String uri = req.getRequestURI();
        String result = formatParams(req);
        resp.getWriter().print("\nMethod doPost");
        resp.getWriter().print("\nMy URI is:\n" + uri +
                "\nMy parameters are:\n" + result + "\n");
    }

    private String formatParams(HttpServletRequest req) {
        return req.getParameterMap()
                .entrySet()
                .stream()
                .map(entry -> {
                    String param = String.join(" and ", entry.getValue());
                    return entry.getKey() + " => " + param;
                })
                .collect(Collectors.joining("\n"));
    }

    @Override
    public void destroy() {
        log("Method destroy");
    }
}

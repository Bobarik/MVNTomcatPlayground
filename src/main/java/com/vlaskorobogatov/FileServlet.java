package com.vlaskorobogatov;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.*;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(urlPatterns = {"/file-servlet"})
public class FileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        for (Part part : req.getParts()) {
            if(part.getName().equals("author-name")) {
                InputStream inputStream = part.getInputStream();
                InputStreamReader isr = new InputStreamReader(inputStream);
                String authorName = new BufferedReader(isr).lines().collect(Collectors.joining("\n"));
                log(authorName);
            } else {
                part.write(UUID.randomUUID() + part.getSubmittedFileName());
            }
        }
        resp.sendRedirect("hello-servlet");
    }
}

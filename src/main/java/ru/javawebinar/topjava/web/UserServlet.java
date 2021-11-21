package ru.javawebinar.topjava.web;

import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = request.getParameter("userId");
        if (userId != null) {
            log.debug("set userId and redirect to meals");
            HttpSession session = request.getSession(false);
            if(session != null){
                session.invalidate();
            }
            SecurityUtil.setAuthUserId(Integer.parseInt(userId));
            response.sendRedirect("meals");
        } else {
            log.debug("forward to users");
            request.getRequestDispatcher("/users.jsp").forward(request, response);
        }
    }
}

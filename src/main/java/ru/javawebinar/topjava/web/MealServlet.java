package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealCRUD;
import ru.javawebinar.topjava.model.MealCRUDInMemoryImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_FORMATTER_OUTPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final int CALORIES_PER_DAY = 2000;
    private static final MealCRUD MEAL_CRUD_IN_MEMORY = new MealCRUDInMemoryImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward;
        String action = req.getParameter("action");
        req.setAttribute("localDateTimeFormatter", DATE_FORMATTER_OUTPUT);
        if (action == null) {
            forward = "/meals.jsp";
            req.setAttribute("mealsList", MealsUtil.unfiltered(MEAL_CRUD_IN_MEMORY.getAll(), CALORIES_PER_DAY));
            req.getRequestDispatcher(forward).forward(req, resp);
        } else if (action.equalsIgnoreCase("delete")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            MEAL_CRUD_IN_MEMORY.delete(id);
            forward = req.getContextPath() + "/meals";
            req.setAttribute("mealsList", MealsUtil.unfiltered(MEAL_CRUD_IN_MEMORY.getAll(), CALORIES_PER_DAY));
            resp.sendRedirect(forward);
        } else if (action.equalsIgnoreCase("edit")) {
            Integer id = Integer.parseInt(req.getParameter("id"));
            forward = "/edit_meal.jsp";
            req.setAttribute("meal", MEAL_CRUD_IN_MEMORY.getById(id));
            req.getRequestDispatcher(forward).forward(req, resp);
        } else {
            forward = "/edit_meal.jsp";
            req.getRequestDispatcher(forward).forward(req, resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(req.getParameter("date"), DATE_FORMATTER_INPUT));
        meal.setDescription(req.getParameter("description"));
        meal.setCalories(Integer.parseInt(req.getParameter("calories")));
        String id = req.getParameter("id");
        if (id == null || id.isEmpty()) {
            Integer newId = MealCRUDInMemoryImpl.nextId();
            meal.setId(newId);
            MEAL_CRUD_IN_MEMORY.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            MEAL_CRUD_IN_MEMORY.update(meal);
        }
        req.setAttribute("localDateTimeFormatter", DATE_FORMATTER_OUTPUT);
        req.setAttribute("mealsList", MealsUtil.unfiltered(MEAL_CRUD_IN_MEMORY.getAll(), CALORIES_PER_DAY));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

}
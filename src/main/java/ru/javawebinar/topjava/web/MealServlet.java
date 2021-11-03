package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealCRUD;
import ru.javawebinar.topjava.dao.MealCRUDInMemoryImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final DateTimeFormatter DATE_FORMATTER_OUTPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER_INPUT = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
    private static final int CALORIES_PER_DAY = 2000;
    private static final MealCRUD MEAL_CRUD_IN_MEMORY = new MealCRUDInMemoryImpl();

    static {
        List<Meal> testMeals = Arrays.asList(
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(MEAL_CRUD_IN_MEMORY.generateId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        testMeals.forEach(MEAL_CRUD_IN_MEMORY::add);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        switch (String.valueOf(action).toLowerCase()) {
            case "delete":
                MEAL_CRUD_IN_MEMORY.delete(Integer.parseInt(req.getParameter("id")));
                resp.sendRedirect("meals");
                break;
            case "edit":
                req.setAttribute("meal", MEAL_CRUD_IN_MEMORY.getById(Integer.parseInt(req.getParameter("id"))));
                req.getRequestDispatcher("/edit_meal.jsp").forward(req, resp);
                break;
            case "insert":
                req.getRequestDispatcher("/edit_meal.jsp").forward(req, resp);
                break;
            default:
                req.setAttribute("localDateTimeFormatter", DATE_FORMATTER_OUTPUT);
                req.setAttribute("mealsList", MealsUtil.filteredByStreams(MEAL_CRUD_IN_MEMORY.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                req.getRequestDispatcher("/meals.jsp").forward(req, resp);
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
            Integer newId = MEAL_CRUD_IN_MEMORY.generateId();
            meal.setId(newId);
            MEAL_CRUD_IN_MEMORY.add(meal);
        } else {
            meal.setId(Integer.parseInt(id));
            MEAL_CRUD_IN_MEMORY.update(meal);
        }
        req.setAttribute("localDateTimeFormatter", DATE_FORMATTER_OUTPUT);
        req.setAttribute("mealsList", MealsUtil.filteredByStreams(MEAL_CRUD_IN_MEMORY.getAll(), LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

}
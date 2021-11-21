package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Collection<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAllFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return MealsUtil.getFilteredTos(service.getAllFiltered(authUserId(), startDate, endDate), MealsUtil.DEFAULT_CALORIES_PER_DAY, startTime, endTime);
    }

    public Meal get(int mealId) {
        return service.get(mealId, authUserId());
    }

    public Meal create(Meal meal) {
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal) {
        service.update(meal, authUserId());
    }
}
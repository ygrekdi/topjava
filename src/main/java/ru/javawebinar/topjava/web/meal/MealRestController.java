package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;

import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {

    @Autowired
    private MealService service;

    public Collection<Meal> getAll() {
        return service.getAll(authUserId());
    }

    public Meal get(int mealId) {
        return service.get(mealId, authUserId());
    }

    public Meal create(Meal meal) {
        return service.create(meal, authUserId());
    }

    public void delete(int mealId) {
        service.delete(mealId, authUserId());
    }

    public void update(Meal meal, int mealId) {
        assureIdConsistent(meal, mealId);
        service.update(meal, authUserId());
    }
}
package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealCRUD {
    void add(Meal meal);

    void delete(Integer id);

    void update(Meal meal);

    Meal getById(Integer mealId);

    List<Meal> getAll();
}

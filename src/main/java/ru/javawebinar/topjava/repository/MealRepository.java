package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.util.Collection;

public interface MealRepository {
    // null if updated meal do not belong to userId
    Meal save(Meal meal, int authUserId);

    // false if meal do not belong to userId
    boolean delete(int id, int authUserId);

    // null if meal do not belong to userId
    Meal get(int id, int authUserId);

    // ORDERED dateTime desc
    Collection<Meal> getAll(int authUserId);
}

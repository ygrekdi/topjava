package ru.javawebinar.topjava.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MealCRUDInMemoryImpl implements MealCRUD {

    private static Integer identifier = 0;
    private static Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    public static synchronized Integer nextId() {
        return identifier++;
    }

    @Override
    public void add(Meal meal) {
        mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delete(Integer id) {
        mealMap.remove(id);
    }

    @Override
    public void update(Meal meal) {
        mealMap.replace(meal.getId(), meal);
    }

    @Override
    public Meal getById(Integer mealId) {
        return mealMap.get(mealId);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(mealMap.values());
    }
}

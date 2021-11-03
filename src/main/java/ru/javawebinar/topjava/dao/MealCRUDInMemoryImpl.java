package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealCRUDInMemoryImpl implements MealCRUD {

    private final AtomicInteger identifier = new AtomicInteger();
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();

    @Override
    public Integer generateId() {
        return identifier.getAndIncrement();
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

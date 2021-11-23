package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        Map<Integer, Meal> mealMap = repository.computeIfAbsent(authUserId, v -> new ConcurrentHashMap<>());
        if (meal.isNew() || mealMap.get(meal.getId()) == null) {
            meal.setId(counter.incrementAndGet());
        }
        mealMap.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public boolean delete(int id, int authUserId) {
        Map<Integer, Meal> mealMap = repository.get(authUserId);
        return mealMap != null && mealMap.get(id) != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int authUserId) {
        Map<Integer, Meal> mealMap = repository.get(authUserId);
        return mealMap == null ? null : mealMap.get(id);
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        return filterByPredicate(authUserId, meal -> true);
    }

    @Override
    public Collection<Meal> getAllFiltered(int authUserId, LocalDate startDate, LocalDate endDate) {
        return filterByPredicate(authUserId, meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate));
    }

    private Collection<Meal> filterByPredicate(int authUserId, Predicate<Meal> filter) {
        return (Optional.ofNullable(repository.get(authUserId)).orElse(Collections.emptyMap()).values().stream()
                .filter(filter)
                .sorted((m1, m2) -> -m1.getDateTime().compareTo(m2.getDateTime())).distinct()
                .collect(Collectors.toList()));
    }
}
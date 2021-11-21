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
        if (meal.isNew()) {
            meal.setUserId(authUserId);
            meal.setId(counter.incrementAndGet());
            putInRepo(repository, meal, authUserId);
            return meal;
        }
        Meal inRepoMeal = get(meal.getId(), authUserId);
        if (inRepoMeal != null && inRepoMeal.getUserId() != authUserId) {
            return null;
        }
        // handle case: update, but not present in storage
        meal.setUserId(authUserId);
        putInRepo(repository, meal, authUserId);
        return meal;
    }

    @Override
    public boolean delete(int id, int authUserId) {
        Map<Integer, Meal> mealMap = repository.get(authUserId);
        if (mealMap == null) {
            return false;
        }
        Meal meal = mealMap.get(id);
        if (meal == null || meal.getUserId() != authUserId) {
            return false;
        }
        return repository.get(authUserId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int authUserId) {
        Map<Integer, Meal> mealMap = repository.get(authUserId);
        if (mealMap == null) {
            return null;
        }
        Meal meal = mealMap.get(id);
        if (meal == null || meal.getUserId() != authUserId) {
            return null;
        }
        return meal;
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

    private void putInRepo(Map<Integer, Map<Integer, Meal>> repository, Meal meal, Integer authUserId) {
        if (repository.get(authUserId) == null) {
            repository.put(authUserId, new ConcurrentHashMap<Integer, Meal>() {{
                put(meal.getId(), meal);
            }});
        } else {
            repository.get(authUserId).put(meal.getId(), meal);
        }
    }
}
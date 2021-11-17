package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(m -> save(m, SecurityUtil.authUserId()));
    }

    @Override
    public Meal save(Meal meal, int authUserId) {
        if (meal.isNew()) {
            meal.setUserId(authUserId);
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        if (meal.getUserId() == null || meal.getUserId() != authUserId) {
            return null;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int authUserId) {
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != authUserId) {
            return false;
        }
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int authUserId) {
        Meal meal = repository.get(id);
        if (meal == null || meal.getUserId() != authUserId) {
            return null;
        }
        return meal;
    }

    @Override
    public Collection<Meal> getAll(int authUserId) {
        return getAllFiltered(authUserId, null, null);
    }

    @Override
    public Collection<Meal> getAllFiltered(int authUserId, LocalDate startDate, LocalDate endDate) {
        return repository.values().stream()
                .filter(meal -> meal.getUserId().equals(authUserId))
                .filter(meal -> DateTimeUtil.isBetweenDate(meal.getDate(), startDate, endDate))
                .sorted((m1, m2) -> -m1.getDateTime().compareTo(m2.getDateTime())).distinct()
                .collect(Collectors.toList());
    }
}
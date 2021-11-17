package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDate;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.*;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, int authUserId) {
        checkNew(meal);
        return repository.save(meal, authUserId);
    }

    public void delete(int mealId, int authUserId) {
        checkNotFoundWithId(repository.delete(mealId, authUserId), mealId);
    }

    public Meal get(int mealId, int authUserId) {
        return checkNotFoundWithId(repository.get(mealId, authUserId), mealId);
    }

    public Collection<Meal> getAll(int authUserId) {
        return repository.getAll(authUserId);
    }

    public Collection<Meal> getAllFiltered(int authUserId, LocalDate startDate, LocalDate endDate) {
        return repository.getAllFiltered(authUserId, startDate, endDate);
    }

    public void update(Meal meal, int authUserid) {
        checkNotFoundWithId(repository.save(meal, authUserid), meal.getId());
    }
}
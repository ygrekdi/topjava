package ru.javawebinar.topjava.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.AbstractMealController;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {

    @GetMapping("")
    public String get(Model model){
        model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping("/create")
    public String create(Model model){
        final Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/delete/{mealId}")
    String delete(@PathVariable(value="mealId") Integer id){
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/update/{mealId}")
    String update(@PathVariable(value="mealId") Integer id, Model model){
        Meal meal = super.get(id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping("/update/{mealId}")
    String update(@PathVariable(value="mealId") Integer id,
                  @RequestParam(value="calories") Integer calories,
                  @RequestParam(value="description") String description,
                  @RequestParam(value="dateTime") String dateTime){
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        super.update(meal, id);
        return "redirect:/meals";
    }

    @PostMapping("/create")
    String create(@RequestParam(value="calories") Integer calories,
                  @RequestParam(value="description") String description,
                  @RequestParam(value="dateTime") String dateTime){
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        super.create(meal);
        return "redirect:/meals";
    }


    @GetMapping("/filter")
    String filter(@RequestParam(value="startTime") String startTime,
                  @RequestParam(value="endTime") String endTime,
                  @RequestParam(value="startDate") String startDate,
                  @RequestParam(value="endDate") String endDate,
                  Model model){
        List<MealTo> filteredMeals = super.getBetween(parseLocalDate(startDate), parseLocalTime(startTime), parseLocalDate(endDate), parseLocalTime(endTime));
        model.addAttribute("meals", filteredMeals);
        return "meals";
    }
}
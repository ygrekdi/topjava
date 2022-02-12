package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

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

    @GetMapping("/delete/{mealId}")
    String delete(@PathVariable(value="mealId") Integer id){
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping(value = { "/createorupdate", "/createorupdate/{id}" })
    String createorupdate(@PathVariable(required = false) Integer id, Model model){
        Meal meal;
        if (id != null) {
            meal = super.get(id);
        } else {
             meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        }
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping(value = { "/createorupdate", "/createorupdate/{id}" })
    String update(@PathVariable(required = false) Integer id,
                  @RequestParam(value="calories") Integer calories,
                  @RequestParam(value="description") String description,
                  @RequestParam(value="dateTime") String dateTime){
        Meal meal = new Meal(LocalDateTime.parse(dateTime), description, calories);
        if (id != null) {
            super.update(meal, id);
        } else {
            super.create(meal);
        }
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
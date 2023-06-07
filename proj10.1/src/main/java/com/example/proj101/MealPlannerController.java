package com.example.proj101;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MealPlannerController {
    private static final String SPOONACULAR_API_KEY = "ba2ef5c08e934119a8d76192b3aee40a";
    private static final String SPOONACULAR_API_URL = "https://api.spoonacular.com/mealplanner/";

    @GetMapping("/mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(
            @RequestParam(value = "numCalories", required = false) String numCalories,
            @RequestParam(value = "diet", required = false) String diet,
            @RequestParam(value = "exclusions", required = false) String exclusions) {

        if (numCalories == null) {
            numCalories = "2000";
        }
        if (diet == null) {
            diet = "none";
        }
        if (exclusions == null) {
            exclusions = "";
        }

        String url = SPOONACULAR_API_URL + "generate?apiKey=" + SPOONACULAR_API_KEY +
                "&timeFrame=week&targetCalories=" + numCalories +
                "&diet=" + diet + "&exclude=" + exclusions;

        RestTemplate restTemplate = new RestTemplate();
        WeekResponse weekResponse = restTemplate.getForObject(url, WeekResponse.class);

        return new ResponseEntity<>(weekResponse, HttpStatus.OK);
    }

    @GetMapping("/mealplanner/day")
    public ResponseEntity<DayResponse> getDayMeals(
            @RequestParam(value = "numCalories", required = false) String numCalories) {

        if (numCalories == null) {
            numCalories = "2000";
        }

        String url = SPOONACULAR_API_URL + "generate?apiKey=" + SPOONACULAR_API_KEY +
                "&timeFrame=day&targetCalories=" + numCalories;

        RestTemplate restTemplate = new RestTemplate();
        DayResponse dayResponse = restTemplate.getForObject(url, DayResponse.class);

        return new ResponseEntity<>(dayResponse, HttpStatus.OK);
    }
}

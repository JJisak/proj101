package com.example.proj101;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class MealPlannerController {
    private static final String SPOONACULAR_API_KEY = "ba2ef5c08e934119a8d76192b3aee40a";
    private static final String SPOONACULAR_API_URL = "https://api.spoonacular.com/mealplanner/";

    @GetMapping("/mealplanner/week")
    public ResponseEntity<WeekResponse> getWeekMeals(
            @RequestParam(value = "numCalories", required = false, defaultValue = "2000") String numCalories,
            @RequestParam(value = "diet", required = false) String diet,
            @RequestParam(value = "exclusions", required = false) String exclusions) {

        String url = UriComponentsBuilder.fromHttpUrl(SPOONACULAR_API_URL + "generate")
                .queryParam("apiKey", SPOONACULAR_API_KEY)
                .queryParam("timeFrame", "week")
                .queryParam("targetCalories", numCalories)
                .queryParam("diet", diet)
                .queryParam("exclude", exclusions)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        WeekResponse weekResponse;

        try {
            ResponseEntity<WeekResponse> response = restTemplate.getForEntity(url, WeekResponse.class);
            if (response.getStatusCode() == HttpStatus.OK) {
                weekResponse = response.getBody();
                return new ResponseEntity<>(weekResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping("/mealplanner/day")
    public ResponseEntity<DayResponse> getDayMeals(
            @RequestParam(value = "numCalories", required = false, defaultValue = "2000") String numCalories) {

        String url = UriComponentsBuilder.fromHttpUrl(SPOONACULAR_API_URL + "generate")
                .queryParam("apiKey", SPOONACULAR_API_KEY)
                .queryParam("timeFrame", "day")
                .queryParam("targetCalories", numCalories)
                .toUriString();

        RestTemplate restTemplate = new RestTemplate();
        DayResponse dayResponse;

        try {
            dayResponse = restTemplate.getForObject(url, DayResponse.class);
            if (dayResponse != null) {
                return new ResponseEntity<>(dayResponse, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

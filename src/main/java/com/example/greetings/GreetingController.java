package com.example.greetings;

import com.example.greetings.model.Greeting;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.beans.factory.annotation.Autowired;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Integer.parseInt;

@RestController
@CrossOrigin(origins  =  "http://localhost:3000")
public class GreetingController {

    private ArrayList<Greeting> greetings = new ArrayList<>();

    // dependency injection
        // avoids us needing to make a new instance of the repository
    @Autowired
    GreetingRepository repository;

    @GetMapping("/greeting/{id}")
    public ResponseEntity<String> getGreetingById(@PathVariable int id){
        // what made up my response
            //status code
            //body -our actual greeting
            //headers - additional info re the request and response
        //ResponseEntity
            //we can configure our entire response using this
            //.status() configure the status code we receive
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(id).toString());
        }catch(Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id: " + id +" Doesn't exist");
        }
    }

    @GetMapping("/greetings")
    public ResponseEntity<List<Greeting>> getAllGreetings() {
//        repository.findAll().stream().count();
        return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
    }

    @GetMapping("/random")
    public ResponseEntity<Greeting> getRandomGreeting() {
        Random r = new Random();
//        Greeting randomGreeting = greetings.get(r.nextInt(greetings.size()));
//        Greeting randomGreeting = repository.findById(r.nextInt((int) repository.findAll().stream().count()));
//        Greeting randomGreeting = repository.findById( r.nextInt ((int) repository.count()));
//        return randomGreeting;

        // refactor to get random greeting from database, not greetings array
            // .count to find number of entries in repository
            int index = 1 + r.nextInt((int) (repository.count()));
            return ResponseEntity.status(HttpStatus.OK).body(repository.findById(index));

            // .findAll (already written ^^) to get all of the existing greetings
//        List<Greeting> allGreetings = (List<Greeting>) getAllGreetings();
//        Greeting randomGreeting = allGreetings.get(r.nextInt(allGreetings.size()));
//        return ResponseEntity.status(HttpStatus.OK).body(randomGreeting);
    }

    @PostMapping("/greetings")
    public ResponseEntity<String> createGreeting(@RequestBody Greeting greeting) {
        // set the greetings id based on the greetings list
        // set the created by
//        greeting.setId(greetings.size() + 1);
//        greeting.setCreatedBy("Ollie");
//        greeting.setDateCreated(new Timestamp(System.currentTimeMillis()));
        repository.save(greeting);
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(greeting.toString() + " added");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(e.toString());
        }
    }

    // UPDATE route
    @PutMapping("/greetings/{id}")
    public String updateFullGreeting(@PathVariable int id, @RequestBody Greeting newGreeting){
        Greeting updatedGreeting = greetings.get(id);
        updatedGreeting.setGreeting(newGreeting.getGreeting());
        updatedGreeting.setCreatedBy(newGreeting.getCreatedBy());
        updatedGreeting.setOriginCountry(newGreeting.getGreeting());
        return "Greeting with id: " + id + "changed to" + newGreeting;
    }

    // DELETE route
    @Transactional
    @DeleteMapping("/greetings/{id}")
    public String deleteGreeting(@PathVariable int id) {
//        greetings.remove(greetings.get(id));
//        repository.deleteById(String.valueOf(id));
        repository.delete(repository.findById(id));
        return "Greeting with id: " + id + " deleted.";
    }
}

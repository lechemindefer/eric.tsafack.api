package eric.tsafack.api.controller;

import eric.tsafack.api.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RequestMapping("/users")
public interface UserController {
    @GetMapping("/welcome")
    String welcome();
    @PostMapping("/create")
    ResponseEntity<String> createUser(@RequestBody Map<String,String> requestMap);
    @GetMapping("/get/{id}")
    ResponseEntity<User> getUserById(@PathVariable Integer id);
    @GetMapping("/all")
    ResponseEntity<List<User>> getAll();
    @PutMapping("/update")
    ResponseEntity<String> updateUser(@RequestBody Map<String,String> requestMap);
    @DeleteMapping("/delete/{id}")
    ResponseEntity<String> deleteUser(@PathVariable Integer id);
}

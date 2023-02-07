package eric.tsafack.api.service;

import eric.tsafack.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    ResponseEntity<String> createUser(Map<String, String> requestMap);

    ResponseEntity<User> getUserById(Integer id);

    ResponseEntity<String> updateUser(Map<String, String> requestMap);

    ResponseEntity<String> deleteUser(Integer id);

    ResponseEntity<List<User>> getAll();
}

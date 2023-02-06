package eric.tsafack.api.service;

import eric.tsafack.api.model.User;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Map;

public interface UserService {
    String createUser(Map<String, String> requestMap);

    User getUserById(Integer id);

    String updateUser(Map<String, String> requestMap);

    String deleteUser(Integer id);

    List<User> getAll();
}

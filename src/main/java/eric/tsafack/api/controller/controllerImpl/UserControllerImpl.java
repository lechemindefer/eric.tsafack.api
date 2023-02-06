package eric.tsafack.api.controller.controllerImpl;

import eric.tsafack.api.controller.UserController;
import eric.tsafack.api.model.User;
import eric.tsafack.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Autowired
    public UserControllerImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }

    @Override
    public ResponseEntity<String> createUser(Map<String, String> requestMap) {
        try {
            return new ResponseEntity<>(userService.createUser(requestMap),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("User doesn't created ", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        try {
           return new ResponseEntity<>(userService.getUserById(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        try{
            return new ResponseEntity<>(userService.getAll(),HttpStatus.OK);
        }catch (Exception ex){
           ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try {
           return new ResponseEntity<>(userService.updateUser(requestMap),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("User is not create", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        try {
            return new ResponseEntity<>(userService.deleteUser(id),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("User is not create", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

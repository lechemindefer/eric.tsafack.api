package eric.tsafack.api.service.serviceImpl;

import eric.tsafack.api.model.User;
import eric.tsafack.api.repository.UserRepository;
import eric.tsafack.api.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    private UserRepository userRepository;
    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(Map<String, String> requestMap) {
        try{
            log.info("createUser(Map<String, String> requestMap)");
            User user = new User();
            user.setAge(Integer.parseInt((requestMap.get("age"))));
            user.setFirstName(requestMap.get("firstName"));
            user.setLastName(requestMap.get("lastName"));
            user.setCity(requestMap.get("city"));
            user.setEmail(requestMap.get("email"));
            return userRepository.save(user);
            //return new ResponseEntity<>("user added successfully",HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new User();
    }

    @Override
    public User getUserById(Integer id) {
        try{
            log.info(" getUserById(Integer id)");
            Optional optional = userRepository.findById(id);
            if (optional.isPresent()){
                return userRepository.findById(id).get();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new User();
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try{
            log.info("updateUser(Map<String, String> requestMap)");
           User user = new User();
            user.setId(Integer.parseInt(requestMap.get("id")));
            user.setAge(Integer.parseInt((requestMap.get("age"))));
            user.setFirstName(requestMap.get("firstName"));
            user.setLastName(requestMap.get("lastName"));
            user.setCity(requestMap.get("city"));
            user.setEmail(requestMap.get("email"));
            userRepository.save(user);
            return  new ResponseEntity<>("user updated successfully",HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String>deleteUser(Integer id) {
        try{
            log.info("deleteUser(Integer id)");
            Optional optional = userRepository.findById(id);
           if (optional.isPresent()){
               userRepository.deleteById(id);
               return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public List<User> getAll() {
        try {
            return userRepository.findAll();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ArrayList<>();
    }
}
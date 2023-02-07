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
    public ResponseEntity<String> createUser(Map<String, String> requestMap) {
        try{
            log.info("createUser(Map<String, String> requestMap)");
            User user = new User();
            user.setAge(Integer.parseInt((requestMap.get("age"))));
            user.setFirstName(requestMap.get("firstName"));
            user.setLastName(requestMap.get("lastName"));
            user.setCity(requestMap.get("city"));
            user.setEmail(requestMap.get("email"));
            userRepository.save(user);

            return new ResponseEntity<>("user added successfully",HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<User> getUserById(Integer id) {
        try{
            log.info(" getUserById(Integer id)");
            Optional optional = userRepository.findById(id);
           if(optional.isPresent()){
               return new ResponseEntity<>(userRepository.findById(id).get(),HttpStatus.OK);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new User(),HttpStatus.INTERNAL_SERVER_ERROR) ;
    }

    @Override
    public ResponseEntity<String> updateUser(Map<String, String> requestMap) {
        try{
            log.info("updateUser(Map<String, String> requestMap)");
           User user = new User();
           Optional<User> optional = userRepository.findById(Integer.parseInt(requestMap.get("id")));
           if(optional.isPresent()){
            user.setId(Integer.parseInt(requestMap.get("id")));
            user.setAge(Integer.parseInt((requestMap.get("age"))));
            user.setFirstName(requestMap.get("firstName"));
            user.setLastName(requestMap.get("lastName"));
            user.setCity(requestMap.get("city"));
            user.setEmail(requestMap.get("email"));
            userRepository.save(user);
            return new ResponseEntity<>("user updated successfully",HttpStatus.OK) ;
           }else {
               return new ResponseEntity<>("User id does not exist",HttpStatus.OK);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("something went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<String> deleteUser(Integer id) {
        try{
            log.info("deleteUser(Integer id)");
            Optional optional = userRepository.findById(id);
           if (optional.isPresent()){
               userRepository.deleteById(id);
               return new ResponseEntity<>("user deleted successfully",HttpStatus.OK);
           }
           else {
               return new ResponseEntity<>("User id does not exist.",HttpStatus.OK);
           }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>("somthing went wrong",HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<List<User>> getAll() {
        try {
            return new ResponseEntity<>(userRepository.findAll(),HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

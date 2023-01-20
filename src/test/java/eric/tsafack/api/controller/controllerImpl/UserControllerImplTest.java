package eric.tsafack.api.controller.controllerImpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eric.tsafack.api.model.User;
import eric.tsafack.api.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.*;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserControllerImpl.class)
public class UserControllerImplTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;
    @MockBean
    private UserService userService;

    User user1 = User.builder()
            .id(1)
            .firstName("Tsafack")
            .lastName("Eric")
            .age(25)
            .email("eric.tsafack@gmail.com")
            .city("Padova")
            .build();

    User user2 = User.builder()
            .id(2)
            .firstName("Demanou")
            .lastName("Isabella")
            .age(22)
            .email("isabella.demanou@gmail.com")
            .city("Padova")
            .build();
    User user3 = User.builder()
            .id(3)
            .firstName("Azemo")
            .lastName("Annie")
            .age(30)
            .email("annie.azemo@gmail.com")
            .city("Massanzago")
            .build();



    @Test
    void createUser() throws Exception {
       /* Map<String,String> map = new HashMap<>();
        map.put("firstName",user1.getFirstName());
        map.put("lastName",user1.getLastName());
        map.put("age",user1.getAge().toString());
        map.put("email",user1.getEmail());
        map.put("city",user1.getCity());
        userService.createUser(map);*/

        mockMvc.perform(MockMvcRequestBuilders
                .post("/users/create")
                        .content(asJsonString(user1))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
                //.andExpect(jsonPath("$.status));
                //.andExpect(jsonPath("$").exists());
                //.andExpect(MockMvcResultMatchers.jsonPath("");

    }

    private static String  asJsonString(final User user) {
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void getUserById() throws Exception {
        when(userService.getUserById(1)).thenReturn(user1);
        mockMvc.perform(get("/users/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$").exists());
    }

    @Test
    void getUserByIdTest() {
        when(userService.getUserById(1)).thenReturn(user1);
        assertNotEquals(userService.getUserById(1).getId(),null);

        assertEquals(userService.getUserById(1).getFirstName(),"Tsafack");
        assertEquals(userService.getUserById(1).getLastName(),"Eric");
        assertEquals(userService.getUserById(1).getAge(),25);
        assertEquals(userService.getUserById(1).getEmail(),"eric.tsafack@gmail.com");
        assertEquals(userService.getUserById(1).getCity(),"Padova");
    }

    @Test
    public void getAll() throws Exception {
        when(userService.getAll()).thenReturn((List.of(user1,user2,user3)));
        mockMvc.perform(get("/users/list"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$",hasSize(3)))
                .andExpect(jsonPath("$").isArray());
        MvcResult result = mockMvc.perform(get("/users/list")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andReturn();
        String content = result.getResponse().getContentAsString();
    }

    @Test
    public void getAllList() throws Exception {
        when(userService.getAll()).thenReturn(Arrays.asList(user1,user2,user3));
        assertEquals(userService.getAll().size(),3);

        assertNotEquals(userService.getAll().get(0),null);
        assertEquals(userService.getAll().get(0).getFirstName(),"Tsafack");
        assertEquals(userService.getAll().get(0).getLastName(),"Eric");
        assertEquals(userService.getAll().get(0).getAge(),25);
        assertEquals(userService.getAll().get(0).getEmail(),"eric.tsafack@gmail.com");
        assertEquals(userService.getAll().get(0).getCity(),"Padova");

        assertNotEquals(userService.getAll().get(1),null);
        assertEquals(userService.getAll().get(1).getFirstName(),"Demanou");
        assertEquals(userService.getAll().get(1).getLastName(),"Isabella");
        assertEquals(userService.getAll().get(1).getAge(),22);
        assertEquals(userService.getAll().get(1).getEmail(),"isabella.demanou@gmail.com");
        assertEquals(userService.getAll().get(1).getCity(),"Padova");

        assertNotEquals(userService.getAll().get(2),null);
        assertEquals(userService.getAll().get(2).getFirstName(),"Azemo");
        assertEquals(userService.getAll().get(2).getLastName(),"Annie");
        assertEquals(userService.getAll().get(2).getAge(),30);
        assertEquals(userService.getAll().get(2).getEmail(),"annie.azemo@gmail.com");
        assertEquals(userService.getAll().get(2).getCity(),"Massanzago");


    }

    @Test
    void updateUser() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                .put("/users/update")
                .content(asJsonString(user1))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());

    }

    @Test
    void deleteUser() {
        userService.deleteUser(user1.getId());
        verify(userService,times(1)).deleteUser(user1.getId());
        ArgumentCaptor<Integer> userArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(userService).deleteUser(userArgumentCaptor.capture());
        Integer userDeletedById = userArgumentCaptor.getValue();
        assertNotNull(userDeletedById);
        assertEquals(1,userDeletedById);
    }
}
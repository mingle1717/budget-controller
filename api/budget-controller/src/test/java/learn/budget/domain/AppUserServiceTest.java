package learn.budget.domain;

import learn.budget.data.UserJdbcRepo;
import learn.budget.data.UserRepo;
import learn.budget.models.AppUser;
import learn.budget.models.MyRole;
import learn.budget.models.viewmodels.RegisterRequest;
import learn.budget.security.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AppUserServiceTest {
    @Autowired
    AppUserService service;
    @MockBean
    UserJdbcRepo repo;

    @Test
    void shouldRegisterValidUser() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword("BobPassword");
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertTrue(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithNullPassword() {

    }
    @Test
    void shouldNotRegisterUserWithBlankPassword() {

    }
    @Test
    void shouldNotRegisterUserWithPasswordUnderSixCharacters() {

    }
    @Test
    void shouldNotRegisterUserWithPasswordOverTwelveCharacters() {

    }
    @Test
    void shouldNotRegisterUserWithNullUsername() {

    }
    @Test
    void shouldNotRegisterUserWithBlankUsername() {

    }
    @Test
    void shouldNotRegisterUserWithNullEmail() {

    }
    @Test
    void shouldNotRegisterUserWithBlankEmail() {

    }
    @Test
    void shouldNotRegisterUserWithExistingUsername() {

    }
    @Test
    void shouldNotRegisterUserWithExistingEmail() {

    }
}

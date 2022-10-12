package learn.budget.domain;

import learn.budget.data.UserJdbcRepo;
import learn.budget.models.AppUser;
import learn.budget.models.MyRole;
import learn.budget.models.viewmodels.RegisterRequest;
import learn.budget.security.AppUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.util.ArrayList;


import static org.junit.jupiter.api.Assertions.assertFalse;
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
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertTrue(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithNullPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword(null);
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithBlankPassword() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword(" ");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithPasswordUnderSixCharacters() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword("BobPa");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithPasswordOverTwelveCharacters() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword("BobPasswordPasswordPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithNullUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername(null);
        request.setPassword("BobPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithBlankUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername(" ");
        request.setPassword("BobPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithNullEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(null);
        request.setUsername("Bob");
        request.setPassword("BobPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }

    // TODO: 10/12/2022 Fill in all the above tests with this mock method:

    @Test
    void shouldNotRegisterUserWithBlankEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail(" ");
        request.setUsername("Bob");
        request.setPassword("BobPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithExistingUsername() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword("BobPassword");
        when(repo.getUserByEmail(request.getEmail())).thenReturn(null);
        when(repo.getUserByUsername(request.getUsername())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                        request.getPasshash(), false, new ArrayList<MyRole>()));
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
    @Test
    void shouldNotRegisterUserWithExistingEmail() {
        RegisterRequest request = new RegisterRequest();
        request.setEmail("bob@bob.bob");
        request.setUsername("Bob");
        request.setPassword("BobPassword");
        when(repo.getUserByUsername(request.getUsername())).thenReturn(null);
        when(repo.getUserByEmail(request.getEmail())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        when(repo.register(request, request.getPasshash())).thenReturn(new AppUser(1, "Bob", "bob@bob.bob",
                request.getPasshash(), false, new ArrayList<MyRole>()));
        assertFalse(service.register(request).isSuccess());
    }
}

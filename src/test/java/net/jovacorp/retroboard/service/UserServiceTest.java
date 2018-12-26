package net.jovacorp.retroboard.service;

import net.jovacorp.retroboard.model.User;
import net.jovacorp.retroboard.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.Mockito.*;

@RunWith(SpringRunner.class)
public class UserServiceTest {

  @MockBean
  private UserRepository userRepository;

  private UserService userService;

  @Before
  public void setup() {
    userService = new UserService(userRepository);
  }

  @Test
  public void loadUserByUsername_HappyPath_shoudlReturn1User() {
    // Given
    User user = new User();
    user.setUsername("shazin");
    user.setPassword("sha980");
    user.setRole("USER");

    when(userRepository.findByUsername("shazin")).thenReturn(user);

    // When
    UserDetails actual = userService.loadUserByUsername("shazin");

    // Then
    verify(userRepository, times(1)).findByUsername("shazin");

  }
}

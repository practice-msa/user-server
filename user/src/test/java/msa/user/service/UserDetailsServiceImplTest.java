package msa.user.service;

import msa.user.domain.User;
import msa.user.domain.UserDetails;
import msa.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UsernameNotFoundException;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsServiceImplTest {


    @Test
    void loadUserByUsername_UserFound_ReturnsUserDetails() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository);

        String username = "exampleUsername";
        User user = new User(); // Create a user for testing
        user.setUid(username);

        Mockito.when(userRepository.getByUid(username))
                .thenReturn(user);

        // When
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        // Then
        assertEquals(username, userDetails.getUsername());
        // Add more assertions based on UserDetails properties or behavior if needed
    }

    @Test
    void loadUserByUsername_UserNotFound_ThrowsException() {
        // Given
        UserRepository userRepository = Mockito.mock(UserRepository.class);
        UserDetailsServiceImpl userDetailsService = new UserDetailsServiceImpl(userRepository);

        String username = "nonexistentUsername";

        // When
        Mockito.when(userRepository.getByUid(username))
                .thenReturn(null);

        // Then
        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername(username)
        );
    }
}
package msa.user.service;

import lombok.RequiredArgsConstructor;
import msa.user.domain.User;
import msa.user.domain.UserDetails;
import msa.user.repository.UserRepository;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService{
//    private final Logger LOGGER = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username){
//        LOGGER.info("[loadUserByUsername] loadUserByUsername 수행. username:{}",username);
        User user = userRepository.getByUid(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return user;
    }
}

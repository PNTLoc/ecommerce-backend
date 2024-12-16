package ecommerce.api.service;

import ecommerce.api.entity.User;
import ecommerce.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public boolean authenticate(String username, String password) {
        User user = userRepository.findByUsername(username).orElse(null);
        return user != null && user.getPassword().equals(password);
    }
}

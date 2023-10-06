package service;

import java.util.Set;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.java.server.user.entity.User;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import model.RegisterUserRequest;
import repository.UserRepository;

@Service
public class UserService {
    @Autowired // Auto inject dependency using springboot
    private UserRepository userRepository;

    @Autowired
    private Validator validator;

    @Transactional // Run with db transaction
    public void register(RegisterUserRequest request) throws RuntimeException {
        // Validate request body
        Set<ConstraintViolation<RegisterUserRequest>> constraintViolations = validator.validate(request);
        if (constraintViolations.size() != 0) {
            throw new ConstraintViolationException(constraintViolations);
        }

        // Check user data
        if (userRepository.existsById(request.getId().toString())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "user already exist");
        }

        // Create new user
        User newUser = new User();
        newUser.setEmail(request.getEmail());
        newUser.setUsername(request.getUsername());
        newUser.setPassword(request.getPassword()); // TODO encrypt password

        // Save to database
        userRepository.save(newUser);
    }
}

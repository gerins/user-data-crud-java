package com.java.server.user.controller;

import java.util.Optional;

import org.jpos.iso.ISOException;
import org.jpos.iso.ISOMsg;
import org.jpos.iso.packager.GenericPackager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.java.server.user.entity.User;
import com.java.server.user.model.RegisterUserRequest;
import com.java.server.user.model.WebResponse;
import com.java.server.user.service.UserService;

@RestController
public class UserController {
    private UserService userService;

    // Constructor based dependency injection
    // Spring will automatically provide an instance of UserService if it's
    // available in the application context.
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Define register route with method POST
    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterUserRequest request) {
        userService.register(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    // Define route to get user by ID with method GET
    @GetMapping(path = "/api/users/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getByID(@PathVariable Long userId) {
        Optional<User> userOptional = userService.getByID(userId);

        if (userOptional.isPresent()) {
            return ResponseEntity.ok(userOptional.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Page<User>> getUsersWithPagination(Pageable pageable) {
        Page<User> usersPage = userService.getUsersWithPagination(pageable);

        if (!usersPage.isEmpty()) {
            return ResponseEntity.ok(usersPage);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

    @GetMapping(path = "/api/iso8583", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<byte[]> generateISO8583Message() {
        ISOMsg isoMsg = new ISOMsg();

        try {
            // Load the packager from your XML definition file
            GenericPackager packager = new GenericPackager("./files/spec/iso8583.xml");
            isoMsg.setPackager(packager);

            isoMsg.setMTI("0200");
            isoMsg.set(3, "000000"); // Field 3
            isoMsg.set(4, "10000"); // Field 4

            byte[] messageBytes = isoMsg.pack();

            return ResponseEntity.ok(messageBytes);
        } catch (ISOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}

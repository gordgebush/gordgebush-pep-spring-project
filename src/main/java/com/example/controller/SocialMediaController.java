package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import com.example.entity.Account;
import com.example.entity.Message;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidLoginException;
import com.example.exception.InvalidRegistrationException;
import com.example.service.AccountService;
import com.example.service.MessageService;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

 @RestController
public class SocialMediaController {

        @Autowired
        private AccountService accountService;
        @Autowired
        private MessageService messageService;

        //register user
        @PostMapping("/register")
        public ResponseEntity<?> registerAccount(@RequestBody Account x){
        Account createdAccount = accountService.registerAccount(x);
        return ResponseEntity.ok(createdAccount); //200
        }

        //dupe user
        @ExceptionHandler(DuplicateUsernameException.class)
        public ResponseEntity<String> handleDuplicateUsername(DuplicateUsernameException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage()); //409
        }

        //password bad
        @ExceptionHandler(InvalidRegistrationException.class)
        public ResponseEntity<String> handleInvalidRegistration(InvalidRegistrationException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage()); //400
        }
        //end register user

        //login
        @PostMapping("/login")
        public ResponseEntity<?> login(@RequestBody Account account){
                Account loggedAccount = accountService.login(account);
                return ResponseEntity.ok(loggedAccount);
        }

        @ExceptionHandler(InvalidLoginException.class)
        public ResponseEntity<String> handleInvalidLogin(InvalidLoginException ex){
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
        }
        //login end

        //createmessage
        @PostMapping("/messages")
        public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Message createdMessage = messageService.createMessage(message);
        return ResponseEntity.ok(createdMessage); 
        }

        @ExceptionHandler(IllegalArgumentException.class)
        public ResponseEntity<String> handleBadMessage(IllegalArgumentException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage()); 
        }
        //createmessage end

        //getall messages
        @GetMapping("/messages")
        public ResponseEntity<List<Message>> getAllMessages(){
                List<Message> messages = messageService.getAlMessages();
                return ResponseEntity.ok(messages);
        }
}

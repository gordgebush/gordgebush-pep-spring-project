package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.Account;
import com.example.exception.DuplicateUsernameException;
import com.example.exception.InvalidRegistrationException;
import com.example.service.AccountService;

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


}

package com.junbetterway.security.springauthzero.controller;

import java.math.BigDecimal;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.junbetterway.security.springauthzero.model.Account;

/**
 * 
 * @author junbetterway
 *
 */
@RestController
@RequestMapping(path = "api/account")
public class AccountController {

    @GetMapping(value = "/public")
    public ResponseEntity<Account> publicEndpoint() {
    	Account account = Account.builder()
    			.name("Jun King Minon")
    			.balance(new BigDecimal("5500"))
    			.remarks("You DO NOT need to be authenticated here!!!")
    			.build();
		return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/private")
    public ResponseEntity<Account> privateEndpoint() {
    	Account account = Account.builder()
    			.name("Jun King Minon")
    			.balance(new BigDecimal("5500"))
    			.remarks("You can see this because you are authenticated!!!")
    			.build();
		return new ResponseEntity<>(account, HttpStatus.OK);
    }

    @GetMapping(value = "/private-scoped")
    public ResponseEntity<Account> privateScopedEndpoint() {
    	Account account = Account.builder()
    			.name("Jun King Minon")
    			.balance(new BigDecimal("5500"))
    			.remarks("You can see this because you are authenticated with a token granted the 'read:accounts' scope")
    			.build();
		return new ResponseEntity<>(account, HttpStatus.OK);
    }
    
}

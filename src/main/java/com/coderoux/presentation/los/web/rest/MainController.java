package com.coderoux.presentation.los.web.rest;

import com.coderoux.presentation.los.domain.Account;
import com.coderoux.presentation.los.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

/**
 * Created by jroux on 20.06.2016.
 */
@RestController
@RequestMapping(value = "api/")
        public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    private static final String QUERY = "select * from account where username = '%s';";

    @Autowired
    private AccountService accountService;

    @RequestMapping(value = "example1", method = RequestMethod.POST)
    public String example1(@RequestParam String username) {
        LOGGER.debug("Example1: reset password");
        return accountService.resetPasswordJdbc(username);
    }

    @RequestMapping(value = "example2", method = RequestMethod.POST)
    public String example2(@RequestParam String username) {
        LOGGER.debug("Example2: reset clean password");
        return accountService.resetPasswordCleanJdbc(username);
    }

    @RequestMapping(value = "example3", method = RequestMethod.POST)
    public String example3(@RequestParam String username) {
        LOGGER.debug("Example3: reset password hql");
        return accountService.resetPasswordHql(username);
    }

    @RequestMapping(value = "example4", method = RequestMethod.POST)
    public String example4(@RequestParam String username) {
        LOGGER.debug("Example4: reset clean password hql");
        return accountService.resetCleanPasswordHql(username);
    }

    @RequestMapping(value = "example5", method = RequestMethod.POST)
    public String example5(@RequestParam String username) throws NoSuchAlgorithmException {
        LOGGER.debug("Example5: hash");
        return accountService.hash(username);
    }

    @RequestMapping(value = "example6", method = RequestMethod.POST)
    public String example6(@RequestParam String username, @RequestParam int iterations) throws NoSuchAlgorithmException {
        LOGGER.debug("Example6: hash bcrypt");
        long start = System.currentTimeMillis();
        String hashbcrypt = accountService.hashbcrypt(username, iterations);
        long end = System.currentTimeMillis();
        long duration = end - start;
        LOGGER.debug("Performed in: " + duration);
        return hashbcrypt + " <br />Performed in " + duration + "ms";
    }

    @RequestMapping(value = "example7", method = RequestMethod.POST)
    public String example7(@RequestParam String line1, @RequestParam String line2) throws NoSuchAlgorithmException {
        LOGGER.debug("Example7: slow equals");
        long start = System.currentTimeMillis();
        boolean equlity = accountService.slowEquals(line1, line2);
        long end = System.currentTimeMillis();
        long duration = end - start;
        LOGGER.debug("Performed in: " + duration);
        return equlity + " <br />Performed in " + duration + "ms";
    }

    @RequestMapping(value = "accounts", method = RequestMethod.GET)
    public Iterable<Account> getAll() {
        LOGGER.debug("Get all accounts");
        return accountService.getAll();
    }



}

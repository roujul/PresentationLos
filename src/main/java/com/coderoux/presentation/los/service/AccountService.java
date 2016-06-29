package com.coderoux.presentation.los.service;

import com.coderoux.presentation.los.domain.Account;
import com.coderoux.presentation.los.repository.AccountDao;
import com.coderoux.presentation.los.repository.AccountHibernate;
import com.coderoux.presentation.los.repository.AccountJdbc;
import org.apache.commons.lang.StringUtils;
import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by jroux on 20.06.2016.
 */
@Service
public class AccountService {

    private static final Logger LOG = LoggerFactory.getLogger(AccountService.class);

    private final AccountDao accountDao;
    private final AccountJdbc accountJdbc;
    private final AccountHibernate accountHibernate;

    @Autowired
    public AccountService(AccountDao accountDao, AccountJdbc accountJdbc, AccountHibernate accountHibernate) {
        this.accountDao = accountDao;
        this.accountJdbc = accountJdbc;
        this.accountHibernate = accountHibernate;
    }

    public Iterable<Account> getAll() {
        return accountDao.findAll();
    }

    public String resetPasswordJdbc(String username) {
        String password = null;
        try {
            password = accountJdbc.getPassword(username);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur serveur";
        }
        if(StringUtils.isNotBlank(password)) {
            return "Mail envoyé";
        }
        return "L'email n'est pas dans la base de données";
    }

    public String resetPasswordCleanJdbc(String username) {
        String password = null;
        try {
            password = accountJdbc.getCleanPassword(username);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur serveur";
        }
        if(StringUtils.isNotBlank(password)) {
            return "Mail envoyé";
        }
        return "L'email n'est pas dans la base de données";
    }

    public String resetPasswordHql(String username) {
        String password = null;
        try {
            password = accountHibernate.getPassword(username);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur serveur";
        }
        if(StringUtils.isNotBlank(password)) {
            return "Mail envoyé";
        }
        return "L'email n'est pas dans la base de données";
    }

    public String resetCleanPasswordHql(String username) {
        String password = null;
        try {
            password = accountHibernate.getCleanPassword(username);
        } catch (Exception e) {
            e.printStackTrace();
            return "Erreur serveur";
        }
        if(StringUtils.isNotBlank(password)) {
            return "Mail envoyé";
        }
        return "L'email n'est pas dans la base de données";
    }

    public void deleteAll() {
        accountDao.deleteAll();
    }

    public void loadData() {
        accountDao.save(Account.build("jroux@smartwavesa.com", "julien"));
        accountDao.save(Account.build("ymarrek@smartwavesa.co", "yannick"));
        accountDao.save(Account.build("vsimon@smartwavesa.com", "valérian"));
        accountDao.save(Account.build("ylarvor@smartwavesa.com", "yannick"));
        accountDao.save(Account.build("rtrompier@smartwavesa.com", "rémy"));
    }


    public static SecureRandom getSalt() throws NoSuchAlgorithmException {
        return SecureRandom.getInstance("SHA1PRNG");
    }

    public static ShaPasswordEncoder getPasswordEncoder() {
        return new ShaPasswordEncoder(512);
    }

    public static String encodePassword(String rawPass, Object salt) throws NoSuchAlgorithmException {
        String password = getPasswordEncoder().encodePassword(rawPass, salt);
        assert(getPasswordEncoder().isPasswordValid(password, rawPass, salt));
        return password;
    }

    public String hash(String username) throws NoSuchAlgorithmException {
        SecureRandom salt = getSalt();
        String hashed = encodePassword(username, salt);
        return "Salt: " + salt.nextInt() + " <br /> Hash: " + hashed;
    }

    public String hashbcrypt(String username, int iterations) {
        return BCrypt.hashpw(username, BCrypt.gensalt(iterations));
    }

    public boolean slowEquals(String line1, String line2) {
        byte[] a = line1.getBytes();
        byte[] b = line2.getBytes();
        int diff = a.length ^ b.length;
        for(int i = 0; i < a.length && i < b.length; i++) {
            diff |= a[i] ^ b[i];
        }
        return diff == 0;
    }
}

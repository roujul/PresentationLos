package com.coderoux.presentation.los.repository;

import com.coderoux.presentation.los.domain.Account;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.vendor.HibernateJpaSessionFactoryBean;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.util.List;

/**
 * Created by jroux on 26.06.2016.
 */
@Repository
public class AccountHibernate {

    @PersistenceContext
    EntityManager entityManager;

    private final DataSource dataSource;

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHibernate.class);

    @Autowired
    public AccountHibernate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static final String SELECT_FROM_ACCOUNT = "from Account where username = '%s'";
    public String getPassword(String username) {
        LOGGER.debug("Get password: " + username);
        Session currentSession = entityManager.unwrap(Session.class);
        String sql = String.format(SELECT_FROM_ACCOUNT, username);
        Query query=currentSession.createQuery(sql);
        List rows=query.list();
        if(rows.isEmpty()) {
            return null;
        }
        Account account = (Account)rows.get(0);
        return account.getPassword();
    }

    public static final String CLEAN_SELECT_FROM_ACCOUNT = "from Account where username = :email";
    public String getCleanPassword(String username) {
        LOGGER.debug("Get clean password: password" + username);
        Session currentSession = entityManager.unwrap(Session.class);
        Query query=currentSession.createQuery(CLEAN_SELECT_FROM_ACCOUNT);
        query.setParameter("email", username);
        List rows=query.list();
        if(rows.isEmpty()) {
            return null;
        }
        Account account = (Account)rows.get(0);
        return account.getPassword();
    }

}

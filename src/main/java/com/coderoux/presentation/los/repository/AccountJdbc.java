package com.coderoux.presentation.los.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;

/**
 * Created by jroux on 20.06.2016.
 */
@Repository
public class AccountJdbc {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountJdbc.class);

    private final DataSource dataSource;

    @Autowired
    public AccountJdbc(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static final String SELECT_FROM_ACCOUNT = "select * from account where username = '%s';";
    public String getPassword(String user) throws Exception {
        LOGGER.debug("Get password: " + user);
        String password = null;
        String query = String.format(SELECT_FROM_ACCOUNT, user);
        Statement statement = dataSource.getConnection().createStatement();
        ResultSet resultSet = statement.executeQuery(query);
        while(resultSet.next()) {
            password = resultSet.getString("password");
        }
        return password;
    }

    private static final String CLEAN_SELECT_FROM_ACCOUNT = "select * from account where username = ?";
    public String getCleanPassword(String user) throws Exception {
        LOGGER.debug("Get clean password: " + user);
        String password = null;
        PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(CLEAN_SELECT_FROM_ACCOUNT);
        preparedStatement.setString(1, user);
        ResultSet resultSet = preparedStatement.executeQuery();
        while(resultSet.next()) {
            password = resultSet.getString("password");
        }
        return password;
    }
}


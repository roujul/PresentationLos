package com.coderoux.presentation.los;

import com.coderoux.presentation.los.repository.AccountDao;
import com.coderoux.presentation.los.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.net.UnknownHostException;


/**
 * Created by jroux on 20.06.2016.
 */
@SpringBootApplication
@PropertySource("classpath:/META-INF/application.properties")
public class ServerLauncher {

    private static final Logger LOG = LoggerFactory.getLogger(ServerLauncher.class);

    @Autowired
    private AccountService accountService;

    public static void main(String[] args) throws UnknownHostException {
        SpringApplication app = new SpringApplication(ServerLauncher.class);
        app.run(args);
        LOG.debug("Server started....");
    }

    @PostConstruct
    public void postConstruct() {
        LOG.debug("Creating database");
        accountService.deleteAll();
        accountService.loadData();
        LOG.debug("Database up and running");
    }
}

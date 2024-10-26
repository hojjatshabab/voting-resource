package com.ream.core;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.util.StreamUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

@EnableFeignClients
@SpringBootApplication
@EnableAsync
public class VotingResource {


    private static final Logger log = LoggerFactory.getLogger(VotingResource.class);

    private final Environment env;
    private final DataSource dataSource;

    public VotingResource(Environment env, DataSource dataSource) {
        this.env = env;
        this.dataSource = dataSource;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(VotingResource.class);
        ApplicationContext context = app.run(args);
        Environment env = context.getEnvironment();
        String rootPath = env.getProperty("spring.datasource.file.rootPath");
        String directory = env.getProperty("spring.datasource.file.directory");
//        String directoryPath = env.getProperty("spring.datasource.file.rootPath").concat(env.getProperty("spring.datasource.file.directory"));
        String directoryPath = rootPath + directory;
        createDirectoryIfNotExists(directoryPath);
        DataSource dataSource = context.getBean(DataSource.class);
        insertSql(env);
        //initSql(dataSource);
        logApplicationStartup(env);
    }


    public static void initSql( DataSource dataSource) {
        try (Connection connection = dataSource.getConnection()) {
            Resource resource = new ClassPathResource("init_data.sql");

            // Read the content of the SQL file with UTF-8 encoding
            try (InputStream inputStream = resource.getInputStream()) {
                String sqlScript = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

                // Execute the SQL script
                ScriptUtils.executeSqlScript(connection, new ByteArrayResource(sqlScript.getBytes(StandardCharsets.UTF_8)));

                log.info("SQL script executed successfully");
            }

        } catch (SQLException e) {
            log.error("Failed to execute SQL script", e);
        } catch (IOException e) {
            log.error("Failed to read SQL script", e);
        }
    }


    private static void logApplicationStartup(Environment env) {
        String protocol = Optional.ofNullable(env.getProperty("server.ssl.key-store")).map(key -> "https").orElse("http");
        String serverPort = env.getProperty("server.port");
        String contextPath = Optional
                .ofNullable(env.getProperty("server.servlet.context-path"))
                .filter(StringUtils::isNotBlank)
                .orElse("/");
        String hostAddress = "localhost";
        try {
            hostAddress = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            log.warn("The host name could not be determined, using `localhost` as fallback");
        }
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Application '{}' is running! Access URLs:\n\t" +
                        "Local: \t\t{}://localhost:{}{}\n\t" +
                        "External: \t{}://{}:{}{}\n\t" +
                        "Profile(s): \t{}\n----------------------------------------------------------",
                env.getProperty("spring.application.name"),
                protocol,
                serverPort,
                contextPath,
                protocol,
                hostAddress,
                serverPort,
                contextPath,
                env.getActiveProfiles().length == 0 ? env.getDefaultProfiles() : env.getActiveProfiles()
        );

        String configServerStatus = env.getProperty("configserver.status");
        if (configServerStatus == null) {
            configServerStatus = "Not found or not setup for this application";
        }
        log.info(
                "\n----------------------------------------------------------\n\t" +
                        "Config Server: \t{}\n----------------------------------------------------------",
                configServerStatus
        );
    }


    private static void createDirectoryIfNotExists(String directoryPath) {
        File directory = new File(directoryPath);
        if (!directory.exists()) {
            boolean created = directory.mkdirs();
            if (created) {
                log.info("Directory created successfully: " + directoryPath);
            } else {
                log.info("Failed to create directory: " + directoryPath);
            }
        } else {
            log.info("Directory already exists: " + directoryPath);
        }
    }




    private static void insertSql(Environment env) {

        Connection connection = null;
        try {
            connection = DriverManager.getConnection(
                    env.getProperty("spring.datasource.url"), env.getProperty("spring.datasource.username"), env.getProperty("spring.datasource.password"));


            try {

                    final String statement =   "INSERT INTO \"public\".\"jw_user\" VALUES ('48a73c81-41bc-4a63-8e57-c276ef790990', '48a73c81-41bc-4a63-8e57-c276ef790990',  now(),false,'', now(), '48a73c81-41bc-4a63-8e57-c276ef790990', 't', 'admin', 'system', '$2a$10$LTM5q3/aTqDCc64VlrEe8uSRauGCSv2Uga.S9SGkH4Dqz9MyYb1HG', 'admin') ON CONFLICT DO NOTHING;" +
                            "INSERT INTO \"public\".\"jw_role\" VALUES ('60987c30-4a78-435d-8e03-1583d262dc2c', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', now(),false,'', now(),'614bfb0e-d711-4c0e-a0c2-59af1c00d2e3' , NULL, 'administrator', 'مدیرسیستم') ON CONFLICT DO NOTHING;"+
                           " INSERT INTO \"public\".\"jw_role\" VALUES ('60987c30-4a78-435d-8e03-1583d262dc2e', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', now(),false,'', now(),'614bfb0e-d711-4c0e-a0c2-59af1c00d2e3' , NULL, 'members', 'اعضا') ON CONFLICT DO NOTHING;"+
                            "INSERT INTO \"public\".\"jw_user_role\" VALUES ('57f126e2-531d-4b28-ad8f-f5fb510a6684', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3',  now(),false,'', now(), '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', '60987c30-4a78-435d-8e03-1583d262dc2c', '48a73c81-41bc-4a63-8e57-c276ef790990') ON CONFLICT DO NOTHING;" +
                            "INSERT INTO \"public\".\"jw_permission\" VALUES ('00329a2d-f060-413e-a2de-eb836ea93a84', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3',  now(),false,'', now(), '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', 'GET', 'findAll','مشاهده',null) ON CONFLICT DO NOTHING;"+
                            "INSERT INTO \"public\".\"jw_permission\" VALUES ('00329a2d-f060-413e-a2de-eb836ea93a85', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3',  now(),false,'', now(), '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', 'POST', 'save','ذخیره',null) ON CONFLICT DO NOTHING;"+
                            "INSERT INTO \"public\".\"jw_permission\" VALUES ('00329a2d-f060-413e-a2de-eb836ea93a86', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3',  now(),false,'', now(), '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', 'PUT', 'update','ویرایش',null) ON CONFLICT DO NOTHING;"+
                            "INSERT INTO \"public\".\"jw_permission\" VALUES ('00329a2d-f060-413e-a2de-eb836ea93a87', '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3',  now(),false,'', now(), '614bfb0e-d711-4c0e-a0c2-59af1c00d2e3', 'DELETE', 'delete','حذف',null) ON CONFLICT DO NOTHING;"
                            ;


                    connection.createStatement().executeQuery(statement).next();


            } catch (SQLException e) {

                if (e.toString().contains("No results were returned by the query."))
                    log.info("executeQuery insertSql");
                else
                    log.info(" not executeQuery insertSql");
            }

        } catch (Exception e) {
            log.info(" not connection  insertSql");
        }
    }












}

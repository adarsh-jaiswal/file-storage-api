package com.lc.filestorageapi.utils;

import com.lc.filestorageapi.vo.Document;
import org.hibernate.HibernateException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.util.Properties;

import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_DDL_AUTO;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_DIALECT;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_DRIVER;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_PASSWORD;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_SHOW_SQL;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_URL;
import static com.lc.filestorageapi.utils.FileStorageConstants.HIBERNATE_STORAGE_USERNAME;
import static java.util.Objects.isNull;

public class HibernateUtils {

    private static SessionFactory sessionFactory;
    private static Properties settings;

    public HibernateUtils(Properties settings) throws InstantiationException {
        this.settings = settings;
        validatePropertiesAreSet();
        loadSessionFactory();
    }

    private static void loadSessionFactory() throws InstantiationException {
        if (isNull(sessionFactory)) {
            try {
                Configuration configuration = new Configuration();
                Properties properties = new Properties();
                properties.put(Environment.DRIVER, settings.getProperty(HIBERNATE_STORAGE_DRIVER));
                properties.put(Environment.URL, settings.getProperty(HIBERNATE_STORAGE_URL));
                properties.put(Environment.USER, settings.getProperty(HIBERNATE_STORAGE_USERNAME));
                properties.put(Environment.PASS, settings.getProperty(HIBERNATE_STORAGE_PASSWORD));
                properties.put(Environment.DIALECT, settings.getProperty(HIBERNATE_STORAGE_DIALECT));
                properties.put(Environment.SHOW_SQL, settings.getProperty(HIBERNATE_STORAGE_SHOW_SQL));
                properties.put(Environment.HBM2DDL_AUTO, settings.getProperty(HIBERNATE_STORAGE_DDL_AUTO));
                configuration.setProperties(properties);
                configuration.addAnnotatedClass(Document.class);
                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (HibernateException e) {
                throw new InstantiationException("Not able to create database connection. " + e.getMessage());
            }
        }
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    private static void validatePropertiesAreSet() {
        doSanityCheckOnProperty(HIBERNATE_STORAGE_DRIVER);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_URL);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_USERNAME);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_PASSWORD);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_DIALECT);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_SHOW_SQL);
        doSanityCheckOnProperty(HIBERNATE_STORAGE_DDL_AUTO);
    }

    private static void doSanityCheckOnProperty(String propertyName) {
        String property = settings.getProperty(propertyName);
        if (isNull(property) || property.trim().isEmpty()) {
            throw new IllegalArgumentException(propertyName + " property is not set.");
        }
        settings.setProperty(propertyName , property.trim());
    }
}


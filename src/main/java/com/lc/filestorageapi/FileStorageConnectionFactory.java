package com.lc.filestorageapi;

import com.lc.filestorageapi.utils.HibernateUtils;
import com.lc.filestorageapi.utils.PropertyReader;
import com.lc.filestorageapi.utils.StorageType;

import java.util.Properties;

import static com.lc.filestorageapi.utils.FileStorageConstants.LOCAL_STORAGE_PATH;
import static com.lc.filestorageapi.utils.FileStorageConstants.STORAGE_TYPE;
import static java.util.Objects.isNull;

public class FileStorageConnectionFactory {

    private Properties properties;

    public FileStorageConnectionFactory() {
        properties = new PropertyReader().initializeProperties();
    }

    public FileStorageConnection createFileStorageConnection() throws InstantiationException {
        String storageType = properties.getProperty(STORAGE_TYPE);
        if (isNull(storageType) || storageType.trim().isEmpty()) {
            throw new IllegalArgumentException("The property " + STORAGE_TYPE + "is not set.");
        }
        try {
            switch (StorageType.valueOf(storageType.toLowerCase())) {
                case postgres:
                case mysql:
                    return new RDBFileStorageConnectionImpl(new HibernateUtils(properties));
                case local:
                    return new LocalStorageConnectionImpl(properties.getProperty(LOCAL_STORAGE_PATH));
            }
        } catch (IllegalArgumentException e) {
            throw new UnsupportedOperationException(storageType + " is currently not supported.");
        }
        return null;
    }

}

package com.lc.filestorageapi;

import com.lc.filestorageapi.vo.Document;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static com.lc.filestorageapi.utils.DocumentValidationUtil.validateDocument;
import static com.lc.filestorageapi.utils.FileStorageConstants.LOCAL_STORAGE_PATH;
import static java.util.Objects.isNull;

public class LocalStorageConnectionImpl implements FileStorageConnection {

    private String path;

    public LocalStorageConnectionImpl(String path) {
        if (isNull(path) || path.trim().isEmpty()) {
            throw new IllegalArgumentException(LOCAL_STORAGE_PATH + " is not set.");
        }
        this.path = path;
    }


    @Override
    public boolean saveDocument(Document document) throws Exception {
        validateDocument(document);
        String filePath = this.path + "\\"+ document.getName() + document.getId()+".txt";
        //to-do need to also check for duplicate file name with above combination already present then throw exception.
        try {
            Path path = Paths.get(filePath);
            Files.write(path, document.getData());
        } catch (IOException e) {
            e.printStackTrace();
            throw new Exception("Error is saving the document.");
        }
        return false;
    }

    @Override
    public Document getDocument(long documentId) throws Exception {
        if (documentId <= 0 ) {
            throw new IllegalArgumentException("Invalid document id.");
        }
        File folder = new File(this.path);

        FilenameFilter txtFileFilter = (dir, name) -> {
            if(name.endsWith(documentId + ".txt"))
                return true;
            else
                return false;
        };
        File[] files = folder.listFiles(txtFileFilter);
        if (isNull(files) || files.length ==0) {
            throw new Exception("No Document found for the given id.");
        }
        File file = files[0];
        // to-do set the properties of the file.
        Document document = new Document();
        document.setName(file.getName());
        // to-do convert the file to byte array.
        return document;
    }

    //TO-DO
    @Override
    public Document getDocumentAttribute(long documentId) {
        return null;
    }

    //TO-DO
    @Override
    public boolean renameDocument(long documentId, String newName) {
        return false;
    }

    //TO-DO
    @Override
    public boolean deleteDocument(long documentId) {
        return false;
    }
}

package com.lc.filestorageapi.utils;

import com.lc.filestorageapi.vo.Document;

import java.util.Date;

import static java.util.Objects.isNull;

public class DocumentValidationUtil {

    public static void validateDocument(Document document) throws Exception {
        if (isNull(document)) {
            throw new IllegalArgumentException("Document is mandatory.");
        }
        String documentName = document.getName();
        if (isNull(documentName) || documentName.trim().isEmpty()) {
            throw new Exception("Name of a document is mandatory.");
        }
        Date documentCreationDate = document.getCreationTimeStamp();
        if (isNull(documentCreationDate)) {
            throw new Exception("Date of a document is mandatory.");
        }
        Date documentUpdationDate = document.getCreationTimeStamp();
        if (isNull(documentUpdationDate)) {
            throw new Exception("Date of a document is mandatory.");
        }
        if (documentCreationDate.compareTo(documentUpdationDate) > 0) {
            throw new Exception("Date of a creation of a document cannot be greater than updation date.");
        }

    }
}

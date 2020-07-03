package com.lc.filestorageapi;

import com.lc.filestorageapi.vo.Document;

public interface FileStorageConnection {

    /**
     * To save a file to the storage system.
     * @param document the document to be saved.
     * @return true if successful else false.
     */
    boolean saveDocument(Document document) throws Exception;

    /**
     * To retrieve a document from the storage system.
     * @param documentId the document id that is to be retrieved.
     * @return.
     */
    Document getDocument(long documentId) throws Exception;

    /**
     * To get the document attributes of a particular document.
     * @param documentId the document to be retrieved.
     * @return the document.
     */
    Document getDocumentAttribute(long documentId) throws Exception;

    /**
     * To rename a document in the storage.
     * @param documentId for which the rename is to be done.
     * @param newName the new name for the document.
     * @return the document.
     */
    boolean renameDocument(long documentId, String newName) throws Exception;


    /**
     * To delete the document from the storage.
     * @param documentId of the document to be deleted.
     * @return the document.
     */
    boolean deleteDocument(long documentId) throws Exception;
}

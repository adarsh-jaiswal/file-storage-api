package com.lc.filestorageapi;

import com.lc.filestorageapi.utils.HibernateUtils;
import com.lc.filestorageapi.vo.Document;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Date;

import static com.lc.filestorageapi.utils.DocumentValidationUtil.validateDocument;
import static java.util.Objects.isNull;

public class RDBFileStorageConnectionImpl implements FileStorageConnection {

    private HibernateUtils hibernateUtils;

    public RDBFileStorageConnectionImpl(HibernateUtils hibernateUtils) {
        this.hibernateUtils = hibernateUtils;
    }

    @Override
    public boolean saveDocument(Document document) throws Exception {
        validateDocument(document);
        Session session = hibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(document);
            tx.commit();
        } catch (HibernateException e) {
            if (isNull(tx)) tx.rollback();
            e.printStackTrace();
            throw new Exception("Unable to save the document." + e.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public Document getDocument(long documentId) throws Exception {
        if (documentId <= 0 ) {
            throw new IllegalArgumentException("Invalid document id.");
        }

        Session session = hibernateUtils.getSessionFactory().openSession();
        try {
            Document document = session.get(Document.class, documentId);
            return document;
        } catch (HibernateException e) {
            e.printStackTrace();
            throw new Exception("Unable to find the document." + e.getMessage());
        } finally {
            session.close();
        }
    }

    @Override
    public Document getDocumentAttribute(long documentId) throws Exception {
        if (documentId <= 0 ) {
            throw new IllegalArgumentException("Invalid document id.");
        }
       return getDocument(documentId);
    }

    @Override
    public boolean renameDocument(long documentId, String newName) throws Exception {
        if (documentId <= 0 ) {
            throw new IllegalArgumentException("Invalid document id.");
        }
        if (isNull(newName) || newName.trim().isEmpty()) {
            throw new IllegalArgumentException("New Name for the document is mandatory");
        }
        Session session = hibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Document document = session.get(Document.class, documentId);
            document.setName(newName);
            document.setLastUpdatedTimeStamp(new Date());
            session.saveOrUpdate(document);
            tx.commit();
        } catch (HibernateException e) {
            if (isNull(tx)) tx.rollback();
            e.printStackTrace();
            throw new Exception("Unable to rename the document." + e.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

    @Override
    public boolean deleteDocument(long documentId) throws Exception {
        if (documentId <= 0 ) {
            throw new IllegalArgumentException("Invalid document id.");
        }
        Session session = hibernateUtils.getSessionFactory().openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Document document = session.get(Document.class, documentId);
            session.delete(document);
            tx.commit();
        } catch (HibernateException e) {
            if (isNull(tx)) tx.rollback();
            e.printStackTrace();
            throw new Exception("Unable to delete the document." + e.getMessage());
        } finally {
            session.close();
        }
        return true;
    }

}

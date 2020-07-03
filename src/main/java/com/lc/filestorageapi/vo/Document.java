package com.lc.filestorageapi.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "document")
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(nullable=false)
    private String name;

    @Lob
    private byte[] data;

    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creation_time_stamp;

    @Column(nullable=false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date last_updated_time_stamp;

    @Column(name = "size")
    private long size;

    public Document() {
    }

    public Document(String name, byte[] data, Date creation_time_stamp, Date last_updated_time_stamp, long size) {
        this.name = name;
        this.data = data;
        this.creation_time_stamp = creation_time_stamp;
        this.last_updated_time_stamp = last_updated_time_stamp;
        this.size = size;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public Date getCreationTimeStamp() {
        return creation_time_stamp;
    }

    public void setCreationTimeStamp(Date creation_time_stamp) {
        this.creation_time_stamp = creation_time_stamp;
    }

    public Date getLastUpdatedTimeStamp() {
        return last_updated_time_stamp;
    }

    public void setLastUpdatedTimeStamp(Date last_updated_time_stamp) {
        this.last_updated_time_stamp = last_updated_time_stamp;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}

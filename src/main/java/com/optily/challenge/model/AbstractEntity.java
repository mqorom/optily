package com.optily.challenge.model;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractEntity {

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created", nullable = false)
    private Date created;

    @PrePersist
    protected void onCreate() {
        created = new Date();
    }

    /**
     * @return {@link #created}
     */
    public Date getCreated() {
        return created;
    }
}

package com.salari.accounting.model.entity;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@SuperBuilder
@MappedSuperclass
public abstract class BaseEntity<T extends Number> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected T id;

    protected Long creationDate=System.currentTimeMillis();

    public BaseEntity() {
    }

    public BaseEntity(Long creationDate) {
        this.creationDate = creationDate;
    }

    public T getId() {
        return id;
    }

    public void setId(T id) {
        this.id = id;
    }

    public Long getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Long creationDate) {
        this.creationDate = creationDate;
    }
}

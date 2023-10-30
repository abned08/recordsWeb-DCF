package com.dcfB.model;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.List;
import java.util.Set;

@Entity
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String recordName;
    private String code;
    private Integer quantity;
    private Integer quantityDemand;
    private String note;



    public Report() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecordName() {
        return recordName;
    }

    public void setRecordName(String recordName) {
        this.recordName = recordName;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getQuantityDemand() {
        return quantityDemand;
    }

    public void setQuantityDemand(Integer quantityDemand) {
        this.quantityDemand = quantityDemand;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}

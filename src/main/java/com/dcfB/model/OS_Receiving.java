package com.dcfB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OS_Receiving {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int receive_num;
    private String receive_date;
    private Integer memo_num;
    private String memo_date;
    private String deliver_date;
    private String deliver_to;
    private String mo_num;
    private String mo_date;
    private String mo_owner;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Services servicesReceiving;

    @JsonIgnore
    @OneToMany(mappedBy = "receiving",cascade = CascadeType.ALL)
    public Set<OS_Records> records;

    public OS_Receiving() {
    }

    public Services getServicesReceiving() {
        return servicesReceiving;
    }

    public void setServicesReceiving(Services servicesReceiving) {
        this.servicesReceiving = servicesReceiving;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReceive_num() {
        return receive_num;
    }

    public void setReceive_num(int receive_num) {
        this.receive_num = receive_num;
    }

    public String getReceive_date() {
        return receive_date;
    }

    public void setReceive_date(String receive_date) {
        this.receive_date = receive_date;
    }

    public Integer getMemo_num() {
        return memo_num;
    }

    public void setMemo_num(Integer memo_num) {
        this.memo_num = memo_num;
    }

    public String getMemo_date() {
        return memo_date;
    }

    public void setMemo_date(String memo_date) {
        this.memo_date = memo_date;
    }

    public String getDeliver_date() {
        return deliver_date;
    }

    public void setDeliver_date(String deliver_date) {
        this.deliver_date = deliver_date;
    }

    public String getDeliver_to() {
        return deliver_to;
    }

    public void setDeliver_to(String deliver_to) {
        this.deliver_to = deliver_to;
    }

    public String getMo_num() {
        return mo_num;
    }

    public void setMo_num(String mo_num) {
        this.mo_num = mo_num;
    }

    public String getMo_date() {
        return mo_date;
    }

    public void setMo_date(String mo_date) {
        this.mo_date = mo_date;
    }

    public String getMo_owner() {
        return mo_owner;
    }

    public void setMo_owner(String mo_owner) {
        this.mo_owner = mo_owner;
    }
}

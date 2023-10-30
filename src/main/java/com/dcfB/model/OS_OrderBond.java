package com.dcfB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OS_OrderBond {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int orderNum;
    private String orderDate;
    private String fileName;
    private String state;


    @JsonIgnore
    @OneToMany(mappedBy = "orderBond",cascade = CascadeType.ALL)
    private Set<OS_ReceivingEnter> receivingEnters;

    @JsonIgnore
    @OneToMany(mappedBy = "orderBond",cascade = CascadeType.ALL)
    private Set<OS_Records> recordsOB;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Services servicesOrderBond;



    public OS_OrderBond() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Services getServicesOrderBond() {
        return servicesOrderBond;
    }

    public void setServicesOrderBond(Services servicesOrderBond) {
        this.servicesOrderBond = servicesOrderBond;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}

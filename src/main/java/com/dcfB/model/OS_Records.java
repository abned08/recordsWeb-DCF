package com.dcfB.model;


import javax.persistence.*;

@Entity
public class OS_Records {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String recorddate;
    private  int recordin;
    private  int recordout;
    private  String invent;
    private Integer count;
    private Integer quantityDemand;
    private String note;
    private Integer orderBondQuantity;



    @ManyToOne (cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OS_RecordName recordName;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Services services;


    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OS_Receiving receiving;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OS_ReceivingEnter receiving_enter;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OS_OrderBond orderBond;



    public OS_Records() {
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRecorddate() {
        return recorddate;
    }

    public void setRecorddate(String recorddate) {
        this.recorddate = recorddate;
    }

    public int getRecordin() {
        return recordin;
    }

    public void setRecordin(int recordin) {
        this.recordin = recordin;
    }

    public int getRecordout() {
        return recordout;
    }

    public void setRecordout(int recordout) {
        this.recordout = recordout;
    }

    public String getInvent() {
        return invent;
    }

    public void setInvent(String invent) {
        this.invent = invent;
    }


    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
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

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getOrderBondQuantity() {
        return orderBondQuantity;
    }

    public void setOrderBondQuantity(Integer orderBondQuantity) {
        this.orderBondQuantity = orderBondQuantity;
    }

    public void setRecordName(OS_RecordName recordName) {
        this.recordName = recordName;
    }

    public void setReceiving(OS_Receiving receiving) {
        this.receiving = receiving;
    }

    public void setReceiving_enter(OS_ReceivingEnter receiving_enter) {
        this.receiving_enter = receiving_enter;
    }

    public void setOrderBond(OS_OrderBond orderBond) {
        this.orderBond = orderBond;
    }

    public OS_RecordName getRecordName() {
        return recordName;
    }

    public OS_Receiving getReceiving() {
        return receiving;
    }

    public OS_ReceivingEnter getReceiving_enter() {
        return receiving_enter;
    }

    public OS_OrderBond getOrderBond() {
        return orderBond;
    }
}

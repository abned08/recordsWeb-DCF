package com.dcfB.model;


import javax.persistence.*;

@Entity
public class Records {

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
    private RecordName recordName;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Services services;


    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Receiving receiving;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private ReceivingEnter receiving_enter;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OrderBond orderBond;



    public Records() {
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

    public RecordName getRecordName() {
        return recordName;
    }

    public void setRecordName(RecordName recordName) {
        this.recordName = recordName;
    }

    public Services getServices() {
        return services;
    }

    public void setServices(Services services) {
        this.services = services;
    }

    public Receiving getReceiving() {
        return receiving;
    }

    public void setReceiving(Receiving receiving) {
        this.receiving = receiving;
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

    public ReceivingEnter getReceiving_enter() {
        return receiving_enter;
    }

    public void setReceiving_enter(ReceivingEnter receiving_enter) {
        this.receiving_enter = receiving_enter;
    }

    public Integer getOrderBondQuantity() {
        return orderBondQuantity;
    }

    public void setOrderBondQuantity(Integer orderBondQuantity) {
        this.orderBondQuantity = orderBondQuantity;
    }

    public OrderBond getOrderBond() {
        return orderBond;
    }

    public void setOrderBond(OrderBond orderBond) {
        this.orderBond = orderBond;
    }
}

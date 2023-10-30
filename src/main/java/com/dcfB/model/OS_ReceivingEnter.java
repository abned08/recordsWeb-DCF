package com.dcfB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Set;

@Entity
public class OS_ReceivingEnter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int receive_enter_num;
    private String receive_enter_date;
    private String deliver_enter_to;
    private String fileName;
    private String filePath;
    private String fileType;
    private String fileSize;



    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private Services servicesReceivingEnter;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    @JoinColumn
    private OS_OrderBond orderBond;


    @JsonIgnore
    @OneToMany(mappedBy = "receiving_enter",cascade = CascadeType.ALL)
    public Set<OS_Records> records;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getReceive_enter_num() {
        return receive_enter_num;
    }

    public void setReceive_enter_num(int receive_enter_num) {
        this.receive_enter_num = receive_enter_num;
    }

    public String getReceive_enter_date() {
        return receive_enter_date;
    }

    public void setReceive_enter_date(String receive_enter_date) {
        this.receive_enter_date = receive_enter_date;
    }


    public String getDeliver_enter_to() {
        return deliver_enter_to;
    }

    public void setDeliver_enter_to(String deliver_enter_to) {
        this.deliver_enter_to = deliver_enter_to;
    }


    public Services getServicesReceivingEnter() {
        return servicesReceivingEnter;
    }

    public void setServicesReceivingEnter(Services servicesReceivingEnter) {
        this.servicesReceivingEnter = servicesReceivingEnter;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }

    public OS_OrderBond getOrderBond() {
        return orderBond;
    }

    public void setOrderBond(OS_OrderBond orderBond) {
        this.orderBond = orderBond;
    }
}

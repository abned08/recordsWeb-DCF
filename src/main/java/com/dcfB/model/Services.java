package com.dcfB.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import java.util.Set;

@Entity
public class Services {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer serviceid;
    private String servicename;

    @JsonIgnore
    @OneToMany(mappedBy = "services",cascade = CascadeType.ALL)
    public Set<Records> records;


    @JsonIgnore
    @OneToMany(mappedBy = "servicesReceiving",cascade = CascadeType.ALL)
    private Set<Receiving> receivings;


    @JsonIgnore
    @OneToMany(mappedBy = "servicesReceivingEnter",cascade = CascadeType.ALL)
    private Set<ReceivingEnter> receivingsEnter;

    @JsonIgnore
    @OneToMany(mappedBy = "servicesOrderBond",cascade = CascadeType.ALL)
    private Set<OrderBond> orderBondsService;


    public Services() {

    }

    public Integer getServiceid() {
        return serviceid;
    }

    public void setServiceid(Integer serviceid) {
        this.serviceid = serviceid;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }


}

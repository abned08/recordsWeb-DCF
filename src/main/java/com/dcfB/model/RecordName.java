package com.dcfB.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Set;



@Entity
public class RecordName {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Id;
	private String code;
	private String recname;
	private String recname_fr;
	private Integer count;
	private Integer safetyStock;


	@JsonIgnore
	@OneToMany(mappedBy = "recordName",cascade = CascadeType.ALL)
	private Set<Records>records;



	public Set<Records> getRecords() {
		return records;
	}

	public void setRecords(Set<Records> records) {
		this.records = records;
	}


	public RecordName() {
		super();
	}


	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public void setSafetyStock(Integer safetyStock) {
		this.safetyStock = safetyStock;
	}

	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}




	public String getRecname() {
		return recname;
	}




	public void setRecname(String recname) {
		this.recname = recname;
	}


	public String getRecname_fr() {
		return recname_fr;
	}

	public void setRecname_fr(String recname_fr) {
		this.recname_fr = recname_fr;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Integer getSafetyStock() {
		return safetyStock;
	}
}

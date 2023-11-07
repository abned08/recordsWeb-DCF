package com.dcfB.model;

import java.util.List;

public class RecordsInit {
    private List<Records> recordsInitList;

    public RecordsInit(List<Records> recordsInitList) {
        this.recordsInitList = recordsInitList;
    }

    public void addRecord(Records record) {

        this.recordsInitList.add(record);
    }




    public List<Records> getRecordsInitList() {
        return recordsInitList;
    }

    public void setRecordsInitList(List<Records> recordsInitList) {
        this.recordsInitList = recordsInitList;
    }
}

package com.dcfB.model;

import java.util.List;

public class OS_RecordsInit {
    private List<OS_Records> recordsInitList;

    public OS_RecordsInit(List<OS_Records> recordsInitList) {
        this.recordsInitList = recordsInitList;
    }

    public void addRecord(OS_Records record) {

        this.recordsInitList.add(record);
    }




    public List<OS_Records> getRecordsInitList() {
        return recordsInitList;
    }

    public void setRecordsInitList(List<OS_Records> recordsInitList) {
        this.recordsInitList = recordsInitList;
    }
}

package com.example.fitnessapp.classes;

public class DatabaseRef {
    private String itemId;
    private String programHeader;
    private String programDescription;

    public DatabaseRef() {
//        Default constructor
    }

    public DatabaseRef(String itemId, String programHeader, String programDescription) {
        this.itemId = itemId;
        this.programHeader = programHeader;
        this.programDescription = programDescription;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getProgramHeader() {
        return programHeader;
    }

    public void setProgramHeader(String programHeader) {
        this.programHeader = programHeader;
    }

    public String getProgramDescription() {
        return programDescription;
    }

    public void setProgramDescription(String programDescription) {
        this.programDescription = programDescription;
    }
}

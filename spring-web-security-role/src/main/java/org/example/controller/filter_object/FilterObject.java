package org.example.controller.filter_object;

public class FilterObject {

    private Long id;
    private String owner;
    private boolean canWrite;

    public FilterObject() {
    }

    public FilterObject(long id, String owner, boolean canWrite) {
        this.id = id;
        this.owner = owner;
        this.canWrite = canWrite;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isCanWrite() {
        return canWrite;
    }

    public void setCanWrite(boolean canWrite) {
        this.canWrite = canWrite;
    }
}
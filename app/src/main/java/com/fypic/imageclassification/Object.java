package com.fypic.imageclassification;

public class Object {
    private String mat;
    private int id;

    public Object(int id, String mat) {
        this.mat = mat;
        this.id = id;
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

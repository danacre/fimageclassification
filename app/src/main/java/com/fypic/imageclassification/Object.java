package com.fypic.imageclassification;

public class Object {
    private int matid;
    private int id;
    private String mat;

    public Object(int id, int matid) {
        this.matid = matid;
        this.mat = mat;
        this.id = id;

        if(matid == 1){
            mat = "Paper";
        }

        if(matid == 2){
            mat = "Metal";
        }

        if(matid == 3){
            mat = "Plastic";
        }

        if(matid == 4){
            mat = "Waste";
        }
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

    public int getMatId() {
        return matid;
    }
}

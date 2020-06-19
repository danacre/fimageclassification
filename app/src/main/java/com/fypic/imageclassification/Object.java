package com.fypic.imageclassification;

import java.time.DateTimeException;
import java.util.Date;

public class Object {
    private int matid;
    private int id;
    private String mat;
    private Date date;


    public Object(int id, int matid) {
        this.matid = matid;
        this.mat = mat;
        this.id = id;
        this.date = date;

        if (matid == 1) {
            mat = "Paper";
        }

        if (matid == 2) {
            mat = "Metal";
        }

        if (matid == 3) {
            mat = "Plastic";
        }

        if (matid == 4) {
            mat = "Waste";
        }
    }

    public String getMat() {
        return mat;
    }

    public void setMat(String mat) {
        this.mat = mat;
    }
}


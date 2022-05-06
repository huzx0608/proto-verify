package com.zetyun.dingo.avo;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Dummy {
    private Date date;
    private SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss.SSS");

    public Dummy() {
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return df.format(date);
    }
}

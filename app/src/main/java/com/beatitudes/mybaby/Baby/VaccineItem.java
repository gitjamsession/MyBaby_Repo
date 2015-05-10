package com.beatitudes.mybaby.Baby;

/**
 * Created by user on 10-05-2015.
 */

import java.text.SimpleDateFormat;
import java.util.Date;


public class VaccineItem {

    String vaccinename;
    Date vaccinedate;

    public String getVaccinename() {
        return vaccinename;
    }

    public Date getVaccinedate() {
        return vaccinedate;
    }

    public VaccineItem(String _vaccine) {
        this(_vaccine, new Date(java.lang.System.currentTimeMillis()));
    }

    public VaccineItem(String _vaccine, Date _date) {
        vaccinename = _vaccine;
        vaccinedate = _date;
    }

    @Override
    public String toString() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yy");
        String dateString = sdf.format(vaccinedate);
        return "(" + dateString + ") " + vaccinename;
    }
}
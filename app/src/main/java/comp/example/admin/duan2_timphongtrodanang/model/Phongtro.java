package comp.example.admin.duan2_timphongtrodanang.model;

import java.io.Serializable;

/**
 * Created by Admin on 10/25/2018.
 */

public class Phongtro implements Serializable{
    public int ID;
    public String Tenphongtro;
    public Integer Giaphongtro;
    public String Hinhanhphongtro;
    public String Motaphongtro;
    public String Diachiphongtro;
    public String Sdtphongtrong;
    public int IDPhongtro;

    public Phongtro(int ID, String tenphongtro, Integer giaphongtro, String hinhanhphongtro, String motaphongtro, String diachiphongtro, String sdtphongtrong, int IDPhongtro) {
        this.ID = ID;
        Tenphongtro = tenphongtro;
        Giaphongtro = giaphongtro;
        Hinhanhphongtro = hinhanhphongtro;
        Motaphongtro = motaphongtro;
        Diachiphongtro = diachiphongtro;
        Sdtphongtrong = sdtphongtrong;
        this.IDPhongtro = IDPhongtro;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTenphongtro() {
        return Tenphongtro;
    }

    public void setTenphongtro(String tenphongtro) {
        Tenphongtro = tenphongtro;
    }

    public Integer getGiaphongtro() {
        return Giaphongtro;
    }

    public void setGiaphongtro(Integer giaphongtro) {
        Giaphongtro = giaphongtro;
    }

    public String getHinhanhphongtro() {
        return Hinhanhphongtro;
    }

    public void setHinhanhphongtro(String hinhanhphongtro) {
        Hinhanhphongtro = hinhanhphongtro;
    }

    public String getMotaphongtro() {
        return Motaphongtro;
    }

    public void setMotaphongtro(String motaphongtro) {
        Motaphongtro = motaphongtro;
    }

    public String getDiachiphongtro() {
        return Diachiphongtro;
    }

    public void setDiachiphongtro(String diachiphongtro) {
        Diachiphongtro = diachiphongtro;
    }

    public String getSdtphongtrong() {
        return Sdtphongtrong;
    }

    public void setSdtphongtrong(String sdtphongtrong) {
        Sdtphongtrong = sdtphongtrong;
    }

    public int getIDPhongtro() {
        return IDPhongtro;
    }

    public void setIDPhongtro(int IDPhongtro) {
        this.IDPhongtro = IDPhongtro;
    }
}

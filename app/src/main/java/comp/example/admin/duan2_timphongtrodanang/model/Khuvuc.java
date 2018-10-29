package comp.example.admin.duan2_timphongtrodanang.model;

/**
 * Created by Admin on 10/24/2018.
 */

public class Khuvuc {
    public int Id;
    public String Tenkhuvuc;
    public String Hinhanhkhuvuc;

    public Khuvuc(int id, String tenkhuvuc, String hinhanhkhuvuc) {
        Id = id;
        Tenkhuvuc = tenkhuvuc;
        Hinhanhkhuvuc = hinhanhkhuvuc;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getTenkhuvuc() {
        return Tenkhuvuc;
    }

    public void setTenkhuvuc(String tenkhuvuc) {
        Tenkhuvuc = tenkhuvuc;
    }

    public String getHinhanhkhuvuc() {
        return Hinhanhkhuvuc;
    }

    public void setHinhanhkhuvuc(String hinhanhkhuvuc) {
        Hinhanhkhuvuc = hinhanhkhuvuc;
    }
}

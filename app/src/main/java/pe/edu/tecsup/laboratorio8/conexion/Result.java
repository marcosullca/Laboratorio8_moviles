package pe.edu.tecsup.laboratorio8.conexion;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Result {
    @SerializedName("geometry")
    public Geometry geometry;
    @SerializedName("name")
    private String name;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Geometry getGeometry() {
        return geometry;
    }

    public void setGeometry(Geometry geometry) {
        this.geometry = geometry;
    }



}


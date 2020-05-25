package pe.edu.tecsup.laboratorio8.conexion;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class ModelData {
    @SerializedName("results")
    private ArrayList<Result> results;
    public ArrayList<Result> getResults() {
        return results;
    }

    public void setResults(ArrayList<Result> results) {
        this.results = results;
    }
}


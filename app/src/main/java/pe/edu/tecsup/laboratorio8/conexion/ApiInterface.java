package pe.edu.tecsup.laboratorio8.conexion;

import java.net.ResponseCache;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import pe.edu.tecsup.laboratorio8.conexion.ModelData;
public interface ApiInterface {
    @GET("nearbysearch/json")
    Call<ModelData> getDatosMarcadores(@Query("location") String location,
                                             @Query("radius") String radius,
                                             @Query("type") String type,
                                             @Query("keyword") String keyword,
                                             @Query("key") String key);

}

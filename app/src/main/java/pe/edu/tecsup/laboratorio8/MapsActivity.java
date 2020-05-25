package pe.edu.tecsup.laboratorio8;

import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import okhttp3.Challenge;
import pe.edu.tecsup.laboratorio8.conexion.ApiInterface;
import pe.edu.tecsup.laboratorio8.conexion.ModelData;
import pe.edu.tecsup.laboratorio8.conexion.Result;
import pe.edu.tecsup.laboratorio8.conexion.ServiceRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final int REQUEST_CODE_ASK_PERMISSIONS = 100;

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        ApiInterface client= ServiceRetrofit.createService(ApiInterface.class);
        Call<ModelData> call=client.getDatosMarcadores("-12.046373,-77.042755","50000","restaurant",
                "polloalabrasa","AIzaSyAn8DpxSG8yU35XhtDeS5R_eMvBI8XXm2g");

        call.enqueue(new Callback<ModelData>() {
            @Override
            public void onResponse(Call<ModelData> call, Response<ModelData> response) {
                for(Result result:response.body().getResults() ){
                    LatLng marcador=new LatLng(
                            result.getGeometry().getLocation().getLat(),
                            result.getGeometry().getLocation().getLng()
                    );

                    String marcadorTitulo=result.getName();
                    mMap.addMarker(new MarkerOptions().position(marcador).title(marcadorTitulo).icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));
                    CameraPosition position  = new CameraPosition.Builder().target(marcador).zoom(17).build();
                    googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
                }


            }
            @Override
            public void onFailure(Call<ModelData> call, Throwable t) {
                Toast.makeText(MapsActivity.this,"GG",Toast.LENGTH_SHORT).show();
                Log.e(this.getClass().getName(),t.getMessage());
            }
        });
//        mMap.addMarker(
//                new MarkerOptions()
//                        .position(locationTecsup)
//                        .title("Tecsup")
//                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.ic_location)));
//        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraTecsup));

        // Muestra los botones de control de zoom de la cámara
        mMap.getUiSettings().setZoomControlsEnabled(true);

        // Permite al usuario cambiar el nivel de zoom mediante arrastre o gestura
        mMap.getUiSettings().setZoomGesturesEnabled(true);

        // Muestra la brújula en el mapa
        mMap.getUiSettings().setCompassEnabled(true);

        // Permite al usuario rotar el mapa mediante arrastre o gestura
        mMap.getUiSettings().setRotateGesturesEnabled(true);

        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            int hasLocationPermission = checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION);

            if (hasLocationPermission != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        REQUEST_CODE_ASK_PERMISSIONS);

            } else if (hasLocationPermission == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            }

        } else {
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if(REQUEST_CODE_ASK_PERMISSIONS == requestCode) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                Toast.makeText(this, "No concedió los permisos :(", Toast.LENGTH_SHORT).show();
            }
        }else{
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

}

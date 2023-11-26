package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa;

import ifsp.spo.edu.vagainclusivafrontend.R;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.geojson.Point;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.IconFactory;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Semaphore;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.os.AsyncTask;

import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaCadastro.TelaCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaFiltros.TelaFiltros;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaInfos.TelaInfos;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaPerfil.TelaPerfil;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.TelaVagaIndividual;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.Vaga;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual.VagaManager;
import ifsp.spo.edu.vagainclusivafrontend.userLogin.UserManager;

@SuppressWarnings({"ALL", "Convert2Lambda"})
public class TelaMapa extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener, MapboxMap.OnMapClickListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private Point originPosition;
    private Point destinationPosition;
    private Marker destinationMarker;
    private boolean isExpanded = true;
    private ImageView arrowButton, closeButton, btn_localizacao;
    private View backgroundView, topView, viewLineOne, viewLineTwo, viewGraySquare, viewGreenSquare, viewYellowSquare, viewRedSquare;
    private Button botaoCadastrar, botaoEntrar;
    private TextView viewLoginCadastro, textNaoAvaliado, textBom, textRuim;
    public boolean ready = false;
    // HashMap para o id
    HashMap<Marker, Integer> markerMap = new HashMap<>();
    VagaManager vagaManager = VagaManager.getInstance();
    private Location lastKnownLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Verificando Controle de Sessão
        // Adicionando Key
        Mapbox.getInstance(this, getString(R.string.access_token));
        // Rodando MapBox
        setContentView(R.layout.tela_mapa);
        mapView = (MapView) findViewById(R.id.mapView);
        getSupportActionBar().hide();
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
        closeButton = findViewById(R.id.closeButton);

        arrowButton = findViewById(R.id.arrowDown);
        backgroundView = findViewById(R.id.backgroundView);
        botaoCadastrar = findViewById(R.id.botaocadastrar);
        botaoEntrar = findViewById(R.id.botaoentrar);
        viewLoginCadastro = findViewById(R.id.viewLoginCadastro);
        backgroundView = findViewById(R.id.backgroundView);
        closeButton = findViewById(R.id.closeButton);
        textNaoAvaliado = findViewById(R.id.textNaoAvaliado);
        textBom = findViewById(R.id.textBom);
        textRuim = findViewById(R.id.textRuim);
        topView = findViewById(R.id.topView);
        viewLineOne = findViewById(R.id.viewLineOne);
        viewLineTwo = findViewById(R.id.viewLineTwo);
        viewGraySquare = findViewById(R.id.viewGraySquare);
        viewGreenSquare = findViewById(R.id.viewGreenSquare);
        viewYellowSquare = findViewById(R.id.viewYellowSquare);
        viewRedSquare = findViewById(R.id.viewRedSquare);

        // Fechar a Janela das Cores
        closeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textNaoAvaliado.setVisibility(View.GONE);
                closeButton.setVisibility(View.GONE);
                textBom.setVisibility(View.GONE);
                textRuim.setVisibility(View.GONE);
                topView.setVisibility(View.GONE);
                viewLineOne.setVisibility(View.GONE);
                viewLineTwo.setVisibility(View.GONE);
                viewGraySquare.setVisibility(View.GONE);
                viewYellowSquare.setVisibility(View.GONE);
                viewGreenSquare.setVisibility(View.GONE);
                viewRedSquare.setVisibility(View.GONE);
            }
        });

        // Funcionalidade para voltar ao Inicio pelo Botão
        btn_localizacao = findViewById(R.id.btn_localizacao);

        btn_localizacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (originLocation != null) {
                    // Verifique se a localização de origem (sua posição atual) está disponível
                    setCameraPosition(originLocation);
                } else {
                    // Caso a localização de origem não esteja disponível
                    Toast.makeText(TelaMapa.this, "Localização não disponível", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Trocar para Tela de Login
        botaoEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaMapa.this, TelaLogin.class);
                startActivity(intent);
            }
        });

        // Trocar para Tela de Cadastro
        botaoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TelaMapa.this, TelaCadastro.class);
                startActivity(intent);
            }
        });

        // Mover os Botões de Cadastro/Login para cima e para Baixo
        arrowButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    // Ocultar a view, os botões e mover a seta para baixo
                    botaoCadastrar.setVisibility(View.GONE);
                    botaoEntrar.setVisibility(View.GONE);
                    arrowButton.setImageResource(R.drawable.icon_arrow_up);

                    // Seta
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) arrowButton.getLayoutParams();
                    int arrowHeight = arrowButton.getHeight();
                    int displacement_arrow = 390;
                    params.bottomMargin = -arrowHeight - displacement_arrow; // Define a margem inferior negativa para posicionar o botão abaixo
                    arrowButton.setLayoutParams(params);

                    // Texto "Faça login ou cadastre-se"
                    ConstraintLayout.LayoutParams textParams = (ConstraintLayout.LayoutParams) viewLoginCadastro.getLayoutParams();
                    int displacement_text = 450;
                    textParams.bottomMargin = -displacement_text;
                    viewLoginCadastro.setLayoutParams(textParams);

                    // Background View
                    ConstraintLayout.LayoutParams backgroundParams = (ConstraintLayout.LayoutParams) backgroundView.getLayoutParams();
                    int displacement_background = 340;
                    backgroundParams.bottomMargin = -displacement_background;
                    backgroundView.setLayoutParams(backgroundParams);
                } else {
                    // Mostrar a view, os botões e mover a seta para cima
                    botaoCadastrar.setVisibility(View.VISIBLE);
                    botaoEntrar.setVisibility(View.VISIBLE);
                    arrowButton.setImageResource(R.drawable.icon_arrow_down);

                    // Seta
                    ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) arrowButton.getLayoutParams();
                    params.bottomMargin = 0; // Define a margem inferior padrão
                    arrowButton.setLayoutParams(params);

                    // Texto "Faça login ou cadastre-se"
                    ConstraintLayout.LayoutParams textParams = (ConstraintLayout.LayoutParams) viewLoginCadastro.getLayoutParams();
                    textParams.bottomMargin = 0; // Define a margem inferior padrão para a TextView
                    viewLoginCadastro.setLayoutParams(textParams);

                    // Background View
                    ConstraintLayout.LayoutParams backgroundParams = (ConstraintLayout.LayoutParams) backgroundView.getLayoutParams();
                    int displacement_background = 0;
                    backgroundParams.bottomMargin = -displacement_background;
                    backgroundView.setLayoutParams(backgroundParams);
                }
                isExpanded = !isExpanded;
            }
        });
    }

    // Metodos das Interfaces
    // AsyncTask Vagas
    private class GetVagasAsyncTask extends AsyncTask<Location, Void, JSONArray> {

        @Override
        protected JSONArray doInBackground(Location... locations) {
            Location location = locations[0];

            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();

            // Android Studio
            //String url = "http://10.0.2.2:8000/vagas/?latitude=" + latitude + "&longitude=" + longitude;
            // Celular Fisico
            String url = "http://18.209.252.205/vagas/?latitude=" + latitude + "&longitude=" + longitude;

            Log.d("API Request URL", url);
            Log.d("Latitude", String.valueOf(latitude));
            Log.d("Longitude", String.valueOf(longitude));

            RequestQueue requestQueue = Volley.newRequestQueue(TelaMapa.this);

            final Semaphore semaphore = new Semaphore(0);

            final JSONArray[] jsonArray = {null};

            // Create the GET request
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            jsonArray[0] = response;
                            semaphore.release();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            semaphore.release();
                        }
                    });

            // Add the request to the RequestQueue
            requestQueue.add(jsonArrayRequest);

            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return jsonArray[0];
        }

        @Override
        protected void onPostExecute(JSONArray response) {
            super.onPostExecute(response);
            Log.e("VOLLEY", "On post response");
            if (response != null) {
                Log.e("VOLLEY", response.toString());
                Log.d("API Response", response.toString());

                try {

                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        double lat = jsonObject.getDouble("latitude");
                        double lng = jsonObject.getDouble("longitude");
                        int id = jsonObject.getInt("index");
                        double media_nota = jsonObject.getDouble("media_notas");
                        JSONArray jsonArray = jsonObject.getJSONArray("acess");
                        List<Integer> acess = new ArrayList<>();

                        UserManager userManager = UserManager.getInstance();

                        //Nota Filter
                        if (userManager.getNota() > 0){
                            if(media_nota < userManager.getNota()) continue;
                        }

                        for (int j = 0; j < jsonArray.length(); j++) {
                            acess.add(jsonArray.getInt(j));
                        }

                        //Acessibilidade Filter
                        List<Integer> filterAcess = userManager.getAcess();
                        if (filterAcess != null && filterAcess.size() > 0){

                            for (Integer member : filterAcess) {
                                if (!acess.contains(member)) {
                                    continue;
                                }
                            }

                        }


                        LatLng vagaLatLng = new LatLng(lat, lng);

                        MarkerOptions markerOptions = new MarkerOptions()
                                .position(vagaLatLng);

                        int map_icon; // Variável para armazenar o recurso de ícone

                        //LOGICA PARA COR DE VAGA

                        if (media_nota >= 1.0 && media_nota <= 2.4) {
                            map_icon = R.drawable.vaga_vermelha;
                        } else if (media_nota >= 2.5 && media_nota <= 3.9) {
                            map_icon = R.drawable.vaga_amarela;
                        } else if (media_nota >= 4.0 && media_nota <= 5.0) {
                            map_icon = R.drawable.vaga_verde;
                        } else {
                            map_icon = R.drawable.vaga_cinza;
                        }

                        /*
                        if (media_nota > 4) {
                            map_icon = R.drawable.icon_exclamation; // Ícone padrão
                        } else {
                            // Outra condição - escolha um ícone diferente aqui
                            map_icon = R.drawable.icon_localization; // Substitua por outro ícone
                        } */

                        IconFactory iconFactory = IconFactory.getInstance(TelaMapa.this);
                        Icon customIcon = iconFactory.fromResource(map_icon);

                        markerOptions.icon(customIcon); // Define o ícone com base na condição

                        Marker marker = map.addMarker(markerOptions);

                        Vaga newVaga = new Vaga(id, vagaLatLng, marker, acess);

                        newVaga.setMediaNotas(media_nota);

                        vagaManager.addVaga(id, newVaga);

                        markerMap.put(marker, id);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                CharSequence text = "Erro";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(TelaMapa.this, text, duration);
                toast.show();
            }
        }
    }


    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        map.addOnMapClickListener(this);
        enableLocation();

        map.setOnMarkerClickListener(new MapboxMap.OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                // Envio das Variaveis
                Intent intent = new Intent(TelaMapa.this, TelaVagaIndividual.class);

                // Variaveis que irão para a Proxima Pagina

                Integer retrievedId = markerMap.get(marker);
                intent.putExtra("id", retrievedId);

                // Vai para a TelaVagaIndividual
                startActivity(intent);

                return true; // Return true to consume the event
            }
        });
        ready = true;
    }

    private void enableLocation(){
        if(PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();

        } else {
            permissionsManager = new PermissionsManager((this));
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine(){
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.LOW_POWER);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if(lastLocation != null){
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(this);

        }

    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationLayer(){
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location){
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 15.5));
    }
    @SuppressWarnings("MissingPermission")

    @Override
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            if (lastKnownLocation == null || location.distanceTo(lastKnownLocation) >= 200) {
                // Location has changed by at least 200 meters
                originLocation = location;
                setCameraPosition(location);
                TelaMapa.GetVagasAsyncTask asyncTask = new TelaMapa.GetVagasAsyncTask();
                asyncTask.execute(originLocation);

                // Update lastKnownLocation
                lastKnownLocation = location;
            }
        }
    }

    @SuppressWarnings("MissingPermission")
    // Fazendo Mapbox Rodar em todos os estado possiveis
    @Override
    protected void onStart() {
        super.onStart();
        if(locationEngine != null){
            locationEngine.requestLocationUpdates();
        }
        //if(locationLayerPlugin != null){
        //    locationLayerPlugin.onStart();
        //}
        mapView.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(locationEngine != null){
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null){
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (locationEngine != null) {
            locationEngine.deactivate();
        }
        mapView.onDestroy();
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {

        //Apresentar balão mostrando que o usuário não deu permissão
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
        initializeLocationEngine();
        initializeLocationLayer();
    }

    @Override
    public void onPermissionResult(boolean granted) {
        Log.e("MAP", "getting permissions" );

        if (granted) {
            Log.e("MAP", "permissions granted" );

            initializeLocationEngine();
            initializeLocationLayer();
        }

    }
    @Override
    public void onMapClick(@NonNull LatLng point) {
        if(destinationMarker != null){
            map.removeMarker(destinationMarker);
        }
        if(!ready){
            Log.e("MAP", "Click" );

            onMapReady(map);
        }
    }

    public void onImageFilterClick(View view) {
        Intent intent = new Intent(this, TelaFiltros.class);
        startActivity(intent);
        finish();
    }

    public void onImageInfoClick(View view) {
        Intent intent = new Intent(this, TelaInfos.class);
        startActivity(intent);
        finish();
    }

    public void onImagePersonClick(View view) {
        Intent intent = new Intent(this, TelaPerfil.class);
        startActivity(intent);
        finish();
    }

    public void onImageStartClick(View view) {

    }

}
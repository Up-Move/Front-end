package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaMapa;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import ifsp.spo.edu.vagainclusivafrontend.R;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaCadastro.TelaCadastro;
import ifsp.spo.edu.vagainclusivafrontend.userInterface.telaLogin.TelaLogin;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.location.Location;

import com.mapbox.android.core.location.LocationEngine;
import com.mapbox.android.core.location.LocationEngineListener;
import com.mapbox.android.core.location.LocationEnginePriority;
import com.mapbox.android.core.location.LocationEngineProvider;
import com.mapbox.android.core.permissions.PermissionsListener;
import com.mapbox.android.core.permissions.PermissionsManager;
import com.mapbox.android.telemetry.TelemetryEnabler;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;

import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.plugins.locationlayer.LocationLayerPlugin;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.CameraMode;
import com.mapbox.mapboxsdk.plugins.locationlayer.modes.RenderMode;

import java.util.List;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

@SuppressWarnings({"Convert2Lambda", "unused", "SpellCheckingInspection"})
public class TelaMapa extends AppCompatActivity implements OnMapReadyCallback, LocationEngineListener, PermissionsListener {
    private MapView mapView;
    private MapboxMap map;
    private PermissionsManager permissionsManager;
    private LocationEngine locationEngine;
    private LocationLayerPlugin locationLayerPlugin;
    private Location originLocation;
    private boolean isExpanded = true;
    private ImageView arrowButton, closeButton;
    private View backgroundView, topView, viewLineOne, viewLineTwo, viewGraySquare, viewGreenSquare, viewYellowSquare, viewRedSquare;
    private Button botaoCadastrar, botaoEntrar;
    private TextView viewLoginCadastro, textNaoAvaliado, textBom, textRuim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Adicionando Key
        Mapbox.getInstance(this, getString(R.string.access_token));
        // Rodando MapBox
        setContentView(R.layout.tela_mapa);
        mapView = findViewById(R.id.mapView);
        if(getSupportActionBar() != null){
            getSupportActionBar().hide();
        }

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

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
    @Override
    public void onMapReady(MapboxMap mapboxMap) {
        map = mapboxMap;
        enableLocation();
    }

    private void enableLocation(){
        if (PermissionsManager.areLocationPermissionsGranted(this)) {
            initializeLocationEngine();
            initializeLocationLayer();
        } else {
            permissionsManager = new PermissionsManager(this);
            permissionsManager.requestLocationPermissions(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationEngine() {
        locationEngine = new LocationEngineProvider(this).obtainBestLocationEngineAvailable();
        locationEngine.setPriority(LocationEnginePriority.HIGH_ACCURACY);
        locationEngine.activate();

        Location lastLocation = locationEngine.getLastLocation();
        if (lastLocation != null) {
            originLocation = lastLocation;
            setCameraPosition(lastLocation);
        } else {
            locationEngine.addLocationEngineListener(this);
        }
    }

    @SuppressWarnings("MissingPermission")
    private void initializeLocationLayer() {
        locationLayerPlugin = new LocationLayerPlugin(mapView, map, locationEngine);
        locationLayerPlugin.setLocationLayerEnabled(true);
        locationLayerPlugin.setCameraMode(CameraMode.TRACKING);
        locationLayerPlugin.setRenderMode(RenderMode.NORMAL);
    }

    private void setCameraPosition(Location location) {
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(location.getLatitude(),
                location.getLongitude()), 13.0));
    }

    @Override
    @SuppressWarnings("MissingPermission")
    public void onConnected() {
        locationEngine.requestLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location != null) {
            originLocation = location;
            setCameraPosition(location);
        }
    }

    @Override
    public void onExplanationNeeded(List<String> permissionsToExplain) {
        // Aqui que falamos porque precisamos de acesso
    }

    @Override
    public void onPermissionResult(boolean granted) {
        if (granted) {
            enableLocation();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionsManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    // Fazendo Mapbox Rodar em todos os estado possiveis
    @Override
    @SuppressWarnings("MissingPermission")
    protected void onStart() {
        super.onStart();
        if (locationEngine != null) {
            locationEngine.requestLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStart();
        }
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
        if (locationEngine != null) {
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStop();
        }
        mapView.onStop();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
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
            locationEngine.removeLocationUpdates();
        }
        if (locationLayerPlugin != null) {
            locationLayerPlugin.onStop();
        }
        mapView.onDestroy();
    }
}
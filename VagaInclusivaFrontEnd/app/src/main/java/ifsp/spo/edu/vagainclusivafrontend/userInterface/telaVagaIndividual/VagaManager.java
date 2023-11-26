package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import com.mapbox.mapboxsdk.annotations.Marker;

import java.util.HashMap;
import java.util.Map;

public class VagaManager {
    private static VagaManager instance;
    private HashMap<Integer, Vaga> vagaMap;

    private VagaManager() {
        vagaMap = new HashMap<>();
    }

    public static VagaManager getInstance() {
        if (instance == null) {
            instance = new VagaManager();
        }
        return instance;
    }

    public void addVaga(int id, Vaga vaga) {
        vagaMap.put(id, vaga);
    }

    public Vaga getVagaById(int id) {
        return vagaMap.get(id);
    }

    public Vaga getVagaByMarker(Marker marker) {
        for (Map.Entry<Integer, Vaga> entry : vagaMap.entrySet()) {
            Vaga vaga = entry.getValue();
            if (vaga.getMarker().equals(marker)) {
                return vaga;
            }
        }
        return null; // No Vaga found for the provided Marker.
    }

}

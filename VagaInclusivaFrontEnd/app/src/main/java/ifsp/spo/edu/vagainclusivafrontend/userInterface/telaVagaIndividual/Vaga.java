package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import com.google.gson.annotations.Expose;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.geometry.LatLng;

import java.util.List;

@SuppressWarnings("NullableProblems")
public class Vaga {
    @Expose
    private int id;
    @Expose
    private VagaDetails vaga_details;
    @Expose
    private int total_avaliacoes;

    @Expose
    private Double media_notas;

    private int vaga;

    private LatLng geometry;

    private Marker marker;

    private List<Integer> acess;

    public Vaga(int id, LatLng latlng, Marker marker, List<Integer> acess){
        this.id = id;
        this.geometry = latlng;
        this.marker = marker;
        this.acess = acess;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public VagaDetails getVagaDetails() {
        return vaga_details;
    }

    public void setVagaDetails(VagaDetails vagaDetails) {
        this.vaga_details = vagaDetails;
    }

    public int getTotalAvaliacoes() {
        return total_avaliacoes;
    }

    public void setTotalAvaliacoes(int totalAvaliacoes) {
        this.total_avaliacoes = totalAvaliacoes;
    }

    public Double getMediaNotas() {
        return media_notas;
    }

    public void setMediaNotas(Double mediaNotas) {
        this.media_notas = mediaNotas;
    }

    public int getVaga() {
        return vaga;
    }

    public void setVaga(int vaga) {
        this.vaga = vaga;
    }

    @Override
    public String toString() {
        return "Vaga{" +
                "id=" + id +
                ", vaga_details=" + vaga_details +
                ", total_avaliacoes=" + total_avaliacoes +
                ", media_notas='" + media_notas + '\'' +
                ", vaga=" + vaga +
                '}';
    }

    public Marker getMarker() {
        return marker;
    }

    public void setMarker(Marker marker){
        this.marker = marker;
    }

    public List<Integer> getAcess() {
        return acess;
    }

    // Setter for the 'access' field
    public void setAccess(List<Integer> acess) {
        this.acess = acess;
    }
}


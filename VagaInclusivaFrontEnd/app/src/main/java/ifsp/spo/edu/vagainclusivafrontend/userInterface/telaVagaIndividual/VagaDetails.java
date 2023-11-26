package ifsp.spo.edu.vagainclusivafrontend.userInterface.telaVagaIndividual;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class VagaDetails {
    @Expose
    @SerializedName("local")
    private String local;

    @Expose
    @SerializedName("complemento")
    private String complemento;

    @Expose
    @SerializedName("quantidadev")
    private int quantidadev;

    @Expose
    @SerializedName("area")
    private String area;

    @Expose
    @SerializedName("coordenadas")
    private String coordenadas;

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public int getQuantidadev() {
        return quantidadev;
    }

    public void setQuantidadev(int quantidadev) {
        this.quantidadev = quantidadev;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCoordenadas() {
        return coordenadas;
    }

    public void setCoordenadas(String coordenadas) {
        this.coordenadas = area;
    }
}

package aat.com.usuario;

public class objServicio {

    private String servicio;
    private Integer tiempo;
    private Double costo;

    public objServicio() {
    }

    public objServicio(String servicio, Integer tiempo, Double costo) {
        this.servicio = servicio;
        this.tiempo = tiempo;
        this.costo = costo;
    }


    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public Integer getTiempo() {
        return tiempo;
    }

    public void setTiempo(Integer tiempo) {
        this.tiempo = tiempo;
    }

    public Double getCosto() {
        return costo;
    }

    public void setCosto(Double costo) {
        this.costo = costo;
    }
}

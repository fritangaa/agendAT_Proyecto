package aat.com.usuario;

public class objCita {
    private Integer estado;
    private String fecha;
    private String hora;
    private String lugar;
   

    public objCita() {
    }

    public objCita( Integer estado, String lugar, String fecha, String hora) {
        this.estado = estado;
        this.lugar = lugar;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

}

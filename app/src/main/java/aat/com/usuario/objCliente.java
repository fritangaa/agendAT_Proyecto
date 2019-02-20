package aat.com.usuario;

/**
 * Created by maxim on 13/08/2017.
 */

public class objCliente {

    private String correo;
    private String direccion;
    private String nombre;
    private String password;
    private String telefono;
    private String valoracion;

    public objCliente() {
    }

    public objCliente(String correo, String direccion, String nombre, String password, String telefono, String valoracion) {
        this.correo = correo;
        this.direccion = direccion;
        this.nombre = nombre;
        this.password = password;
        this.telefono = telefono;
        this.valoracion = valoracion;
    }


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }




}

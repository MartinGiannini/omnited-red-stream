package coop.bancocredicoop.omnited.entity;

import java.util.Set;

public class Operador {
    
    private int idUsuario;
    private String usuarioNombre;
    private String usuarioApellido;
    private String usuarioUsuario;
    private String usuarioCorreo;
    private Set<String> usuarioHabilidad;
    private Set<String> usuarioEstado;
    private Set<String> usuarioPermisoOperacion;
    private int idSector;
    private int idEstado;

    public Operador() {
    }

    public Operador(int idUsuario, String usuarioNombre, String usuarioApellido, String usuarioUsuario, String usuarioCorreo, Set<String> usuarioHabilidad, Set<String> usuarioEstado, Set<String> usuarioPermisoOperacion, int idSector, int idEstado) {
        this.idUsuario = idUsuario;
        this.usuarioNombre = usuarioNombre;
        this.usuarioApellido = usuarioApellido;
        this.usuarioUsuario = usuarioUsuario;
        this.usuarioCorreo = usuarioCorreo;
        this.usuarioHabilidad = usuarioHabilidad;
        this.usuarioEstado = usuarioEstado;
        this.usuarioPermisoOperacion = usuarioPermisoOperacion;
        this.idSector = idSector;
        this.idEstado = idEstado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuarioNombre() {
        return usuarioNombre;
    }

    public void setUsuarioNombre(String usuarioNombre) {
        this.usuarioNombre = usuarioNombre;
    }

    public String getUsuarioApellido() {
        return usuarioApellido;
    }

    public void setUsuarioApellido(String usuarioApellido) {
        this.usuarioApellido = usuarioApellido;
    }

    public String getUsuarioUsuario() {
        return usuarioUsuario;
    }

    public void setUsuarioUsuario(String usuarioUsuario) {
        this.usuarioUsuario = usuarioUsuario;
    }

    public String getUsuarioCorreo() {
        return usuarioCorreo;
    }

    public void setUsuarioCorreo(String usuarioCorreo) {
        this.usuarioCorreo = usuarioCorreo;
    }

    public Set<String> getUsuarioHabilidad() {
        return usuarioHabilidad;
    }

    public void setUsuarioHabilidad(Set<String> usuarioHabilidad) {
        this.usuarioHabilidad = usuarioHabilidad;
    }

    public Set<String> getUsuarioEstado() {
        return usuarioEstado;
    }

    public void setUsuarioEstado(Set<String> usuarioEstado) {
        this.usuarioEstado = usuarioEstado;
    }

    public Set<String> getUsuarioPermisoOperacion() {
        return usuarioPermisoOperacion;
    }

    public void setUsuarioPermisoOperacion(Set<String> usuarioPermisoOperacion) {
        this.usuarioPermisoOperacion = usuarioPermisoOperacion;
    }

    public int getIdSector() {
        return idSector;
    }

    public void setIdSector(int idSector) {
        this.idSector = idSector;
    }

    public int getIdEstado() {
        return idEstado;
    }

    public void setIdEstado(int idEstado) {
        this.idEstado = idEstado;
    }

    
}
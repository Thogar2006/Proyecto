/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 *
 * @author bossstore
 */

public class CitaDTO implements Serializable {


    private int id;
    private MascotaDTO mascota;
    private PropietarioDTO propietario;
    private VeterinarioDTO veterinario;
    private LocalDateTime fechaHora;
    private String motivo;

    public CitaDTO(int id, MascotaDTO mascota, PropietarioDTO propietario, VeterinarioDTO veterinario, LocalDateTime fechaHora, String motivo) {
        this.id = id;
        this.mascota = mascota;
        this.propietario = propietario;
        this.veterinario = veterinario;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
    }

    public int getId() {
        return id; 
    }
    
    public MascotaDTO getMascota() {
        return mascota; 
    }
    
    public PropietarioDTO getPropietario() {
        return propietario; 
    }
    
    public VeterinarioDTO getVeterinario() {
        return veterinario; 
    }
    
    public LocalDateTime getFechaHora() {
        return fechaHora; 
    }
    
    public String getMotivo() {
        return motivo; 
    }

    public void setId(int id) {
        this.id = id; 
    }
    
    public void setMascota(MascotaDTO mascota) {
        this.mascota = mascota; 
    }
    
    public void setPropietario(PropietarioDTO propietario) {
        this.propietario = propietario; 
    }
    
    public void setVeterinario(VeterinarioDTO veterinario) {
        this.veterinario = veterinario; 
    }
    
    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora; 
    }
    
    public void setMotivo(String motivo) {
        this.motivo = motivo; 
    }
    
}
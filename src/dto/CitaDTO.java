/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDateTime;

/**
 *
 * @author bossstore
 */
public class CitaDTO {
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

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public MascotaDTO getMascota() { return mascota; }
    public void setMascota(MascotaDTO mascota) { this.mascota = mascota; }

    public PropietarioDTO getPropietario() { return propietario; }
    public void setPropietario(PropietarioDTO propietario) { this.propietario = propietario; }

    public VeterinarioDTO getVeterinario() { return veterinario; }
    public void setVeterinario(VeterinarioDTO veterinario) { this.veterinario = veterinario; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }
}
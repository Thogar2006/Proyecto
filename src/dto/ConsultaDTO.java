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
public class ConsultaDTO {
    private int id;
    private MascotaDTO mascota;
    private PropietarioDTO propietario;
    private VeterinarioDTO veterinario;
    private LocalDateTime fechaHora;
    private String diagnostico;
    private String tratamiento;

    public ConsultaDTO(int id, MascotaDTO mascota, PropietarioDTO propietario, VeterinarioDTO veterinario,
                       LocalDateTime fechaHora, String diagnostico, String tratamiento) {
        this.id = id;
        this.mascota = mascota;
        this.propietario = propietario;
        this.veterinario = veterinario;
        this.fechaHora = fechaHora;
        this.diagnostico = diagnostico;
        this.tratamiento = tratamiento;
    }

    // Getters y Setters
    public int getId() { return id; }
    public MascotaDTO getMascota() { return mascota; }
    public PropietarioDTO getPropietario() { return propietario; }
    public VeterinarioDTO getVeterinario() { return veterinario; }
    public LocalDateTime getFechaHora() { return fechaHora; }
    public String getDiagnostico() { return diagnostico; }
    public String getTratamiento() { return tratamiento; }

    public void setId(int id) { this.id = id; }
    public void setMascota(MascotaDTO mascota) { this.mascota = mascota; }
    public void setPropietario(PropietarioDTO propietario) { this.propietario = propietario; }
    public void setVeterinario(VeterinarioDTO veterinario) { this.veterinario = veterinario; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }
    public void setTratamiento(String tratamiento) { this.tratamiento = tratamiento; }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.time.LocalDate;

/**
 *
 * @author bossstore
 */
public class VacunaDTO {
    private int id;
    private MascotaDTO mascota;
    private String tipo;
    private String dosis;
    private LocalDate fechaAplicacion;

    public VacunaDTO(int id, MascotaDTO mascota, String tipo, String dosis, LocalDate fechaAplicacion) {
        this.id = id;
        this.mascota = mascota;
        this.tipo = tipo;
        this.dosis = dosis;
        this.fechaAplicacion = fechaAplicacion;
    }

    // Getters y Setters
    public int getId() { return id; }
    public MascotaDTO getMascota() { return mascota; }
    public String getTipo() { return tipo; }
    public String getDosis() { return dosis; }
    public LocalDate getFechaAplicacion() { return fechaAplicacion; }

    public void setId(int id) { this.id = id; }
    public void setMascota(MascotaDTO mascota) { this.mascota = mascota; }
    public void setTipo(String tipo) { this.tipo = tipo; }
    public void setDosis(String dosis) { this.dosis = dosis; }
    public void setFechaAplicacion(LocalDate fechaAplicacion) { this.fechaAplicacion = fechaAplicacion; }
}
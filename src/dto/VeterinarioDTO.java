/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

/**
 *
 * @author bossstore
 */
public class VeterinarioDTO extends PersonaDTO {
    private String especialidad;

    public VeterinarioDTO(int id, String nombre, String documento, String telefono, String correo, String especialidad) {
        super(id, nombre, documento, telefono, correo);
        this.especialidad = especialidad;
    }

    public String getEspecialidad() { return especialidad; }
    public void setEspecialidad(String especialidad) { this.especialidad = especialidad; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}
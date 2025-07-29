/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dto;

import java.io.Serializable;

/**
 *
 * @author bossstore
 */
public class PropietarioDTO extends PersonaDTO {

    public PropietarioDTO(int id, String nombre, String documento, String telefono, String correo) {
        super(id, nombre, documento, telefono, correo);
    }

    @Override
    public String toString() {
        return nombre + " (" + documento + ")";
    }
}

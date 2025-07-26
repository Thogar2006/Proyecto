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
public class MascotaDTO {
    private int id;
    private String nombre;
    private String especie;
    private String raza;
    private int edad;
    private PropietarioDTO propietario;

    public MascotaDTO(int id, String nombre, String especie, String raza, int edad, PropietarioDTO propietario) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.propietario = propietario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaza() { return raza; }
    public void setRaza(String raza) { this.raza = raza; }

    public int getEdad() { return edad; }
    public void setEdad(int edad) { this.edad = edad; }

    public PropietarioDTO getPropietario() { return propietario; }
    public void setPropietario(PropietarioDTO propietario) { this.propietario = propietario; }

    @Override
    public String toString() {
        return nombre + " (ID: " + id + ")";
    }
}
    
    
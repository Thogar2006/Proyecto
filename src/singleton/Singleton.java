/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package singleton;

import controlador.CitaControlador;
import controlador.ConsultaControlador;
import controlador.MascotaControlador;
import controlador.PropietarioControlador;
import controlador.VacunaControlador;
import controlador.VeterinarioControlador;
import dao.CitaDAO;
import dao.ConsultaDAO;
import dao.MascotaDAO;
import dao.PropietarioDAO;
import dao.VacunaDAO;
import dao.VeterinarioDAO;

/**
 *
 * @author bossstore
 */
public class Singleton {

    private static Singleton instancia;

    // DAOs
    private final PropietarioDAO propietarioDAO;
    private final VeterinarioDAO veterinarioDAO;
    private final MascotaDAO mascotaDAO;
    private final CitaDAO citaDAO;
    private final ConsultaDAO consultaDAO;
    private final VacunaDAO vacunaDAO;

    // Controladores
    private final PropietarioControlador propietarioControlador;
    private final VeterinarioControlador veterinarioControlador;
    private final MascotaControlador mascotaControlador;
    private final CitaControlador citaControlador;
    private final ConsultaControlador consultaControlador;
    private final VacunaControlador vacunaControlador;

    private Singleton() {
        // Instanciar DAOs
        propietarioDAO = new PropietarioDAO();
        veterinarioDAO = new VeterinarioDAO();
        mascotaDAO = new MascotaDAO();
        citaDAO = new CitaDAO();
        consultaDAO = new ConsultaDAO();
        vacunaDAO = new VacunaDAO();

        // Instanciar controladores
        propietarioControlador = new PropietarioControlador(propietarioDAO);
        veterinarioControlador = new VeterinarioControlador(veterinarioDAO);
        mascotaControlador = new MascotaControlador(mascotaDAO);
        citaControlador = new CitaControlador(citaDAO);
        consultaControlador = new ConsultaControlador(consultaDAO);
        vacunaControlador = new VacunaControlador(vacunaDAO);
        // Puedes continuar aquí con los demás controladores cuando los tengamos
    }

    public static Singleton getInstance() {
        if (instancia == null) {
            instancia = new Singleton();
        }
        return instancia;
    }
    
    

    // Métodos para acceder a los DAOs
    public PropietarioDAO getPropietarioDAO() {
        return propietarioDAO; 
    }
    
    public VeterinarioDAO getVeterinarioDAO() {
        return veterinarioDAO; 
    }
    
    public MascotaDAO getMascotaDAO() {
        return mascotaDAO; 
    }
    
    public CitaDAO getCitaDAO() {
        return citaDAO; 
    }
    
    public ConsultaDAO getConsultaDAO() {
        return consultaDAO; 
    }
    
    public VacunaDAO getVacunaDAO() {
        return vacunaDAO; 
    }

    // Métodos para acceder a los controladores
    public PropietarioControlador getPropietarioControlador() {
        return propietarioControlador; 
    }
    
    public VeterinarioControlador getVeterinarioControlador() {
        return veterinarioControlador;
    }
    
    public MascotaControlador getMascotaControlador() {
        return mascotaControlador;
    }
    
    public CitaControlador getCitaControlador() {
        return citaControlador;
    }
    
    public ConsultaControlador getConsultaControlador() {
        return consultaControlador;
    }
    
    public VacunaControlador getVacunaControlador() {
        return vacunaControlador;
    }
    // Agrega getters para otros controladores según los implementemos
}

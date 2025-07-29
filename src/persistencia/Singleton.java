/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistencia;

import controlador.*;
import dao.*;
import dto.*;

import java.io.IOException;
import java.util.List;

public class Singleton {

    private static Singleton instancia;

    private final PropietarioDAO propietarioDAO;
    private final VeterinarioDAO veterinarioDAO;
    private final MascotaDAO mascotaDAO;
    private final CitaDAO citaDAO;
    private final ConsultaDAO consultaDAO;
    private final VacunaDAO vacunaDAO; 

    private final PropietarioControlador propietarioControlador;
    private final VeterinarioControlador veterinarioControlador;
    private final MascotaControlador mascotaControlador;
    private final CitaControlador citaControlador;
    private final ConsultaControlador consultaControlador;
    private final VacunaControlador vacunaControlador; 

    private final String ARCHIVO_PROPIETARIOS = "propietarios.dat";
    private final String ARCHIVO_VETERINARIOS = "veterinarios.dat";
    private final String ARCHIVO_MASCOTAS = "mascotas.dat";
    private final String ARCHIVO_CITAS = "citas.dat";
    private final String ARCHIVO_CONSULTAS = "consultas.dat";
    private final String ARCHIVO_VACUNAS = "vacunas.dat"; 

    private Singleton() {
        this.propietarioDAO = new PropietarioDAO();
        this.veterinarioDAO = new VeterinarioDAO();
        this.mascotaDAO = new MascotaDAO();
        this.citaDAO = new CitaDAO();
        this.consultaDAO = new ConsultaDAO();
        this.vacunaDAO = new VacunaDAO(); 

        this.propietarioControlador = new PropietarioControlador(propietarioDAO);
        this.veterinarioControlador = new VeterinarioControlador(veterinarioDAO);
        this.mascotaControlador = new MascotaControlador(mascotaDAO);
        this.citaControlador = new CitaControlador(citaDAO);
        this.consultaControlador = new ConsultaControlador(consultaDAO);
        this.vacunaControlador = new VacunaControlador(vacunaDAO); 

        cargarDatos();
    }

    public static Singleton getInstancia() {
        if (instancia == null) {
            instancia = new Singleton();
        }
        return instancia;
    }

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

    public void guardarDatos() {
        try {
            Serializador.guardar(ARCHIVO_PROPIETARIOS, propietarioDAO.obtenerTodos());
            Serializador.guardar(ARCHIVO_VETERINARIOS, veterinarioDAO.obtenerTodos());
            Serializador.guardar(ARCHIVO_MASCOTAS, mascotaDAO.obtenerTodos());
            Serializador.guardar(ARCHIVO_CITAS, citaDAO.obtenerTodos());
            Serializador.guardar(ARCHIVO_CONSULTAS, consultaDAO.obtenerTodos());
            Serializador.guardar(ARCHIVO_VACUNAS, vacunaDAO.obtenerTodos()); 
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void cargarDatos() {
        try {
            List<PropietarioDTO> listaP = Serializador.cargar(ARCHIVO_PROPIETARIOS);
            if (listaP != null) propietarioDAO.setLista(listaP);

            List<VeterinarioDTO> listaV = Serializador.cargar(ARCHIVO_VETERINARIOS);
            if (listaV != null) veterinarioDAO.setLista(listaV);

            List<MascotaDTO> listaM = Serializador.cargar(ARCHIVO_MASCOTAS);
            if (listaM != null) mascotaDAO.setLista(listaM);

            List<CitaDTO> listaC = Serializador.cargar(ARCHIVO_CITAS);
            if (listaC != null) citaDAO.setLista(listaC);

            List<ConsultaDTO> listaCon = Serializador.cargar(ARCHIVO_CONSULTAS);
            if (listaCon != null) consultaDAO.setLista(listaCon);

            List<VacunaDTO> listaVac = Serializador.cargar(ARCHIVO_VACUNAS);
            if (listaVac != null) vacunaDAO.setLista(listaVac); 

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

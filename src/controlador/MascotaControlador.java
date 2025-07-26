/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.MascotaDAO;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.List;



/**
 *
 * @author bossstore
 */
public class MascotaControlador {
    private final MascotaDAO mascotaDAO;

    public MascotaControlador(MascotaDAO mascotaDAO) {
        this.mascotaDAO = mascotaDAO;
    }

    // Agregar
    public void agregarMascota(int id, String nombre, String especie, String raza, int edad, PropietarioDTO propietario)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(nombre, especie, raza, edad, propietario);

        MascotaDTO nueva = new MascotaDTO(id, nombre, especie, raza, edad, propietario);
        mascotaDAO.agregar(nueva);
    }

    // Buscar por ID
    public MascotaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return mascotaDAO.buscarPorId(id);
    }

    // Buscar por nombre
    public List<MascotaDTO> buscarPorNombre(String nombre) {
        return mascotaDAO.buscarPorNombre(nombre);
    }

    // Actualizar
    public void actualizarMascota(int id, String nombre, String especie, String raza, int edad, PropietarioDTO propietario)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(nombre, especie, raza, edad, propietario);

        MascotaDTO actualizada = new MascotaDTO(id, nombre, especie, raza, edad, propietario);
        mascotaDAO.actualizar(actualizada);
    }

    // Eliminar
    public void eliminarMascota(int id) throws EntidadNoEncontradaException {
        mascotaDAO.eliminar(id);
    }

    // Obtener todas
    public List<MascotaDTO> obtenerTodas() {
        return mascotaDAO.obtenerTodas();
    }

    // Validar campos
    private void validarCampos(String nombre, String especie, String raza, int edad, PropietarioDTO propietario)
            throws CampoVacioException {
        if (nombre.isBlank() || especie.isBlank() || raza.isBlank() || propietario == null || edad < 0) {
            throw new CampoVacioException("Todos los campos deben estar completos y válidos.");
        }
    }
}
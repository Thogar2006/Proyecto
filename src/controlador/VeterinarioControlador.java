/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.VeterinarioDAO;
import dto.VeterinarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.List;
/**
 *
 * @author bossstore
 */
public class VeterinarioControlador {
    private final VeterinarioDAO veterinarioDAO;

    public VeterinarioControlador(VeterinarioDAO veterinarioDAO) {
        this.veterinarioDAO = veterinarioDAO;
    }

    // Agregar veterinario
    public void agregarVeterinario(int id, String nombre, String documento, String telefono, String correo, String especialidad)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(nombre, documento, telefono, correo, especialidad);

        VeterinarioDTO nuevo = new VeterinarioDTO(id, nombre, documento, telefono, correo, especialidad);
        veterinarioDAO.agregar(nuevo);
    }

    // Buscar por ID
    public VeterinarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return veterinarioDAO.buscarPorId(id);
    }

    // Buscar por documento
    public VeterinarioDTO buscarPorDocumento(String documento) throws EntidadNoEncontradaException {
        return veterinarioDAO.buscarPorDocumento(documento);
    }

    // Actualizar veterinario
    public void actualizarVeterinario(int id, String nombre, String documento, String telefono, String correo, String especialidad)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(nombre, documento, telefono, correo, especialidad);

        VeterinarioDTO actualizado = new VeterinarioDTO(id, nombre, documento, telefono, correo, especialidad);
        veterinarioDAO.actualizar(actualizado);
    }

    // Eliminar
    public void eliminarVeterinario(int id) throws EntidadNoEncontradaException {
        veterinarioDAO.eliminar(id);
    }

    // Obtener todos
    public List<VeterinarioDTO> obtenerTodos() {
        return veterinarioDAO.obtenerTodos();
    }

    // Validar campos
    private void validarCampos(String nombre, String documento, String telefono, String correo, String especialidad)
            throws CampoVacioException {
        if (nombre.isBlank() || documento.isBlank() || telefono.isBlank() || correo.isBlank() || especialidad.isBlank()) {
            throw new CampoVacioException("Todos los campos deben estar completos.");
        }
    }
}
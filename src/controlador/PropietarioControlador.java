/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.PropietarioDAO;
import dto.PropietarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.List;


/**
 *
 * @author bossstore
 */
public class PropietarioControlador {
    private final PropietarioDAO propietarioDAO;

    public PropietarioControlador(PropietarioDAO propietarioDAO) {
        this.propietarioDAO = propietarioDAO;
    }

    // Agregar un nuevo propietario
    public void agregarPropietario(int id, String nombre, String documento, String telefono, String correo)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(nombre, documento, telefono, correo);

        PropietarioDTO nuevo = new PropietarioDTO(id, nombre, documento, telefono, correo);
        propietarioDAO.agregar(nuevo);
    }

    // Buscar propietario por ID
    public PropietarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return propietarioDAO.buscarPorId(id);
    }

    // Buscar propietario por documento
    public PropietarioDTO buscarPorDocumento(String documento) throws EntidadNoEncontradaException {
        return propietarioDAO.buscarPorDocumento(documento);
    }

    // Actualizar un propietario existente
    public void actualizarPropietario(int id, String nombre, String documento, String telefono, String correo)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(nombre, documento, telefono, correo);

        PropietarioDTO actualizado = new PropietarioDTO(id, nombre, documento, telefono, correo);
        propietarioDAO.actualizar(actualizado);
    }

    // Eliminar propietario
    public void eliminarPropietario(int id) throws EntidadNoEncontradaException {
        propietarioDAO.eliminar(id);
    }

    // Obtener todos los propietarios
    public List<PropietarioDTO> obtenerTodos() {
        return propietarioDAO.obtenerTodos();
    }

    // Validar que ningún campo esté vacío
    private void validarCampos(String nombre, String documento, String telefono, String correo) throws CampoVacioException {
        if (nombre.isBlank() || documento.isBlank() || telefono.isBlank() || correo.isBlank()) {
            throw new CampoVacioException("Todos los campos deben estar completos.");
        }
    }
}
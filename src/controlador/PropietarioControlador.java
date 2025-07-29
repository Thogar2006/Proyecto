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

public class PropietarioControlador {

    private final PropietarioDAO dao;

    public PropietarioControlador(PropietarioDAO dao) {
        this.dao = dao;
    }

    public void agregar(PropietarioDTO p) throws CampoVacioException, EntidadDuplicadaException {
        validar(p);
        dao.agregar(p);
    }

    public void actualizar(PropietarioDTO p) throws CampoVacioException, EntidadNoEncontradaException {
        validar(p);
        dao.actualizar(p);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<PropietarioDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(PropietarioDTO p) throws CampoVacioException {
        if (p.getNombre().isEmpty() || p.getDocumento().isEmpty() || p.getTelefono().isEmpty() || p.getCorreo().isEmpty()) {
            throw new CampoVacioException("Todos los campos deben estar completos.");
        }
    }
}
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

public class VeterinarioControlador {

    private final VeterinarioDAO dao;

    public VeterinarioControlador(VeterinarioDAO dao) {
        this.dao = dao;
    }

    public void agregar(VeterinarioDTO v) throws CampoVacioException, EntidadDuplicadaException {
        validar(v);
        dao.agregar(v);
    }

    public void actualizar(VeterinarioDTO v) throws CampoVacioException, EntidadNoEncontradaException {
        validar(v);
        dao.actualizar(v);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<VeterinarioDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(VeterinarioDTO v) throws CampoVacioException {
        if (v.getNombre().isEmpty() || v.getDocumento().isEmpty() ||
            v.getTelefono().isEmpty() || v.getCorreo().isEmpty() ||
            v.getEspecialidad().isEmpty()) {
            throw new CampoVacioException("Todos los campos deben estar completos.");
        }
    }
}
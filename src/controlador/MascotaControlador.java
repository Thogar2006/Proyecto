/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.MascotaDAO;
import dto.MascotaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.List;

public class MascotaControlador {

    private final MascotaDAO dao;

    public MascotaControlador(MascotaDAO dao) {
        this.dao = dao;
    }

    public void agregar(MascotaDTO m) throws CampoVacioException, EntidadDuplicadaException {
        validar(m);
        dao.agregar(m);
    }

    public void actualizar(MascotaDTO m) throws CampoVacioException, EntidadNoEncontradaException {
        validar(m);
        dao.actualizar(m);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<MascotaDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(MascotaDTO m) throws CampoVacioException {
        if (m.getNombre().isEmpty() || m.getEspecie().isEmpty() || m.getRaza().isEmpty() ||
            m.getEdad() <= 0 || m.getPropietario() == null) {
            throw new CampoVacioException("Todos los campos deben estar completos.");
        }
    }
}

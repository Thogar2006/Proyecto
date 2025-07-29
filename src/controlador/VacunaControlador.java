/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.VacunaDAO;
import dto.VacunaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.List;

public class VacunaControlador {
    private final VacunaDAO dao;

    public VacunaControlador(VacunaDAO dao) {
        this.dao = dao;
    }

    public void agregar(VacunaDTO vacuna) throws CampoVacioException, EntidadDuplicadaException {
        validar(vacuna);
        dao.agregar(vacuna);
    }

    public void actualizar(VacunaDTO vacuna) throws CampoVacioException, EntidadNoEncontradaException {
        validar(vacuna);
        dao.actualizar(vacuna);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<VacunaDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(VacunaDTO vacuna) throws CampoVacioException {
        if (vacuna.getMascota() == null ||
            vacuna.getTipo() == null || vacuna.getTipo().isEmpty() ||
            vacuna.getDosis() == null || vacuna.getDosis().isEmpty() ||
            vacuna.getFechaAplicacion() == null) {
            throw new CampoVacioException("Todos los campos de la vacuna deben estar completos.");
        }
    }
}

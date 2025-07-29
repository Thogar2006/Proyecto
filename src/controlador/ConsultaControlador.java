/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ConsultaDAO;
import dto.ConsultaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.List;

public class ConsultaControlador {
    private final ConsultaDAO dao;

    public ConsultaControlador(ConsultaDAO dao) {
        this.dao = dao;
    }

    public void agregar(ConsultaDTO consulta) throws CampoVacioException, EntidadDuplicadaException {
        validar(consulta);
        dao.agregar(consulta);
    }

    public void actualizar(ConsultaDTO consulta) throws CampoVacioException, EntidadNoEncontradaException {
        validar(consulta);
        dao.actualizar(consulta);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<ConsultaDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(ConsultaDTO consulta) throws CampoVacioException {
        if (consulta.getMascota() == null || consulta.getPropietario() == null || consulta.getVeterinario() == null ||
            consulta.getFechaHora() == null || consulta.getDiagnostico().isEmpty() || consulta.getTratamiento().isEmpty()) {
            throw new CampoVacioException("Todos los campos de la consulta deben estar completos.");
        }
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;



import dao.CitaDAO;
import dto.CitaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.List;

public class CitaControlador {
    private final CitaDAO dao;

    public CitaControlador(CitaDAO dao) {
        this.dao = dao;
    }

    public void agregar(CitaDTO cita) throws CampoVacioException, EntidadDuplicadaException {
        validar(cita);
        dao.agregar(cita);
    }
    
    

    public void actualizar(CitaDTO cita) throws CampoVacioException, EntidadNoEncontradaException {
        validar(cita);
        dao.actualizar(cita);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        dao.eliminar(id);
    }

    public List<CitaDTO> obtenerTodos() {
        return dao.obtenerTodos();
    }

    private void validar(CitaDTO cita) throws CampoVacioException {
        if (cita.getMascota() == null || cita.getPropietario() == null || cita.getVeterinario() == null ||
            cita.getFechaHora() == null || cita.getMotivo().isEmpty()) {
            throw new CampoVacioException("Todos los campos de la cita deben estar completos.");
        }
    }
}
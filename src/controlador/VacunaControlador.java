/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.VacunaDAO;
import dto.MascotaDTO;
import dto.VacunaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.time.LocalDate;
import java.util.List;

/**
 *
 * @author bossstore
 */
public class VacunaControlador {
    private final VacunaDAO vacunaDAO;

    public VacunaControlador(VacunaDAO vacunaDAO) {
        this.vacunaDAO = vacunaDAO;
    }

    public void agregarVacuna(int id, MascotaDTO mascota, String tipo, String dosis, LocalDate fecha)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(mascota, tipo, dosis, fecha);

        VacunaDTO vacuna = new VacunaDTO(id, mascota, tipo, dosis, fecha);
        vacunaDAO.agregar(vacuna);
    }

    public VacunaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return vacunaDAO.buscarPorId(id);
    }

    public void actualizarVacuna(int id, MascotaDTO mascota, String tipo, String dosis, LocalDate fecha)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(mascota, tipo, dosis, fecha);

        VacunaDTO vacuna = new VacunaDTO(id, mascota, tipo, dosis, fecha);
        vacunaDAO.actualizar(vacuna);
    }

    public void eliminarVacuna(int id) throws EntidadNoEncontradaException {
        vacunaDAO.eliminar(id);
    }

    public List<VacunaDTO> obtenerTodas() {
        return vacunaDAO.obtenerTodas();
    }

    private void validarCampos(MascotaDTO mascota, String tipo, String dosis, LocalDate fecha)
            throws CampoVacioException {

        if (mascota == null || tipo.isBlank() || dosis.isBlank() || fecha == null) {
            throw new CampoVacioException("Todos los campos de la vacuna deben estar completos.");
        }
    }
}

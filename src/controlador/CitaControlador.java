/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.CitaDAO;
import dto.CitaDTO;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import dto.VeterinarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author bossstore
 */
public class CitaControlador {
    private final CitaDAO citaDAO;

    public CitaControlador(CitaDAO citaDAO) {
        this.citaDAO = citaDAO;
    }

    // Agregar cita
    public void agregarCita(int id, MascotaDTO mascota, PropietarioDTO propietario,
                            VeterinarioDTO veterinario, LocalDateTime fechaHora, String motivo)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(mascota, propietario, veterinario, fechaHora, motivo);

        CitaDTO nueva = new CitaDTO(id, mascota, propietario, veterinario, fechaHora, motivo);
        citaDAO.agregar(nueva);
    }

    // Buscar por ID
    public CitaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return citaDAO.buscarPorId(id);
    }

    // Actualizar
    public void actualizarCita(int id, MascotaDTO mascota, PropietarioDTO propietario,
                               VeterinarioDTO veterinario, LocalDateTime fechaHora, String motivo)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(mascota, propietario, veterinario, fechaHora, motivo);

        CitaDTO actualizada = new CitaDTO(id, mascota, propietario, veterinario, fechaHora, motivo);
        citaDAO.actualizar(actualizada);
    }

    // Eliminar
    public void eliminarCita(int id) throws EntidadNoEncontradaException {
        citaDAO.eliminar(id);
    }

    // Obtener todas
    public List<CitaDTO> obtenerTodas() {
        return citaDAO.obtenerTodas();
    }

    // Validar campos
    private void validarCampos(MascotaDTO mascota, PropietarioDTO propietario, VeterinarioDTO veterinario,
                               LocalDateTime fechaHora, String motivo) throws CampoVacioException {

        if (mascota == null || propietario == null || veterinario == null ||
            fechaHora == null || motivo.isBlank()) {
            throw new CampoVacioException("Todos los campos de la cita deben estar completos.");
        }
    }
}
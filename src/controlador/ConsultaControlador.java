/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.ConsultaDAO;
import dto.ConsultaDTO;
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
public class ConsultaControlador {
    private final ConsultaDAO consultaDAO;

    public ConsultaControlador(ConsultaDAO consultaDAO) {
        this.consultaDAO = consultaDAO;
    }

    public void agregarConsulta(int id, MascotaDTO mascota, PropietarioDTO propietario,
                                VeterinarioDTO veterinario, LocalDateTime fechaHora,
                                String diagnostico, String tratamiento)
            throws CampoVacioException, EntidadDuplicadaException {

        validarCampos(mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);

        ConsultaDTO consulta = new ConsultaDTO(id, mascota, propietario, veterinario,
                fechaHora, diagnostico, tratamiento);
        consultaDAO.agregar(consulta);
    }

    public ConsultaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return consultaDAO.buscarPorId(id);
    }

    public void actualizarConsulta(int id, MascotaDTO mascota, PropietarioDTO propietario,
                                   VeterinarioDTO veterinario, LocalDateTime fechaHora,
                                   String diagnostico, String tratamiento)
            throws CampoVacioException, EntidadNoEncontradaException {

        validarCampos(mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);

        ConsultaDTO consulta = new ConsultaDTO(id, mascota, propietario, veterinario,
                fechaHora, diagnostico, tratamiento);
        consultaDAO.actualizar(consulta);
    }

    public void eliminarConsulta(int id) throws EntidadNoEncontradaException {
        consultaDAO.eliminar(id);
    }

    public List<ConsultaDTO> obtenerTodas() {
        return consultaDAO.obtenerTodas();
    }

    private void validarCampos(MascotaDTO mascota, PropietarioDTO propietario, VeterinarioDTO veterinario,
                               LocalDateTime fechaHora, String diagnostico, String tratamiento)
            throws CampoVacioException {

        if (mascota == null || propietario == null || veterinario == null || fechaHora == null ||
            diagnostico.isBlank() || tratamiento.isBlank()) {
            throw new CampoVacioException("Todos los campos de la consulta deben estar completos.");
        }
    }
}  

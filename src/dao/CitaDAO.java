/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.CitaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class CitaDAO {
    private final List<CitaDTO> lista = new ArrayList<>();

    public void agregar(CitaDTO cita) throws EntidadDuplicadaException {
        for (CitaDTO c : lista) {
            if (c.getId() == cita.getId()) {
                throw new EntidadDuplicadaException("Ya existe una cita con ese ID.");
            }
        }
        lista.add(cita);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        CitaDTO encontrada = buscarPorId(id);
        lista.remove(encontrada);
    }

    public void actualizar(CitaDTO cita) throws EntidadNoEncontradaException {
        CitaDTO existente = buscarPorId(cita.getId());
        existente.setMascota(cita.getMascota());
        existente.setPropietario(cita.getPropietario());
        existente.setVeterinario(cita.getVeterinario());
        existente.setFechaHora(cita.getFechaHora());
        existente.setMotivo(cita.getMotivo());
    }

    public CitaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        for (CitaDTO c : lista) {
            if (c.getId() == id) return c;
        }
        throw new EntidadNoEncontradaException("Cita no encontrada.");
    }

    public List<CitaDTO> obtenerTodos() {
        return lista;
    }

    public void setLista(List<CitaDTO> nuevaLista) {
        lista.clear();
        lista.addAll(nuevaLista);
    }
}
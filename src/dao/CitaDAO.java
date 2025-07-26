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

/**
 *
 * @author bossstore
 */

public class CitaDAO {
    private final List<CitaDTO> lista = new ArrayList<>();

    // Agregar una nueva cita
    public void agregar(CitaDTO cita) throws EntidadDuplicadaException {
        for (CitaDTO c : lista) {
            if (c.getId() == cita.getId()) {
                throw new EntidadDuplicadaException("Ya existe una cita con el ID: " + cita.getId());
            }
        }
        lista.add(cita);
    }

    // Buscar por ID
    public CitaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Cita con ID " + id + " no encontrada."));
    }

    // Actualizar cita
    public void actualizar(CitaDTO cita) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == cita.getId()) {
                lista.set(i, cita);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Cita con ID " + cita.getId() + " no encontrada.");
    }

    // Eliminar por ID
    public void eliminar(int id) throws EntidadNoEncontradaException {
        CitaDTO cita = buscarPorId(id);
        lista.remove(cita);
    }

    // Obtener todas
    public List<CitaDTO> obtenerTodas() {
        return new ArrayList<>(lista);
    }
}
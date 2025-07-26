/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.VacunaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bossstore
 */
public class VacunaDAO {
    private final List<VacunaDTO> lista = new ArrayList<>();

    public void agregar(VacunaDTO vacuna) throws EntidadDuplicadaException {
        for (VacunaDTO v : lista) {
            if (v.getId() == vacuna.getId()) {
                throw new EntidadDuplicadaException("Ya existe una vacuna con ID: " + vacuna.getId());
            }
        }
        lista.add(vacuna);
    }

    public VacunaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Vacuna con ID " + id + " no encontrada."));
    }

    public void actualizar(VacunaDTO vacuna) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == vacuna.getId()) {
                lista.set(i, vacuna);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Vacuna con ID " + vacuna.getId() + " no encontrada.");
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        VacunaDTO vacuna = buscarPorId(id);
        lista.remove(vacuna);
    }

    public List<VacunaDTO> obtenerTodas() {
        return new ArrayList<>(lista);
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ConsultaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author bossstore
 */
public class ConsultaDAO {
    private final List<ConsultaDTO> lista = new ArrayList<>();

    public void agregar(ConsultaDTO consulta) throws EntidadDuplicadaException {
        for (ConsultaDTO c : lista) {
            if (c.getId() == consulta.getId()) {
                throw new EntidadDuplicadaException("Ya existe una consulta con ID: " + consulta.getId());
            }
        }
        lista.add(consulta);
    }

    public ConsultaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(c -> c.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Consulta con ID " + id + " no encontrada."));
    }

    public void actualizar(ConsultaDTO consulta) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == consulta.getId()) {
                lista.set(i, consulta);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Consulta con ID " + consulta.getId() + " no encontrada.");
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        ConsultaDTO consulta = buscarPorId(id);
        lista.remove(consulta);
    }

    public List<ConsultaDTO> obtenerTodas() {
        return new ArrayList<>(lista);
    }
}
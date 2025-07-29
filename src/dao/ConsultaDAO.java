/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.ConsultaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDAO implements Serializable {
    private static final long serialVersionUID = 1L;
    private final List<ConsultaDTO> lista = new ArrayList<>();

    public void agregar(ConsultaDTO consulta) throws EntidadDuplicadaException {
        for (ConsultaDTO c : lista) {
            if (c.getId() == consulta.getId()) {
                throw new EntidadDuplicadaException("Ya existe una consulta con el ID: " + consulta.getId());
            }
        }
        lista.add(consulta);
    }

    public void actualizar(ConsultaDTO consulta) throws EntidadNoEncontradaException {
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == consulta.getId()) {
                lista.set(i, consulta);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new EntidadNoEncontradaException("Consulta no encontrada para actualizar.");
        }
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        ConsultaDTO consultaAEliminar = null;
        for (ConsultaDTO c : lista) {
            if (c.getId() == id) {
                consultaAEliminar = c;
                break;
            }
        }
        if (consultaAEliminar != null) {
            lista.remove(consultaAEliminar);
        } else {
            throw new EntidadNoEncontradaException("Consulta no encontrada para eliminar.");
        }
    }

    public List<ConsultaDTO> obtenerTodos() {
        return new ArrayList<>(lista);
    }

    public void setLista(List<ConsultaDTO> nuevasConsultas) {
        this.lista.clear();
        this.lista.addAll(nuevasConsultas);
    }
}
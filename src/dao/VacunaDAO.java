/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.VacunaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class VacunaDAO implements Serializable {
    private static final long serialVersionUID = 1L;

    private final List<VacunaDTO> lista = new ArrayList<>();

    public void agregar(VacunaDTO vacuna) throws EntidadDuplicadaException {
        for (VacunaDTO v : lista) {
            if (v.getId() == vacuna.getId()) {
                throw new EntidadDuplicadaException("Ya existe una vacuna con el ID: " + vacuna.getId());
            }
        }
        lista.add(vacuna);
    }

    public void actualizar(VacunaDTO vacuna) throws EntidadNoEncontradaException {
        boolean encontrado = false;
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == vacuna.getId()) {
                lista.set(i, vacuna);
                encontrado = true;
                break;
            }
        }
        if (!encontrado) {
            throw new EntidadNoEncontradaException("Vacuna no encontrada para actualizar.");
        }
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        VacunaDTO vacunaEliminar = null;
        for (VacunaDTO v : lista) {
            if (v.getId() == id) {
                vacunaEliminar = v;
                break;
            }
        }
        if (vacunaEliminar != null) {
            lista.remove(vacunaEliminar);
        } else {
            throw new EntidadNoEncontradaException("Vacuna no encontrada para eliminar.");
        }
    }

    public List<VacunaDTO> obtenerTodos() {
        return new ArrayList<>(lista);
    }

    public void setLista(List<VacunaDTO> listaNueva) {
        lista.clear();
        lista.addAll(listaNueva);
    }
}
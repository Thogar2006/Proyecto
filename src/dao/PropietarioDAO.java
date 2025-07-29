/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.PropietarioDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class PropietarioDAO {
    private final List<PropietarioDTO> lista = new ArrayList<>();

    public void agregar(PropietarioDTO propietario) throws EntidadDuplicadaException {
        for (PropietarioDTO p : lista) {
            if (p.getDocumento().equals(propietario.getDocumento())) {
                throw new EntidadDuplicadaException("Ya existe un propietario con ese documento.");
            }
        }
        lista.add(propietario);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        PropietarioDTO encontrado = buscarPorId(id);
        lista.remove(encontrado);
    }

    public void actualizar(PropietarioDTO propietario) throws EntidadNoEncontradaException {
        PropietarioDTO existente = buscarPorId(propietario.getId());
        existente.setNombre(propietario.getNombre());
        existente.setDocumento(propietario.getDocumento());
        existente.setTelefono(propietario.getTelefono());
        existente.setCorreo(propietario.getCorreo());
    }

    public PropietarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        for (PropietarioDTO p : lista) {
            if (p.getId() == id) return p;
        }
        throw new EntidadNoEncontradaException("Propietario no encontrado.");
    }

    public List<PropietarioDTO> obtenerTodos() {
        return lista;
    }

    // Métodos para persistencia
    public void setLista(List<PropietarioDTO> nuevaLista) {
        lista.clear();
        lista.addAll(nuevaLista);
    }
}
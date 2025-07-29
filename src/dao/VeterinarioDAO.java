/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.VeterinarioDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class VeterinarioDAO {
    private final List<VeterinarioDTO> lista = new ArrayList<>();

    public void agregar(VeterinarioDTO veterinario) throws EntidadDuplicadaException {
        for (VeterinarioDTO v : lista) {
            if (v.getDocumento().equals(veterinario.getDocumento())) {
                throw new EntidadDuplicadaException("Ya existe un veterinario con ese documento.");
            }
        }
        lista.add(veterinario);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        VeterinarioDTO encontrado = buscarPorId(id);
        lista.remove(encontrado);
    }

    public void actualizar(VeterinarioDTO veterinario) throws EntidadNoEncontradaException {
        VeterinarioDTO existente = buscarPorId(veterinario.getId());
        existente.setNombre(veterinario.getNombre());
        existente.setDocumento(veterinario.getDocumento());
        existente.setTelefono(veterinario.getTelefono());
        existente.setCorreo(veterinario.getCorreo());
        existente.setEspecialidad(veterinario.getEspecialidad());
    }

    public VeterinarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        for (VeterinarioDTO v : lista) {
            if (v.getId() == id) return v;
        }
        throw new EntidadNoEncontradaException("Veterinario no encontrado.");
    }

    public List<VeterinarioDTO> obtenerTodos() {
        return lista;
    }

    public void setLista(List<VeterinarioDTO> nuevaLista) {
        lista.clear();
        lista.addAll(nuevaLista);
    }
}
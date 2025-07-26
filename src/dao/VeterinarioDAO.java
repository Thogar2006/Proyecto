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

/**
 *
 * @author bossstore
 */
public class VeterinarioDAO {
    private final List<VeterinarioDTO> lista = new ArrayList<>();

    // Agregar nuevo veterinario
    public void agregar(VeterinarioDTO veterinario) throws EntidadDuplicadaException {
        for (VeterinarioDTO v : lista) {
            if (v.getDocumento().equals(veterinario.getDocumento())) {
                throw new EntidadDuplicadaException("Ya existe un veterinario con el documento: " + veterinario.getDocumento());
            }
        }
        lista.add(veterinario);
    }

    // Buscar por ID
    public VeterinarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(v -> v.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Veterinario con ID " + id + " no encontrado."));
    }

    // Buscar por documento
    public VeterinarioDTO buscarPorDocumento(String documento) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(v -> v.getDocumento().equals(documento))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Veterinario con documento " + documento + " no encontrado."));
    }

    // Actualizar veterinario
    public void actualizar(VeterinarioDTO veterinario) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == veterinario.getId()) {
                lista.set(i, veterinario);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Veterinario con ID " + veterinario.getId() + " no encontrado.");
    }

    // Eliminar por ID
    public void eliminar(int id) throws EntidadNoEncontradaException {
        VeterinarioDTO v = buscarPorId(id);
        lista.remove(v);
    }

    // Obtener todos
    public List<VeterinarioDTO> obtenerTodos() {
        return new ArrayList<>(lista); // Copia para seguridad
    }
}
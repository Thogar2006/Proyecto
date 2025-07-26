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

/**
 *
 * @author bossstore
 */
public class PropietarioDAO {
    private final List<PropietarioDTO> lista = new ArrayList<>();

    // Agregar un propietario nuevo
    public void agregar(PropietarioDTO propietario) throws EntidadDuplicadaException {
        for (PropietarioDTO p : lista) {
            if (p.getDocumento().equals(propietario.getDocumento())) {
                throw new EntidadDuplicadaException("Ya existe un propietario con el documento: " + propietario.getDocumento());
            }
        }
        lista.add(propietario);
    }

    // Buscar por ID
    public PropietarioDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Propietario con ID " + id + " no encontrado."));
    }

    // Buscar por documento
    public PropietarioDTO buscarPorDocumento(String documento) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(p -> p.getDocumento().equals(documento))
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Propietario con documento " + documento + " no encontrado."));
    }

    // Actualizar datos
    public void actualizar(PropietarioDTO propietario) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == propietario.getId()) {
                lista.set(i, propietario);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Propietario con ID " + propietario.getId() + " no encontrado.");
    }

    // Eliminar por ID
    public void eliminar(int id) throws EntidadNoEncontradaException {
        PropietarioDTO encontrado = buscarPorId(id);
        lista.remove(encontrado);
    }

    // Obtener todos
    public List<PropietarioDTO> obtenerTodos() {
        return new ArrayList<>(lista); // Copia de seguridad
    }
}
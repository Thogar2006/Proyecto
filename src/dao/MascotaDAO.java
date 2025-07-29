/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import dto.MascotaDTO;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {
    private final List<MascotaDTO> lista = new ArrayList<>();

    public void agregar(MascotaDTO mascota) throws EntidadDuplicadaException {
        for (MascotaDTO m : lista) {
            if (m.getId() == mascota.getId()) {
                throw new EntidadDuplicadaException("Ya existe una mascota con el ID: " + mascota.getId());
            }
        }
        lista.add(mascota);
    }

    public void eliminar(int id) throws EntidadNoEncontradaException {
        MascotaDTO encontrada = buscarPorId(id);
        lista.remove(encontrada);
    }

    public void actualizar(MascotaDTO mascota) throws EntidadNoEncontradaException {
        MascotaDTO existente = buscarPorId(mascota.getId());
        existente.setNombre(mascota.getNombre());
        existente.setEspecie(mascota.getEspecie());
        existente.setRaza(mascota.getRaza());
        existente.setEdad(mascota.getEdad());
        existente.setPropietario(mascota.getPropietario());
    }

    public MascotaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        for (MascotaDTO m : lista) {
            if (m.getId() == id) return m;
        }
        throw new EntidadNoEncontradaException("Mascota no encontrada.");
    }

    public List<MascotaDTO> obtenerTodos() {
        return lista;
    }

    public void setLista(List<MascotaDTO> nuevaLista) {
        lista.clear();
        lista.addAll(nuevaLista);
    }
}

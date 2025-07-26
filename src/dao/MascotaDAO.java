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



/**
 *
 * @author bossstore
 */
public class MascotaDAO {
    private final List<MascotaDTO> lista = new ArrayList<>();

    // Agregar mascota
    public void agregar(MascotaDTO mascota) throws EntidadDuplicadaException {
        for (MascotaDTO m : lista) {
            if (m.getId() == mascota.getId()) {
                throw new EntidadDuplicadaException("Ya existe una mascota con el ID: " + mascota.getId());
            }
        }
        lista.add(mascota);
    }

    // Buscar por ID
    public MascotaDTO buscarPorId(int id) throws EntidadNoEncontradaException {
        return lista.stream()
                .filter(m -> m.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntidadNoEncontradaException("Mascota con ID " + id + " no encontrada."));
    }

    // Buscar por nombre (opcional)
    public List<MascotaDTO> buscarPorNombre(String nombre) {
        List<MascotaDTO> resultados = new ArrayList<>();
        for (MascotaDTO m : lista) {
            if (m.getNombre().equalsIgnoreCase(nombre)) {
                resultados.add(m);
            }
        }
        return resultados;
    }

    // Actualizar
    public void actualizar(MascotaDTO mascota) throws EntidadNoEncontradaException {
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).getId() == mascota.getId()) {
                lista.set(i, mascota);
                return;
            }
        }
        throw new EntidadNoEncontradaException("Mascota con ID " + mascota.getId() + " no encontrada.");
    }

    // Eliminar
    public void eliminar(int id) throws EntidadNoEncontradaException {
        MascotaDTO m = buscarPorId(id);
        lista.remove(m);
    }

    // Obtener todas
    public List<MascotaDTO> obtenerTodas() {
        return new ArrayList<>(lista);
    }
}

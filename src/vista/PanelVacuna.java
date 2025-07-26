/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.VacunaControlador;
import dto.MascotaDTO;
import dto.VacunaDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import singleton.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.util.List;

public class PanelVacuna extends JPanel {

    private final VacunaControlador vacunaControlador;
    private final JComboBox<MascotaDTO> comboMascota;
    private final JTextField txtId, txtTipo, txtDosis, txtFecha;
    private final JTable tabla;
    private final DefaultTableModel modelo;

    public PanelVacuna() {
        vacunaControlador = Singleton.getInstance().getVacunaControlador();

        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 10, 10));

        txtId = new JTextField();
        comboMascota = new JComboBox<>();
        txtTipo = new JTextField();
        txtDosis = new JTextField();
        txtFecha = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Mascota:"));
        panelFormulario.add(comboMascota);
        panelFormulario.add(new JLabel("Tipo Vacuna:"));
        panelFormulario.add(txtTipo);
        panelFormulario.add(new JLabel("Dosis:"));
        panelFormulario.add(txtDosis);
        panelFormulario.add(new JLabel("Fecha Aplicación (AAAA-MM-DD):"));
        panelFormulario.add(txtFecha);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 5, 5));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        add(panelSuperior, BorderLayout.NORTH);

        // Tabla
        modelo = new DefaultTableModel(new Object[]{"ID", "Mascota", "Tipo", "Dosis", "Fecha"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        cargarComboMascotas();
        cargarTabla();

        // Acciones
        btnGuardar.addActionListener(e -> guardarVacuna());
        btnBuscar.addActionListener(e -> buscarVacuna());
        btnEditar.addActionListener(e -> editarVacuna());
        btnEliminar.addActionListener(e -> eliminarVacuna());
        btnLimpiar.addActionListener(e -> limpiarCampos());
    }

    private void cargarComboMascotas() {
        comboMascota.removeAllItems();
        List<MascotaDTO> mascotas = Singleton.getInstance().getMascotaControlador().obtenerTodas();
        for (MascotaDTO m : mascotas) {
            comboMascota.addItem(m);
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (VacunaDTO v : vacunaControlador.obtenerTodas()) {
            modelo.addRow(new Object[]{
                v.getId(),
                v.getMascota().getNombre(),
                v.getTipo(),
                v.getDosis(),
                v.getFechaAplicacion()
            });
        }
    }

    private void guardarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            String tipo = txtTipo.getText();
            String dosis = txtDosis.getText();
            LocalDate fecha = LocalDate.parse(txtFecha.getText());

            vacunaControlador.agregarVacuna(id, mascota, tipo, dosis, fecha);
            JOptionPane.showMessageDialog(this, "Vacuna registrada exitosamente.");
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numérico.");
        } catch (CampoVacioException | EntidadDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void buscarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText());
            VacunaDTO v = vacunaControlador.buscarPorId(id);

            comboMascota.setSelectedItem(v.getMascota());
            txtTipo.setText(v.getTipo());
            txtDosis.setText(v.getDosis());
            txtFecha.setText(v.getFechaAplicacion().toString());

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numérico.");
        } catch (EntidadNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void editarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            String tipo = txtTipo.getText();
            String dosis = txtDosis.getText();
            LocalDate fecha = LocalDate.parse(txtFecha.getText());

            vacunaControlador.actualizarVacuna(id, mascota, tipo, dosis, fecha);
            JOptionPane.showMessageDialog(this, "Vacuna actualizada.");
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numérico.");
        } catch (CampoVacioException | EntidadNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText());
            vacunaControlador.eliminarVacuna(id);
            JOptionPane.showMessageDialog(this, "Vacuna eliminada.");
            limpiarCampos();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID debe ser numérico.");
        } catch (EntidadNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtTipo.setText("");
        txtDosis.setText("");
        txtFecha.setText("");
        comboMascota.setSelectedIndex(0);
    }
}

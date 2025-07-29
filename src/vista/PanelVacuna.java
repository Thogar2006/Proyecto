/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.VacunaControlador;
import dto.MascotaDTO;
import dto.VacunaDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import persistencia.Singleton;

public class PanelVacuna extends JPanel {

    private final VacunaControlador controlador = Singleton.getInstancia().getVacunaControlador();
    private final JComboBox<MascotaDTO> comboMascota = new JComboBox<>();
    private final JTextField txtId = new JTextField(5);
    private final JTextField txtTipo = new JTextField(10);
    private final JTextField txtDosis = new JTextField(10);
    private final JTextField txtFecha = new JTextField(10);
    private final DefaultTableModel modelo = new DefaultTableModel(new Object[]{"ID", "Mascota", "Tipo", "Dosis", "Fecha"}, 0);
    private final JTable tabla = new JTable(modelo);

    public PanelVacuna() {
        setLayout(new BorderLayout());

        JPanel panelCampos = new JPanel(new GridLayout(6, 2));
        panelCampos.add(new JLabel("ID:"));
        panelCampos.add(txtId);
        panelCampos.add(new JLabel("Mascota:"));
        panelCampos.add(comboMascota);
        panelCampos.add(new JLabel("Tipo:"));
        panelCampos.add(txtTipo);
        panelCampos.add(new JLabel("Dosis:"));
        panelCampos.add(txtDosis);
        panelCampos.add(new JLabel("Fecha Aplicación (YYYY-MM-DD):"));
        panelCampos.add(txtFecha);

        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        add(panelCampos, BorderLayout.NORTH);
        add(new JScrollPane(tabla), BorderLayout.CENTER);
        add(panelBotones, BorderLayout.SOUTH);

        cargarComboMascotas();
        cargarTabla();

        btnGuardar.addActionListener(e -> guardarVacuna());
        btnActualizar.addActionListener(e -> actualizarVacuna());
        btnEliminar.addActionListener(e -> eliminarVacuna());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarDesdeTabla());
    }

    private void cargarComboMascotas() {
        comboMascota.removeAllItems();
        for (MascotaDTO m : Singleton.getInstancia().getMascotaControlador().obtenerTodos()) {
            comboMascota.addItem(m);
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for (VacunaDTO v : controlador.obtenerTodos()) {
            modelo.addRow(new Object[]{
                v.getId(),
                v.getMascota().getNombre(),
                v.getTipo(),
                v.getDosis(),
                v.getFechaAplicacion().format(formatter)
            });
        }
    }

    private void guardarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            String tipo = txtTipo.getText().trim();
            String dosis = txtDosis.getText().trim();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

            VacunaDTO vacuna = new VacunaDTO(id, mascota, tipo, dosis, fecha);
            controlador.agregar(vacuna);
            JOptionPane.showMessageDialog(this, "Vacuna registrada.");
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void actualizarVacuna() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            String tipo = txtTipo.getText().trim();
            String dosis = txtDosis.getText().trim();
            LocalDate fecha = LocalDate.parse(txtFecha.getText().trim());

            VacunaDTO vacuna = new VacunaDTO(id, mascota, tipo, dosis, fecha);
            controlador.actualizar(vacuna);
            JOptionPane.showMessageDialog(this, "Vacuna actualizada.");
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarVacuna() {
        try {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione una fila.");
                return;
            }
            int id = (int) modelo.getValueAt(fila, 0);
            controlador.eliminar(id);
            JOptionPane.showMessageDialog(this, "Vacuna eliminada.");
            cargarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void cargarDesdeTabla() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(modelo.getValueAt(fila, 0).toString());
            String nombreMascota = modelo.getValueAt(fila, 1).toString();
            for (int i = 0; i < comboMascota.getItemCount(); i++) {
                if (comboMascota.getItemAt(i).getNombre().equals(nombreMascota)) {
                    comboMascota.setSelectedIndex(i);
                    break;
                }
            }
            txtTipo.setText(modelo.getValueAt(fila, 2).toString());
            txtDosis.setText(modelo.getValueAt(fila, 3).toString());
            txtFecha.setText(modelo.getValueAt(fila, 4).toString());
        }
    }
}

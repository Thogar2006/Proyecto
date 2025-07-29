/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.CitaControlador;
import dto.CitaDTO;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import dto.VeterinarioDTO;
import persistencia.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class PanelCita extends JPanel {

    private final JTextField txtId = new JTextField(10);
    private final JComboBox<MascotaDTO> comboMascota = new JComboBox<>();
    private final JComboBox<PropietarioDTO> comboPropietario = new JComboBox<>();
    private final JComboBox<VeterinarioDTO> comboVeterinario = new JComboBox<>();
    private final JTextField txtFechaHora = new JTextField(20); // yyyy-MM-ddTHH:mm
    private final JTextField txtMotivo = new JTextField(20);
    private final JTable tabla = new JTable();
    private final DefaultTableModel modelo = new DefaultTableModel();

    public PanelCita() {
        setLayout(new BorderLayout());

        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Gestión de Citas"));

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Mascota:"));
        panelFormulario.add(comboMascota);

        panelFormulario.add(new JLabel("Propietario:"));
        panelFormulario.add(comboPropietario);

        panelFormulario.add(new JLabel("Veterinario:"));
        panelFormulario.add(comboVeterinario);

        panelFormulario.add(new JLabel("Fecha y Hora (yyyy-MM-ddTHH:mm):"));
        panelFormulario.add(txtFechaHora);

        panelFormulario.add(new JLabel("Motivo:"));
        panelFormulario.add(txtMotivo);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnLimpiar);

        // Tabla
        modelo.setColumnIdentifiers(new String[]{"ID", "Mascota", "Propietario", "Veterinario", "FechaHora", "Motivo"});
        tabla.setModel(modelo);
        JScrollPane scrollPane = new JScrollPane(tabla);

        add(panelFormulario, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        // Llenar combos
        cargarCombos();

        // Controlador
        CitaControlador controlador = Singleton.getInstancia().getCitaControlador();

        btnGuardar.addActionListener(e -> {
            try {
                if (txtId.getText().isEmpty() || txtFechaHora.getText().isEmpty() || txtMotivo.getText().isEmpty()) {
                    throw new IllegalArgumentException("Todos los campos deben estar completos.");
                }

                int id = Integer.parseInt(txtId.getText().trim());
                MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
                PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
                VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
                String motivo = txtMotivo.getText().trim();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim(), formatter);

                CitaDTO cita = new CitaDTO(id, mascota, propietario, veterinario, fechaHora, motivo);
                controlador.agregar(cita);
                actualizarTabla(controlador);
                limpiarCampos();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "El ID debe ser un número entero.");
            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Fecha y hora inválidas. Usa el formato: yyyy-MM-ddTHH:mm");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            }
        });

        btnLimpiar.addActionListener(e -> limpiarCampos());

        actualizarTabla(controlador);
    }

    private void cargarCombos() {
        comboMascota.removeAllItems();
        comboPropietario.removeAllItems();
        comboVeterinario.removeAllItems();

        for (MascotaDTO m : Singleton.getInstancia().getMascotaControlador().obtenerTodos()) {
            comboMascota.addItem(m);
        }
        for (PropietarioDTO p : Singleton.getInstancia().getPropietarioControlador().obtenerTodos()) {
            comboPropietario.addItem(p);
        }
        for (VeterinarioDTO v : Singleton.getInstancia().getVeterinarioControlador().obtenerTodos()) {
            comboVeterinario.addItem(v);
        }
    }

    private void actualizarTabla(CitaControlador controlador) {
        modelo.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (CitaDTO cita : controlador.obtenerTodos()) {
            modelo.addRow(new Object[]{
                    cita.getId(),
                    cita.getMascota().getNombre(),
                    cita.getPropietario().getNombre(),
                    cita.getVeterinario().getNombre(),
                    cita.getFechaHora().format(formatter),
                    cita.getMotivo()
            });
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtFechaHora.setText("");
        txtMotivo.setText("");
        if (comboMascota.getItemCount() > 0) comboMascota.setSelectedIndex(0);
        if (comboPropietario.getItemCount() > 0) comboPropietario.setSelectedIndex(0);
        if (comboVeterinario.getItemCount() > 0) comboVeterinario.setSelectedIndex(0);
    }
}


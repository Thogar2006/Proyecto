/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author bossstore
 */
import controlador.CitaControlador;
import dto.CitaDTO;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import dto.VeterinarioDTO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import singleton.Singleton;

public class PanelCita extends JPanel {
    private JTextField txtId, txtFechaHora, txtMotivo;
    private JComboBox<MascotaDTO> comboMascota;
    private JComboBox<PropietarioDTO> comboPropietario;
    private JComboBox<VeterinarioDTO> comboVeterinario;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private final CitaControlador controlador;

    public PanelCita(CitaControlador controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(7, 2, 5, 5));
        txtId = new JTextField();
        comboMascota = new JComboBox<>();
        comboPropietario = new JComboBox<>();
        comboVeterinario = new JComboBox<>();
        txtFechaHora = new JTextField(); // formato: 2025-07-25T10:30
        txtMotivo = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Mascota:"));
        panelFormulario.add(comboMascota);
        panelFormulario.add(new JLabel("Propietario:"));
        panelFormulario.add(comboPropietario);
        panelFormulario.add(new JLabel("Veterinario:"));
        panelFormulario.add(comboVeterinario);
        panelFormulario.add(new JLabel("Fecha y hora (2025-07-25T10:30):"));
        panelFormulario.add(txtFechaHora);
        panelFormulario.add(new JLabel("Motivo:"));
        panelFormulario.add(txtMotivo);

        // Botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Mascota", "Propietario", "Veterinario", "Fecha", "Motivo"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tabla);

        add(panelFormulario, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);

        cargarCombos();
        cargarTabla();

        // Acción Guardar
        btnGuardar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
                PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
                VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText());
                String motivo = txtMotivo.getText();

                controlador.agregarCita(id, mascota, propietario, veterinario, fechaHora, motivo);
                JOptionPane.showMessageDialog(this, "Cita registrada correctamente.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción Buscar
        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                CitaDTO c = controlador.buscarPorId(id);
                txtMotivo.setText(c.getMotivo());
                txtFechaHora.setText(c.getFechaHora().toString());
                comboMascota.setSelectedItem(c.getMascota());
                comboPropietario.setSelectedItem(c.getPropietario());
                comboVeterinario.setSelectedItem(c.getVeterinario());
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción Actualizar
        btnActualizar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
                PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
                VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText());
                String motivo = txtMotivo.getText();

                controlador.actualizarCita(id, mascota, propietario, veterinario, fechaHora, motivo);
                JOptionPane.showMessageDialog(this, "Cita actualizada.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Acción Eliminar
        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controlador.eliminarCita(id);
                JOptionPane.showMessageDialog(this, "Cita eliminada.");
                cargarTabla();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void cargarCombos() {
        comboMascota.removeAllItems();
        for (MascotaDTO m : Singleton.getInstance().getMascotaDAO().obtenerTodas()) {
            comboMascota.addItem(m);
        }

        comboPropietario.removeAllItems();
        for (PropietarioDTO p : Singleton.getInstance().getPropietarioDAO().obtenerTodos()) {
            comboPropietario.addItem(p);
        }


        comboVeterinario.removeAllItems();
        for (VeterinarioDTO v : Singleton.getInstance().getVeterinarioDAO().obtenerTodos()) {
            comboVeterinario.addItem(v);
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (CitaDTO c : controlador.obtenerTodas()) {
            modeloTabla.addRow(new Object[]{
                c.getId(),
                c.getMascota().getNombre(),
                c.getPropietario().getNombre(),
                c.getVeterinario().getNombre(),
                c.getFechaHora().format(formatter),
                c.getMotivo()
            });
        }
    }
}
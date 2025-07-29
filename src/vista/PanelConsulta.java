/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ConsultaControlador;
import dto.ConsultaDTO;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import dto.VeterinarioDTO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import persistencia.Singleton;

public class PanelConsulta extends JPanel {
    private final ConsultaControlador controlador = Singleton.getInstancia().getConsultaControlador();
    private final JComboBox<MascotaDTO> comboMascota = new JComboBox<>();
    private final JComboBox<PropietarioDTO> comboPropietario = new JComboBox<>();
    private final JComboBox<VeterinarioDTO> comboVeterinario = new JComboBox<>();
    private final JTextField txtId = new JTextField(15);
    private final JTextField txtFechaHora = new JTextField(15);
    private final JTextField txtDiagnostico = new JTextField(15);
    private final JTextField txtTratamiento = new JTextField(15);
    private final DefaultTableModel modeloTabla = new DefaultTableModel();
    private final JTable tabla = new JTable(modeloTabla);

    public PanelConsulta() {
        setLayout(new BorderLayout());

        // Panel Formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Consulta"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int y = 0;

        // Fila 1: ID
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(txtId, gbc);

        // Fila 2: Mascota
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Mascota:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(comboMascota, gbc);

        // Fila 3: Propietario
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Propietario:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(comboPropietario, gbc);

        // Fila 4: Veterinario
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Veterinario:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(comboVeterinario, gbc);

        // Fila 5: Fecha y hora
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Fecha y hora (yyyy-MM-ddTHH:mm):"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(txtFechaHora, gbc);

        // Fila 6: Diagnóstico
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Diagnóstico:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(txtDiagnostico, gbc);

        // Fila 7: Tratamiento
        gbc.gridx = 0; gbc.gridy = y; panelFormulario.add(new JLabel("Tratamiento:"), gbc);
        gbc.gridx = 1; gbc.gridy = y++; panelFormulario.add(txtTratamiento, gbc);

        // Fila 8: Botones
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        panelBotones.add(btnAgregar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnActualizar);

        gbc.gridx = 0; gbc.gridy = y; gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Mascota");
        modeloTabla.addColumn("Propietario");
        modeloTabla.addColumn("Veterinario");
        modeloTabla.addColumn("Fecha y hora");
        modeloTabla.addColumn("Diagnóstico");
        modeloTabla.addColumn("Tratamiento");

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Cargar datos
        cargarCombos();
        actualizarTabla();

        // Listeners
        btnAgregar.addActionListener(e -> agregarConsulta());
        btnEliminar.addActionListener(e -> eliminarConsulta());
        btnActualizar.addActionListener(e -> actualizarConsulta());
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

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ConsultaDTO c : controlador.obtenerTodos()) {
            modeloTabla.addRow(new Object[]{
                    c.getId(),
                    c.getMascota().getNombre(),
                    c.getPropietario().getNombre(),
                    c.getVeterinario().getNombre(),
                    c.getFechaHora().format(formatter),
                    c.getDiagnostico(),
                    c.getTratamiento()
            });
        }
    }

    private void agregarConsulta() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
            VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
            LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim());
            String diagnostico = txtDiagnostico.getText().trim();
            String tratamiento = txtTratamiento.getText().trim();

            ConsultaDTO consulta = new ConsultaDTO(id, mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);
            controlador.agregar(consulta);

            JOptionPane.showMessageDialog(this, "Consulta agregada exitosamente.");
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al agregar: " + ex.getMessage());
        }
    }

    private void eliminarConsulta() {
        try {
            int fila = tabla.getSelectedRow();
            if (fila >= 0) {
                int id = (int) modeloTabla.getValueAt(fila, 0);
                controlador.eliminar(id);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Consulta eliminada.");
            } else {
                JOptionPane.showMessageDialog(this, "Seleccione una fila para eliminar.");
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
        }
    }

    private void actualizarConsulta() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
            PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
            VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
            LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText().trim());
            String diagnostico = txtDiagnostico.getText().trim();
            String tratamiento = txtTratamiento.getText().trim();

            ConsultaDTO consulta = new ConsultaDTO(id, mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);
            controlador.actualizar(consulta);

            JOptionPane.showMessageDialog(this, "Consulta actualizada.");
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }
}



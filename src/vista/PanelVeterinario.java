/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.VeterinarioControlador;
import dto.VeterinarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import persistencia.Singleton;

public class PanelVeterinario extends JPanel {
    private final JTextField txtId = new JTextField(15);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtDocumento = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtCorreo = new JTextField(15);
    private final JTextField txtEspecialidad = new JTextField(15);
    private final DefaultTableModel modeloTabla = new DefaultTableModel();
    private final JTable tabla = new JTable(modeloTabla);

    private final VeterinarioControlador controlador;

    public PanelVeterinario() {
        this.controlador = Singleton.getInstancia().getVeterinarioControlador();
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridBagLayout());
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Veterinario"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 0;
        panelFormulario.add(new JLabel("ID:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtId, gbc);

        // Fila 2
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Nombre:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtNombre, gbc);

        // Fila 3
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Documento:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtDocumento, gbc);

        // Fila 4
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Teléfono:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtTelefono, gbc);

        // Fila 5
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Correo:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtCorreo, gbc);

        // Fila 6
        gbc.gridx = 0; gbc.gridy++;
        panelFormulario.add(new JLabel("Especialidad:"), gbc);
        gbc.gridx = 1;
        panelFormulario.add(txtEspecialidad, gbc);

        // Fila 7 (botones)
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        JButton btnAgregar = new JButton("Agregar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");
        panelBotones.add(btnAgregar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panelFormulario.add(panelBotones, gbc);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Nombre");
        modeloTabla.addColumn("Documento");
        modeloTabla.addColumn("Teléfono");
        modeloTabla.addColumn("Correo");
        modeloTabla.addColumn("Especialidad");

        JScrollPane scroll = new JScrollPane(tabla);
        add(scroll, BorderLayout.CENTER);

        // Listeners
        btnAgregar.addActionListener(e -> agregarVeterinario());
        btnActualizar.addActionListener(e -> actualizarVeterinario());
        btnEliminar.addActionListener(e -> eliminarVeterinario());
        btnLimpiar.addActionListener(e -> limpiarCampos());
        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccionado());

        actualizarTabla();
    }

    private void agregarVeterinario() {
        try {
            VeterinarioDTO v = obtenerVeterinarioDesdeCampos();
            controlador.agregar(v);
            Singleton.getInstancia().guardarDatos();
            actualizarTabla();
            limpiarCampos();
        } catch (CampoVacioException | EntidadDuplicadaException ex) {
            mostrarError(ex.getMessage());
        } catch (NumberFormatException ex) {
            mostrarError("ID debe ser un número válido.");
        }
    }

    private void actualizarVeterinario() {
        try {
            VeterinarioDTO v = obtenerVeterinarioDesdeCampos();
            controlador.actualizar(v);
            Singleton.getInstancia().guardarDatos();
            actualizarTabla();
            limpiarCampos();
        } catch (CampoVacioException | EntidadNoEncontradaException ex) {
            mostrarError(ex.getMessage());
        } catch (NumberFormatException ex) {
            mostrarError("ID debe ser un número válido.");
        }
    }

    private void eliminarVeterinario() {
        try {
            int id = Integer.parseInt(txtId.getText());
            controlador.eliminar(id);
            Singleton.getInstancia().guardarDatos();
            actualizarTabla();
            limpiarCampos();
        } catch (EntidadNoEncontradaException ex) {
            mostrarError(ex.getMessage());
        } catch (NumberFormatException ex) {
            mostrarError("ID debe ser un número válido.");
        }
    }

    private VeterinarioDTO obtenerVeterinarioDesdeCampos() {
        int id = Integer.parseInt(txtId.getText());
        return new VeterinarioDTO(
                id,
                txtNombre.getText().trim(),
                txtDocumento.getText().trim(),
                txtTelefono.getText().trim(),
                txtCorreo.getText().trim(),
                txtEspecialidad.getText().trim()
        );
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<VeterinarioDTO> lista = controlador.obtenerTodos();
        for (VeterinarioDTO v : lista) {
            modeloTabla.addRow(new Object[]{
                    v.getId(),
                    v.getNombre(),
                    v.getDocumento(),
                    v.getTelefono(),
                    v.getCorreo(),
                    v.getEspecialidad()
            });
        }
    }

    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila >= 0) {
            txtId.setText(modeloTabla.getValueAt(fila, 0).toString());
            txtNombre.setText(modeloTabla.getValueAt(fila, 1).toString());
            txtDocumento.setText(modeloTabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(modeloTabla.getValueAt(fila, 3).toString());
            txtCorreo.setText(modeloTabla.getValueAt(fila, 4).toString());
            txtEspecialidad.setText(modeloTabla.getValueAt(fila, 5).toString());
        }
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtEspecialidad.setText("");
        tabla.clearSelection();
    }

    private void mostrarError(String mensaje) {
        JOptionPane.showMessageDialog(this, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
    }
}

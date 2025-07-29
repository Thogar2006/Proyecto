/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.PropietarioControlador;
import dto.PropietarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import persistencia.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelPropietario extends JPanel {

    private final JTextField txtId = new JTextField(5);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtDocumento = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtCorreo = new JTextField(15);

    private final DefaultTableModel modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Documento", "Teléfono", "Correo"}, 0);
    private final JTable tabla = new JTable(modeloTabla);

    private final PropietarioControlador controlador = Singleton.getInstancia().getPropietarioControlador();

    public PanelPropietario() {
        setLayout(new BorderLayout(10, 10));

        // === PANEL FORMULARIO ===
        JPanel panelFormulario = new JPanel(new GridLayout(5, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Propietario"));

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);

        panelFormulario.add(new JLabel("Documento:"));
        panelFormulario.add(txtDocumento);

        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);

        // === PANEL BOTONES ===
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 5));
        panelBotones.add(btnGuardar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Unir formulario + botones
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // === TABLA ===
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Propietarios"));

        // === AGREGAR A PANEL PRINCIPAL ===
        add(panelSuperior, BorderLayout.NORTH);
        add(scrollTabla, BorderLayout.CENTER);

        // === EVENTOS ===
        btnGuardar.addActionListener(e -> guardar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) cargarSeleccionado();
        });

        cargarTabla();
    }

    private void guardar() {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String documento = txtDocumento.getText().trim();
            String telefono = txtTelefono.getText().trim();
            String correo = txtCorreo.getText().trim();

            PropietarioDTO p = new PropietarioDTO(id, nombre, documento, telefono, correo);

            boolean existe = controlador.obtenerTodos().stream().anyMatch(prop -> prop.getId() == id);
            if (existe) {
                controlador.actualizar(p);
                JOptionPane.showMessageDialog(this, "Propietario actualizado correctamente.");
            } else {
                controlador.agregar(p);
                JOptionPane.showMessageDialog(this, "Propietario agregado correctamente.");
            }

            Singleton.getInstancia().guardarDatos();
            limpiar();
            cargarTabla();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "El ID debe ser numérico.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (CampoVacioException | EntidadDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error inesperado: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        try {
            int fila = tabla.getSelectedRow();
            if (fila == -1) {
                JOptionPane.showMessageDialog(this, "Seleccione un propietario a eliminar.");
                return;
            }

            int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
            controlador.eliminar(id);
            Singleton.getInstancia().guardarDatos();

            JOptionPane.showMessageDialog(this, "Propietario eliminado.");
            limpiar();
            cargarTabla();

        } catch (EntidadNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void cargarSeleccionado() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtDocumento.setText(tabla.getValueAt(fila, 2).toString());
            txtTelefono.setText(tabla.getValueAt(fila, 3).toString());
            txtCorreo.setText(tabla.getValueAt(fila, 4).toString());
        }
    }

    private void cargarTabla() {
        modeloTabla.setRowCount(0);
        List<PropietarioDTO> lista = controlador.obtenerTodos();
        for (PropietarioDTO p : lista) {
            modeloTabla.addRow(new Object[]{
                p.getId(), p.getNombre(), p.getDocumento(), p.getTelefono(), p.getCorreo()
            });
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        tabla.clearSelection();
    }
}


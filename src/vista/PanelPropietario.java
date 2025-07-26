/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.PropietarioControlador;
import dto.PropietarioDTO;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import singleton.Singleton;

/**
 *
 * @author bossstore
 */
public class PanelPropietario extends JPanel {

    private JTextField txtId, txtNombre, txtDocumento, txtCorreo, txtTelefono;
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    private final PropietarioControlador controlador = Singleton.getInstance().getPropietarioControlador();

    public PanelPropietario() {
        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));

        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Propietario"));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtDocumento = new JTextField();
        txtCorreo = new JTextField();
        txtTelefono = new JTextField();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Documento:"));
        panelFormulario.add(txtDocumento);
        panelFormulario.add(new JLabel("Correo:"));
        panelFormulario.add(txtCorreo);
        panelFormulario.add(new JLabel("Teléfono:"));
        panelFormulario.add(txtTelefono);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelFormulario.add(btnGuardar);
        panelFormulario.add(btnBuscar);
        panelFormulario.add(btnEditar);
        panelFormulario.add(btnEliminar);
        panelFormulario.add(btnLimpiar);

        add(panelFormulario, BorderLayout.NORTH);

        // Tabla
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Documento", "Correo", "Teléfono"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollPane = new JScrollPane(tabla);
        add(scrollPane, BorderLayout.CENTER);

        // Eventos
        btnGuardar.addActionListener(e -> guardar());
        btnBuscar.addActionListener(e -> buscar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        actualizarTabla();
    }

    private void guardar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            controlador.agregarPropietario(id, txtNombre.getText(), txtDocumento.getText(),
                    txtCorreo.getText(), txtTelefono.getText());
            JOptionPane.showMessageDialog(this, "Propietario guardado correctamente.");
            limpiar();
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            PropietarioDTO p = controlador.buscarPorId(id);
            txtNombre.setText(p.getNombre());
            txtDocumento.setText(p.getDocumento());
            txtCorreo.setText(p.getCorreo());
            txtTelefono.setText(p.getTelefono());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            controlador.actualizarPropietario(id, txtNombre.getText(), txtDocumento.getText(),
                    txtCorreo.getText(), txtTelefono.getText());
            JOptionPane.showMessageDialog(this, "Propietario actualizado correctamente.");
            limpiar();
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            controlador.eliminarPropietario(id);
            JOptionPane.showMessageDialog(this, "Propietario eliminado.");
            limpiar();
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtDocumento.setText("");
        txtCorreo.setText("");
        txtTelefono.setText("");
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<PropietarioDTO> propietarios = controlador.obtenerTodos();
        for (PropietarioDTO p : propietarios) {
            modeloTabla.addRow(new Object[]{
                    p.getId(), p.getNombre(), p.getDocumento(), p.getCorreo(), p.getTelefono()
            });
        }
    }
}

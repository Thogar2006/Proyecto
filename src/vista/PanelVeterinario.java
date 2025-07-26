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
import singleton.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelVeterinario extends JPanel {

    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtDocumento = new JTextField(15);
    private final JTextField txtTelefono = new JTextField(15);
    private final JTextField txtCorreo = new JTextField(15);
    private final JTextField txtEspecialidad = new JTextField(15);

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");

    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final VeterinarioControlador controlador;

    public PanelVeterinario() {
        controlador = new VeterinarioControlador(Singleton.getInstance().getVeterinarioDAO());
        setLayout(new BorderLayout());

        // --- Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del Veterinario"));

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
        panelFormulario.add(new JLabel("Especialidad:"));
        panelFormulario.add(txtEspecialidad);

        add(panelFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Documento", "Teléfono", "Correo", "Especialidad"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Veterinarios"));

        add(scrollTabla, BorderLayout.CENTER);

        // --- Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // --- Eventos ---
        btnGuardar.addActionListener(e -> guardar());
        btnBuscar.addActionListener(e -> buscar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        actualizarTabla();
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0); // limpiar
        List<VeterinarioDTO> lista = Singleton.getInstance().getVeterinarioDAO().obtenerTodos();
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

    private void guardar() {
        try {
            controlador.agregarVeterinario(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    txtDocumento.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText(),
                    txtEspecialidad.getText()
            );
            JOptionPane.showMessageDialog(this, "Veterinario guardado correctamente.");
            actualizarTabla();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "ID inválido.");
        } catch (CampoVacioException | EntidadDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            VeterinarioDTO v = controlador.buscarPorId(id);
            txtNombre.setText(v.getNombre());
            txtDocumento.setText(v.getDocumento());
            txtTelefono.setText(v.getTelefono());
            txtCorreo.setText(v.getCorreo());
            txtEspecialidad.setText(v.getEspecialidad());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void editar() {
        try {
            controlador.actualizarVeterinario(
                    Integer.parseInt(txtId.getText()),
                    txtNombre.getText(),
                    txtDocumento.getText(),
                    txtTelefono.getText(),
                    txtCorreo.getText(),
                    txtEspecialidad.getText()
            );
            JOptionPane.showMessageDialog(this, "Veterinario actualizado.");
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void eliminar() {
        try {
            controlador.eliminarVeterinario(Integer.parseInt(txtId.getText()));
            JOptionPane.showMessageDialog(this, "Veterinario eliminado.");
            actualizarTabla();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }
}
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.MascotaControlador;
import dto.MascotaDTO;
import dto.PropietarioDTO;
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import persistencia.Singleton;

public class PanelMascota extends JPanel {

    private JTextField txtId, txtNombre, txtEspecie, txtRaza, txtEdad;
    private JComboBox<PropietarioDTO> comboPropietario;
    private JTable tabla;
    private DefaultTableModel modelo;

    private final MascotaControlador controlador;

    public PanelMascota() {
        controlador = Singleton.getInstancia().getMascotaControlador();
        setLayout(new BorderLayout());

        // Panel superior - formulario
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));

        txtId = new JTextField();
        txtNombre = new JTextField();
        txtEspecie = new JTextField();
        txtRaza = new JTextField();
        txtEdad = new JTextField();
        comboPropietario = new JComboBox<>();

        panelFormulario.add(new JLabel("ID:"));
        panelFormulario.add(txtId);
        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Especie:"));
        panelFormulario.add(txtEspecie);
        panelFormulario.add(new JLabel("Raza:"));
        panelFormulario.add(txtRaza);
        panelFormulario.add(new JLabel("Edad:"));
        panelFormulario.add(txtEdad);
        panelFormulario.add(new JLabel("Propietario:"));
        panelFormulario.add(comboPropietario);

        // Panel inferior - botones
        JPanel panelBotones = new JPanel();
        JButton btnGuardar = new JButton("Guardar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnLimpiar);

        // Tabla
        modelo = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "Propietario"}, 0);
        tabla = new JTable(modelo);
        JScrollPane scroll = new JScrollPane(tabla);

        // Agregar paneles
        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);

        // Agregar a la vista principal
        add(panelSuperior, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        cargarComboPropietarios();
        cargarTabla();

        // Acciones
        btnGuardar.addActionListener(e -> guardar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());
        btnLimpiar.addActionListener(e -> limpiar());

        tabla.getSelectionModel().addListSelectionListener(e -> cargarSeleccion());
    }

    private void cargarComboPropietarios() {
        comboPropietario.removeAllItems();
        List<PropietarioDTO> propietarios = Singleton.getInstancia().getPropietarioControlador().obtenerTodos();
        for (PropietarioDTO p : propietarios) {
            comboPropietario.addItem(p);
        }
    }

    private void cargarTabla() {
        modelo.setRowCount(0);
        for (MascotaDTO m : controlador.obtenerTodos()) {
            modelo.addRow(new Object[]{
                    m.getId(), m.getNombre(), m.getEspecie(),
                    m.getRaza(), m.getEdad(), m.getPropietario().getNombre()
            });
        }
    }

    private void guardar() {
        try {
            MascotaDTO m = obtenerMascotaDesdeFormulario();
            controlador.agregar(m);
            Singleton.getInstancia().guardarDatos();
            cargarTabla();
            limpiar();
            JOptionPane.showMessageDialog(this, "Mascota guardada.");
        } catch (CampoVacioException | EntidadDuplicadaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void editar() {
        try {
            MascotaDTO m = obtenerMascotaDesdeFormulario();
            controlador.actualizar(m);
            Singleton.getInstancia().guardarDatos();
            cargarTabla();
            limpiar();
            JOptionPane.showMessageDialog(this, "Mascota actualizada.");
        } catch (CampoVacioException | EntidadNoEncontradaException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminar() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            int id = Integer.parseInt(tabla.getValueAt(fila, 0).toString());
            try {
                controlador.eliminar(id);
                Singleton.getInstancia().guardarDatos();
                cargarTabla();
                limpiar();
                JOptionPane.showMessageDialog(this, "Mascota eliminada.");
            } catch (EntidadNoEncontradaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void limpiar() {
        txtId.setText("");
        txtNombre.setText("");
        txtEspecie.setText("");
        txtRaza.setText("");
        txtEdad.setText("");
        comboPropietario.setSelectedIndex(-1);
        tabla.clearSelection();
    }

    private MascotaDTO obtenerMascotaDesdeFormulario() throws CampoVacioException {
        try {
            int id = Integer.parseInt(txtId.getText().trim());
            String nombre = txtNombre.getText().trim();
            String especie = txtEspecie.getText().trim();
            String raza = txtRaza.getText().trim();
            int edad = Integer.parseInt(txtEdad.getText().trim());
            PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();

            return new MascotaDTO(id, nombre, especie, raza, edad, propietario);
        } catch (NumberFormatException e) {
            throw new CampoVacioException("ID y Edad deben ser números válidos.");
        } catch (NullPointerException e) {
            throw new CampoVacioException("Debe seleccionar un propietario.");
        }
    }

    private void cargarSeleccion() {
        int fila = tabla.getSelectedRow();
        if (fila != -1) {
            txtId.setText(tabla.getValueAt(fila, 0).toString());
            txtNombre.setText(tabla.getValueAt(fila, 1).toString());
            txtEspecie.setText(tabla.getValueAt(fila, 2).toString());
            txtRaza.setText(tabla.getValueAt(fila, 3).toString());
            txtEdad.setText(tabla.getValueAt(fila, 4).toString());

            String propietarioNombre = tabla.getValueAt(fila, 5).toString();
            for (int i = 0; i < comboPropietario.getItemCount(); i++) {
                if (comboPropietario.getItemAt(i).getNombre().equals(propietarioNombre)) {
                    comboPropietario.setSelectedIndex(i);
                    break;
                }
            }
        }
    }
}

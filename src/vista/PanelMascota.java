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
import singleton.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PanelMascota extends JPanel {

    private final JTextField txtId = new JTextField(10);
    private final JTextField txtNombre = new JTextField(15);
    private final JTextField txtEspecie = new JTextField(15);
    private final JTextField txtRaza = new JTextField(15);
    private final JTextField txtEdad = new JTextField(5);

    private final JComboBox<String> comboPropietario = new JComboBox<>();

    private final JButton btnGuardar = new JButton("Guardar");
    private final JButton btnBuscar = new JButton("Buscar");
    private final JButton btnEditar = new JButton("Editar");
    private final JButton btnEliminar = new JButton("Eliminar");

    private final JTable tabla;
    private final DefaultTableModel modeloTabla;

    private final MascotaControlador controlador;

    public PanelMascota() {
        
        controlador = new MascotaControlador(Singleton.getInstance().getMascotaDAO());

        setLayout(new BorderLayout());

        // --- Panel Formulario ---
        JPanel panelFormulario = new JPanel(new GridLayout(6, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos de la Mascota"));

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

        add(panelFormulario, BorderLayout.NORTH);

        // --- Tabla ---
        modeloTabla = new DefaultTableModel(new String[]{"ID", "Nombre", "Especie", "Raza", "Edad", "Propietario"}, 0);
        tabla = new JTable(modeloTabla);
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.setBorder(BorderFactory.createTitledBorder("Lista de Mascotas"));

        add(scrollTabla, BorderLayout.CENTER);

        // --- Botones ---
        JPanel panelBotones = new JPanel(new FlowLayout());
        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        add(panelBotones, BorderLayout.SOUTH);

        // Eventos
        btnGuardar.addActionListener(e -> guardar());
        btnBuscar.addActionListener(e -> buscar());
        btnEditar.addActionListener(e -> editar());
        btnEliminar.addActionListener(e -> eliminar());

        cargarComboPropietarios();
        actualizarTabla();
    }

    private void cargarComboPropietarios() {
        comboPropietario.removeAllItems();
        List<PropietarioDTO> propietarios = Singleton.getInstance().getPropietarioDAO().obtenerTodos();
        for (PropietarioDTO p : propietarios) {
            comboPropietario.addItem(p.getId() + " - " + p.getNombre());
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        List<MascotaDTO> mascotas = Singleton.getInstance().getMascotaDAO().obtenerTodas();
        for (MascotaDTO m : mascotas) {
            modeloTabla.addRow(new Object[]{
                    m.getId(),
                    m.getNombre(),
                    m.getEspecie(),
                    m.getRaza(),
                    m.getEdad(),
                    m.getPropietario().getNombre()
            });
        }
    }

    private void guardar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String especie = txtEspecie.getText();
            String raza = txtRaza.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            PropietarioDTO propietario = obtenerPropietarioSeleccionado();

            controlador.agregarMascota(id, nombre, especie, raza, edad, propietario);
            JOptionPane.showMessageDialog(this, "Mascota registrada.");
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void buscar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            MascotaDTO m = controlador.buscarPorId(id);
            txtNombre.setText(m.getNombre());
            txtEspecie.setText(m.getEspecie());
            txtRaza.setText(m.getRaza());
            txtEdad.setText(String.valueOf(m.getEdad()));
            comboPropietario.setSelectedItem(m.getPropietario().getId() + " - " + m.getPropietario().getNombre());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void editar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            String nombre = txtNombre.getText();
            String especie = txtEspecie.getText();
            String raza = txtRaza.getText();
            int edad = Integer.parseInt(txtEdad.getText());
            PropietarioDTO propietario = obtenerPropietarioSeleccionado();

            controlador.actualizarMascota(id, nombre, especie, raza, edad, propietario);
            JOptionPane.showMessageDialog(this, "Mascota actualizada.");
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private void eliminar() {
        try {
            int id = Integer.parseInt(txtId.getText());
            controlador.eliminarMascota(id);
            JOptionPane.showMessageDialog(this, "Mascota eliminada.");
            actualizarTabla();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
    }

    private PropietarioDTO obtenerPropietarioSeleccionado() throws EntidadNoEncontradaException {
        String seleccionado = (String) comboPropietario.getSelectedItem();
        if (seleccionado == null || !seleccionado.contains("-")) {
            throw new EntidadNoEncontradaException("Debe seleccionar un propietario válido.");
        }
        int idPropietario = Integer.parseInt(seleccionado.split(" - ")[0].trim());
        return Singleton.getInstance().getPropietarioDAO().buscarPorId(idPropietario);
    }
}

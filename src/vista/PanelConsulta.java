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
import excepciones.CampoVacioException;
import excepciones.EntidadDuplicadaException;
import excepciones.EntidadNoEncontradaException;
import singleton.Singleton;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PanelConsulta extends JPanel {

    private final JTextField txtId = new JTextField(5);
    private final JComboBox<MascotaDTO> comboMascota = new JComboBox<>();
    private final JComboBox<PropietarioDTO> comboPropietario = new JComboBox<>();
    private final JComboBox<VeterinarioDTO> comboVeterinario = new JComboBox<>();
    private final JTextField txtFechaHora = new JTextField(20);
    private final JTextField txtDiagnostico = new JTextField(20);
    private final JTextField txtTratamiento = new JTextField(20);
    private final DefaultTableModel modeloTabla = new DefaultTableModel();
    private final JTable tabla = new JTable(modeloTabla);

    private final ConsultaControlador controlador;

    public PanelConsulta() {
        this.controlador = Singleton.getInstance().getConsultaControlador();

        setLayout(new BorderLayout());

        // Panel de formulario
        JPanel panelForm = new JPanel(new GridLayout(8, 2));
        panelForm.add(new JLabel("ID:"));
        panelForm.add(txtId);
        panelForm.add(new JLabel("Mascota:"));
        panelForm.add(comboMascota);
        panelForm.add(new JLabel("Propietario:"));
        panelForm.add(comboPropietario);
        panelForm.add(new JLabel("Veterinario:"));
        panelForm.add(comboVeterinario);
        panelForm.add(new JLabel("Fecha y Hora (yyyy-MM-ddTHH:mm):"));
        panelForm.add(txtFechaHora);
        panelForm.add(new JLabel("Diagnóstico:"));
        panelForm.add(txtDiagnostico);
        panelForm.add(new JLabel("Tratamiento:"));
        panelForm.add(txtTratamiento);

        JButton btnGuardar = new JButton("Guardar");
        JButton btnBuscar = new JButton("Buscar");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnGuardar);
        panelBotones.add(btnBuscar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);

        // Tabla
        modeloTabla.addColumn("ID");
        modeloTabla.addColumn("Mascota");
        modeloTabla.addColumn("Propietario");
        modeloTabla.addColumn("Veterinario");
        modeloTabla.addColumn("Fecha");
        modeloTabla.addColumn("Diagnóstico");
        modeloTabla.addColumn("Tratamiento");

        JScrollPane scrollPane = new JScrollPane(tabla);

        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        cargarCombos();
        actualizarTabla();

        btnGuardar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
                PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
                VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText());
                String diagnostico = txtDiagnostico.getText();
                String tratamiento = txtTratamiento.getText();

                controlador.agregarConsulta(id, mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Consulta registrada con éxito");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "ID o fecha inválida");
            } catch (CampoVacioException | EntidadDuplicadaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnBuscar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                ConsultaDTO consulta = controlador.buscarPorId(id);
                comboMascota.setSelectedItem(consulta.getMascota());
                comboPropietario.setSelectedItem(consulta.getPropietario());
                comboVeterinario.setSelectedItem(consulta.getVeterinario());
                txtFechaHora.setText(consulta.getFechaHora().toString());
                txtDiagnostico.setText(consulta.getDiagnostico());
                txtTratamiento.setText(consulta.getTratamiento());
            } catch (NumberFormatException | EntidadNoEncontradaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnEditar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                MascotaDTO mascota = (MascotaDTO) comboMascota.getSelectedItem();
                PropietarioDTO propietario = (PropietarioDTO) comboPropietario.getSelectedItem();
                VeterinarioDTO veterinario = (VeterinarioDTO) comboVeterinario.getSelectedItem();
                LocalDateTime fechaHora = LocalDateTime.parse(txtFechaHora.getText());
                String diagnostico = txtDiagnostico.getText();
                String tratamiento = txtTratamiento.getText();

                controlador.actualizarConsulta(id, mascota, propietario, veterinario, fechaHora, diagnostico, tratamiento);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Consulta actualizada");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });

        btnEliminar.addActionListener(e -> {
            try {
                int id = Integer.parseInt(txtId.getText());
                controlador.eliminarConsulta(id);
                actualizarTabla();
                JOptionPane.showMessageDialog(this, "Consulta eliminada");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage());
            }
        });
    }

    private void cargarCombos() {
        comboMascota.removeAllItems();
        comboPropietario.removeAllItems();
        comboVeterinario.removeAllItems();

        for (MascotaDTO m : Singleton.getInstance().getMascotaDAO().obtenerTodas()) {
            comboMascota.addItem(m);
        }
        for (PropietarioDTO p : Singleton.getInstance().getPropietarioDAO().obtenerTodos()) {
            comboPropietario.addItem(p);
        }
        for (VeterinarioDTO v : Singleton.getInstance().getVeterinarioDAO().obtenerTodos()) {
            comboVeterinario.addItem(v);
        }
    }

    private void actualizarTabla() {
        modeloTabla.setRowCount(0);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        for (ConsultaDTO c : controlador.obtenerTodas()) {
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
}

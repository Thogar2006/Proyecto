/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.CitaControlador;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import singleton.Singleton;

/**
 *
 * @author bossstore
 */
public class MainFrame extends JFrame {

    private JTabbedPane tabbedPane;

    public MainFrame() {
        setTitle("Sistema de Gestión Clínica Veterinaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); // Centrar ventana

        tabbedPane = new JTabbedPane();

        // Agregar los paneles
        tabbedPane.addTab("Propietarios", new PanelPropietario());
        tabbedPane.addTab("Veterinario", new PanelVeterinario());
        tabbedPane.addTab("Mascotas", new PanelMascota());
        tabbedPane.addTab("Citas", new PanelCita(Singleton.getInstance().getCitaControlador()));
        tabbedPane.addTab("Consulta", new PanelConsulta());
        tabbedPane.addTab("Vacunas", new PanelVacuna());

        // Luego agregaremos: Veterinario, Mascota, etc.

        add(tabbedPane);
    }
    

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MainFrame().setVisible(true);
        });
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import javax.swing.*;

public class JFramePrincipal extends JFrame {

    public JFramePrincipal() {
        setTitle("Sistema de Gestión Clínica Veterinaria");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 600);
        setLocationRelativeTo(null); // Centrar ventana

        JTabbedPane tabs = new JTabbedPane();

        // Agregar paneles
        tabs.addTab("Propietarios", new PanelPropietario());
        tabs.addTab("Veterinarios", new PanelVeterinario());
        tabs.addTab("Mascota", new PanelMascota());
        tabs.addTab("Cita", new PanelCita());
        tabs.addTab("Consulta", new PanelConsulta());
        tabs.addTab("Vacuna", new PanelVacuna());
        

        add(tabs);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new JFramePrincipal().setVisible(true);
        });
    }
}

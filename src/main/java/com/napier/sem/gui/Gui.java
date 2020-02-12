package com.napier.sem.gui;

import javax.swing.*;
import java.awt.*;

public class Gui {
        private GuiComponents components = new GuiComponents();

        public Gui()
        {
            setupFrame();
            setupPanels();
            setupButtons();
            addPanelComponents();
            showFrame();
            applyCloseOperation();
        }

        private void setupFrame()
        {
            components.frame.setSize(1000, 700);
            components.frame.setLayout(null);

        }

        private void setupPanels()
        {
            components.panel.setBounds(components.frame.getBounds());
            components.panel.setLayout(null);
            components.panel.setBackground(Color.DARK_GRAY);
            components.frame.setContentPane(components.panel);
        }

        private void setupButtons()
        {
            components.btnCountryReport.setBounds(50, 50, 200, 40);
            components.btnCountryReport.setText("Print Country Report");
        }

        private void addPanelComponents()
        {
            components.panel.add(components.btnCountryReport);
        }

        private void showFrame()
        {
            components.frame.setVisible(true);
        }

        private void applyCloseOperation()
        {
            components.frame.setDefaultCloseOperation((WindowConstants.EXIT_ON_CLOSE));
        }

    public GuiComponents getGuiComponents() {
        return components;
    }
}

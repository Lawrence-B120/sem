package com.napier.sem.gui;

import com.napier.sem.App;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GuiListener {

    private Gui gui = App.getGui();
    private GuiComponents components = gui.getGuiComponents();

    public GuiListener()
    {
        addMouseListener();
    }

    private void addMouseListener()
    {
        components.btnCountryReport.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                System.out.println("Works");
            }
        });
    }
}

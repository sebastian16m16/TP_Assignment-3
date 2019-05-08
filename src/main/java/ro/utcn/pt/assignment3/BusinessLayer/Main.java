package ro.utcn.pt.assignment3.BusinessLayer;

import ro.utcn.pt.assignment3.PresentationLayer.MenuGUI;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                MenuGUI menuGUI = new MenuGUI();
                menuGUI.setVisible(true);
            }
        });

    }
}

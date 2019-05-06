package ro.utcn.pt.assignment3.PresentationLayer;

import ro.utcn.pt.assignment3.DataLayer.ProductOp;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuGUI extends JFrame{
    private JButton clientOperationsButton;
    private JButton productOperationsButton;
    private JButton productOrderButton;
    private JPanel menuPanel;

    public MenuGUI(){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("Menu");
        setLocationRelativeTo(null);

        add(menuPanel);

        clientOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ClientOpGUI clientOpGUI = new ClientOpGUI();
                clientOpGUI.setVisible(true);
                setVisible(false);
            }
        });

        productOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProductOpGUI productOpGUI = new ProductOpGUI();
                productOpGUI.setVisible(true);
                setVisible(false);
            }
        });

        productOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OrderGUI orderGUI = new OrderGUI();
                orderGUI.setVisible(true);
                setVisible(false);
            }
        });
    }
}

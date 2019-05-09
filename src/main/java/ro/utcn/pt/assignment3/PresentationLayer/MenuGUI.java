package ro.utcn.pt.assignment3.PresentationLayer;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  Opens the menu Frame (window)
 * */

public class MenuGUI extends JFrame{
    private JButton clientOperationsButton;
    private JButton productOperationsButton;
    private JButton productOrderButton;
    private JPanel menuPanel;

    /**
     * Constructor of the JFrame
     * */
    public MenuGUI(){
        super();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 400);
        setTitle("Menu");
        setLocationRelativeTo(null);
        add(menuPanel);

        /**
         * Button opens Client Operations window
         * */
        clientOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ClientOpGUI clientOpGUI = new ClientOpGUI();
                        clientOpGUI.setVisible(true);
                        setVisible(false);
                    }
                });

            }
        });

        /**
         * Button opens Product Operation Window
         * */
        productOperationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        ProductOpGUI productOpGUI = new ProductOpGUI();
                        productOpGUI.setVisible(true);
                        setVisible(false);
                    }
                });

            }
        });


        /**
         *  Button opens the Orders Window
         * */
        productOrderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        OrderGUI orderGUI = new OrderGUI();
                        orderGUI.setVisible(true);
                        setVisible(false);
                    }
                });

            }
        });
    }
}

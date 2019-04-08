package CodeForServer;

import javax.swing.*;
import java.awt.*;

abstract class absGUI extends absCaches {

    final JFrame mainFrame = new JFrame();
    final JPanel topPanel = new JPanel();
    final JPanel bottomPanel = new JPanel();
    final JPanel basePanel = new JPanel();

    void setGUI(String dspName) {
        System.out.println("Displaying " + dspName + " screen...");
        mainFrame.getContentPane().removeAll();
        topPanel.removeAll();
        bottomPanel.removeAll();
        basePanel.removeAll();
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setTitle(dspName);
        topPanel.setOpaque(false);
        bottomPanel.setOpaque(false);
        basePanel.setOpaque(false);
        basePanel.setLayout(new BorderLayout(5, 5));
        basePanel.add(topPanel, BorderLayout.LINE_START);
        basePanel.add(bottomPanel, BorderLayout.PAGE_END);
    }

    void setGUI(JLabel imageLabel) {
        imageLabel.setLayout(new GridBagLayout());
        imageLabel.add(basePanel);
        mainFrame.setContentPane(imageLabel);
        mainFrame.setVisible(true);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setLocationRelativeTo(null);
        mainFrame.getContentPane().repaint();
        mainFrame.validate();
        mainFrame.pack();
    }
}

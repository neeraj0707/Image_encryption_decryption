import javax.swing.*;
import java.awt.*;
import java.io.*;

public class index {

    public static void operate(int key, boolean encrypt) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.showOpenDialog(null);
        File file = fileChooser.getSelectedFile();

        try {
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[fis.available()];
            fis.read(data);

            for (int i = 0; i < data.length; i++) {
                data[i] = (byte) (encrypt ? data[i] ^ key : data[i] ^ key); // Encrypt or decrypt based on the flag
            }

            FileOutputStream fos = new FileOutputStream(file);
            fos.write(data);
            fos.close();
            fis.close();

            JOptionPane.showMessageDialog(null, encrypt ? "Image Encrypted Successfully!" : "Image Decrypted Successfully!");

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "An error occurred: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Image Operation");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel panel = new JPanel();
            GroupLayout layout = new GroupLayout(panel);
            panel.setLayout(layout);
            layout.setAutoCreateGaps(true);
            layout.setAutoCreateContainerGaps(true);

            JLabel keyLabel = new JLabel("Encryption Key:");
            JTextField keyTextField = new JTextField(10);
            JButton encryptButton = new JButton("Encrypt Image");
            JButton decryptButton = new JButton("Decrypt Image");

            encryptButton.addActionListener(e -> {
                String keyText = keyTextField.getText();
                if (!keyText.isEmpty()) {
                    int key = Integer.parseInt(keyText);
                    operate(key, true);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a key!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            decryptButton.addActionListener(e -> {
                String keyText = keyTextField.getText();
                if (!keyText.isEmpty()) {
                    int key = Integer.parseInt(keyText);
                    operate(key, false);
                } else {
                    JOptionPane.showMessageDialog(null, "Please enter a key!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            layout.setHorizontalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(keyLabel)
                            .addComponent(encryptButton)
                            .addComponent(decryptButton))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(keyTextField)));

            layout.setVerticalGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(keyLabel)
                            .addComponent(keyTextField))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(encryptButton))
                    .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(decryptButton)));

            frame.add(panel);
            frame.pack();
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}

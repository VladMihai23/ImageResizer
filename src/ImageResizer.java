import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageResizer extends JFrame implements ActionListener {

    private JTextField widthField, heightField;
    private JLabel originalLabel, resizedLabel;
    private BufferedImage originalImage, resizedImage;
    private JButton openButton, resizeButton, saveButton;
    private long processingTime;

    public ImageResizer() {
        setTitle("Image Resizer");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        JPanel controlPanel = new JPanel();

        openButton = new JButton("Open Image");
        resizeButton = new JButton("Resize Image");
        saveButton = new JButton("Save Image");

        openButton.addActionListener(this);
        resizeButton.addActionListener(this);
        saveButton.addActionListener(this);

        widthField = new JTextField(5);
        heightField = new JTextField(5);

        controlPanel.add(new JLabel("Width:"));
        controlPanel.add(widthField);
        controlPanel.add(new JLabel("Height:"));
        controlPanel.add(heightField);
        controlPanel.add(openButton);
        controlPanel.add(resizeButton);
        controlPanel.add(saveButton);

        originalLabel = new JLabel();
        resizedLabel = new JLabel();

        JPanel imagePanel = new JPanel(new GridLayout(1, 2));
        imagePanel.add(new JScrollPane(originalLabel));
        imagePanel.add(new JScrollPane(resizedLabel));

        add(controlPanel, BorderLayout.NORTH);
        add(imagePanel, BorderLayout.CENTER);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == openButton) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    File file = fileChooser.getSelectedFile();
                    originalImage = ImageIO.read(file);
                    originalLabel.setIcon(new ImageIcon(originalImage));
                } catch (IOException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error loading image.");
                }
            }
        } else if (e.getSource() == resizeButton) {
            if (originalImage == null) {
                JOptionPane.showMessageDialog(this, "Please open an image before resizing.");
                return;
            }

            try {
                int newWidth = Integer.parseInt(widthField.getText());
                int newHeight = Integer.parseInt(heightField.getText());

                resizeButton.setEnabled(false);
                long startTime = System.nanoTime();

                new SwingWorker<BufferedImage, Void>() {
                    @Override
                    protected BufferedImage doInBackground() throws Exception {
                        return resizeImage(originalImage, newWidth, newHeight);
                    }

                    @Override
                    protected void done() {
                        try {
                            resizedImage = get();
                            resizedLabel.setIcon(new ImageIcon(resizedImage));
                            long endTime = System.nanoTime();
                            processingTime = (endTime - startTime) / 1_000_000;
                            JOptionPane.showMessageDialog(ImageResizer.this, "Processing time: " + processingTime + " ms");
                        } catch (Exception ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(ImageResizer.this, "Error during resizing.");
                        } finally {
                            resizeButton.setEnabled(true);
                        }
                    }
                }.execute();

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Please enter valid numbers for width and height.");
            }

        } else if (e.getSource() == saveButton) {
            if (resizedImage == null) {
                JOptionPane.showMessageDialog(this, "Please resize the image before saving.");
                return;
            }

            try {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Image");
                int result = fileChooser.showSaveDialog(this);

                if (result == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    String fileName = file.getName().toLowerCase();

                    if (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg")) {
                        ImageIO.write(resizedImage, "jpg", file);
                        JOptionPane.showMessageDialog(this, "Image saved successfully as JPG.");
                    } else if (fileName.endsWith(".png")) {
                        ImageIO.write(resizedImage, "png", file);
                        JOptionPane.showMessageDialog(this, "Image saved successfully as PNG.");
                    } else {
                        JOptionPane.showMessageDialog(this, "Please save the file with a valid extension (.png or .jpg).");
                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error saving image.");
            }
        }
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int width, int height) {
        int imageType = originalImage.getColorModel().hasAlpha() ? BufferedImage.TYPE_INT_ARGB : BufferedImage.TYPE_INT_RGB;
        Image temp = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        BufferedImage resized = new BufferedImage(width, height, imageType);

        Graphics2D g2d = resized.createGraphics();
        g2d.drawImage(temp, 0, 0, null);
        g2d.dispose();

        return resized;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new ImageResizer().setVisible(true);
        });
    }
}

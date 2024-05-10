import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Controlador extends JFrame {

    Controlador() {
        configControlador();
    }

    private Modelo modelo;
    private Vista0 vista;

    private JPanel mainPanel;

    private JScrollPane scrollPane;
    private JScrollBar scrollBar;
    private JTextArea textArea;


    private JPanel searchPanel;
    private JLabel search = new JLabel("                     Buscar texto: ");
    private JTextField searchText = new JTextField();
    private JButton findText = new JButton("Encontrar texto");
    private JButton findNext = new JButton("Encontrar siguiente");

    private JPanel savePanel;
    private JButton saveButton = new JButton("Guardar archivo");
    private JButton loadButton = new JButton("Cargar archivo");

    private JPanel infoPanel;
    private JLabel info = new JLabel("Panel de información");


    public void configControlador(){

        int i = 1;
        UIManager.LookAndFeelInfo looks[];
        looks = UIManager.getInstalledLookAndFeels();
        try{
            UIManager.setLookAndFeel(looks[i].getClassName());
            SwingUtilities.updateComponentTreeUI(this);
        } catch(Exception e) {}


        modelo = new Modelo();
        vista = new Vista0(this, modelo);
        modelo.addObserver(vista);


        mainPanel = new JPanel();
        mainPanel.setBackground(Color.darkGray);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        this.add(mainPanel);


        //Panel de texto
        textArea = new JTextArea();
        scrollPane = new JScrollPane(textArea, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        //Panel de búsqueda
        searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 2, 10, 10));
        searchPanel.add(search);
        searchPanel.add(searchText);
        searchPanel.add(findText);
        searchPanel.add(findNext);
        findNext.setEnabled(false);
        Border border = BorderFactory.createEmptyBorder(20, 30, 20, 30);
        searchPanel.setBorder(border);

        //Panel de guardar
        savePanel = new JPanel();
        savePanel.setLayout(new GridLayout(1, 2, 10, 10));
        savePanel.add(saveButton);
        savePanel.add(loadButton);

        Border border2 = BorderFactory.createEmptyBorder(10, 30, 10, 30);
        savePanel.setBorder(border2);



        //Panel de información
        infoPanel = new JPanel();
        infoPanel.setLayout(new FlowLayout());
        infoPanel.add(info, BorderLayout.CENTER);
        Border border3 = BorderFactory.createEmptyBorder(22, 0, 0, 0);
        infoPanel.setBorder(border3);


        mainPanel.add(scrollPane);
        mainPanel.add(searchPanel);
        mainPanel.add(savePanel);
        mainPanel.add(infoPanel);


        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(scrollPane);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(searchPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(savePanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createHorizontalGlue());
        mainPanel.add(infoPanel);
        mainPanel.add(Box.createVerticalGlue());
        mainPanel.add(Box.createHorizontalGlue());



        scrollPane.setPreferredSize(new Dimension(500, 280));
        searchPanel.setPreferredSize(new Dimension(500, 100));
        savePanel.setPreferredSize(new Dimension(500, 50));
        infoPanel.setPreferredSize(new Dimension(500, 70));



        //BOTONES

        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                TXTFilter.agregarFiltrotxt(fileChooser);
                int returnValue = fileChooser.showOpenDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    modelo.cargarArchivo(selectedFile);
                }


            }});
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                TXTFilter.agregarFiltrotxt(fileChooser); // Add TXT filter

                int returnValue = fileChooser.showSaveDialog(null);
                if (returnValue == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Ensure extension is added if not provided
                    String fileName = selectedFile.getName();
                    if (!fileName.toLowerCase().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getParent(), fileName + ".txt");
                    }

                    try {
                        modelo.guardarArchivo(textArea.getText(), selectedFile);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        findText.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.buscarTexto(searchText.getText(), textArea.getText(), textArea);

            }
        });

        findNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                modelo.buscarSiguiente(searchText.getText(), textArea.getText(), textArea);
            }
        });


        setTitle("Controlador");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public JTextArea getTextArea() {
        return textArea;
    }

    public JLabel getInfo() {
        return info;
    }
    public JButton getFindNext() {
        return findNext;
    }

}

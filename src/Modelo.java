import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import java.awt.*;
import java.io.*;
import java.util.Observable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Modelo extends Observable {

    private String texto;
    private int posicion, posicion2;
    private boolean encontrado, cargar;




    public Modelo() {
        this.texto = "";
        this.posicion = -1;
    }

    public void cargarArchivo(File archivo){
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                texto += linea + "\n";
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        cargar = true;

        setChanged();
        notifyObservers();
    }

    public void guardarArchivo(String contenido, File archivo) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(archivo))) {
            writer.write(contenido);
        }
    }

    public void buscarTexto(String textoBuscado, String textA, JTextArea textArea) {
        texto = textA;
        String searchTerm = textoBuscado.toLowerCase();
        String text = textA.toLowerCase();
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();


        for (Highlighter.Highlight highlight : highlights) {
            highlighter.removeHighlight(highlight);
        }

         posicion = -1;

        if (!searchTerm.isEmpty()) {
            Pattern pattern = Pattern.compile("\\b" + searchTerm + "\\b");
            Matcher matcher = pattern.matcher(text);

            while (matcher.find()) {
                try {
                    highlighter.addHighlight(matcher.start(), matcher.end(), new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
                    posicion = matcher.start();
                    break;
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }

        if (posicion != -1) {
            encontrado = true;
            textArea.select(posicion, posicion + searchTerm.length());
        }else{
            encontrado = false;
        }
        setChanged();
        notifyObservers();

    }

    public void buscarSiguiente(String textoBuscado, String textA, JTextArea textArea) {
        texto = textA;
        String searchTerm = textoBuscado.toLowerCase();
        String text = textA.toLowerCase();
        Highlighter highlighter = textArea.getHighlighter();
        Highlighter.Highlight[] highlights = highlighter.getHighlights();

        for (Highlighter.Highlight highlight : highlights) {
            highlighter.removeHighlight(highlight);
        }
        posicion2 = posicion;

        if (!searchTerm.isEmpty()) {
            Pattern pattern = Pattern.compile("\\b" + searchTerm + "\\b");
            Matcher matcher = pattern.matcher(text);
            matcher.region(posicion + 1, text.length());

            while (matcher.find()){
                try {
                    highlighter.addHighlight(matcher.start(), matcher.end(), new DefaultHighlighter.DefaultHighlightPainter(Color.RED));
                    posicion = matcher.start();
                    break;
                } catch (BadLocationException ex) {
                    ex.printStackTrace();
                }
            }
        }
        if(posicion == posicion2){
            encontrado = false;
        }else{
            encontrado = true;
        }
        setChanged();
        notifyObservers();

    }





    public boolean isCargar() {
        return cargar;
    }

    public void setCargar(boolean cargar) {
        this.cargar = cargar;
    }

    public String getTexto() {
        return texto;
    }

    public int getPosicion() {
        return posicion;
    }

    public boolean isEncontrado() {
        return encontrado;
    }

}

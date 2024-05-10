import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import java.io.File;

public class TXTFilter extends FileFilter {

    @Override
    public boolean accept(File file) {
        if (file.isDirectory()) {
            return true;
        }
        String fileName = file.getName();
        return fileName.endsWith(".txt");
    }

    @Override
    public String getDescription() {
        return "Archivos TXT (*.txt)";
    }


    public static void agregarFiltrotxt(JFileChooser fileChooser) {
        fileChooser.setFileFilter(new TXTFilter());
        fileChooser.addChoosableFileFilter(new TXTFilter());
    }
}
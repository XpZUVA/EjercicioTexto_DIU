import java.util.Observable;
import java.util.Observer;

public class Vista0 implements Observer {

    private Modelo modelo;
    private Controlador controlador;

    public Vista0(Controlador controlador, Modelo modelo) {
        this.controlador = controlador;
        this.modelo = modelo;
    }

    @Override
    public void update(Observable o, Object arg) {
        if(modelo.isCargar()){
            controlador.getTextArea().setText(modelo.getTexto());
            modelo.setCargar(false);
        }

        if(modelo.isEncontrado()){
            controlador.getInfo().setText("Texto encontrado en la posición: " + modelo.getPosicion());
            controlador.getFindNext().setEnabled(true);
        } else {
            controlador.getInfo().setText("Texto no encontrado");
            controlador.getFindNext().setEnabled(false);
        }

    }

}

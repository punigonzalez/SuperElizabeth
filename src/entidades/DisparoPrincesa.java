package entidades;

import java.awt.Image;
import entorno.Entorno;
import entorno.Herramientas;

public class DisparoPrincesa {
    private double x;
    private double y;
    private final double velocidad;
    private Image img;


    public DisparoPrincesa(double x, double y, boolean haciaDerecha) {
        this.x = x;
        this.y = y;
        this.velocidad = haciaDerecha ? 5 : -5;
        this.img = Herramientas.cargarImagen("sprites/disparo.png");
    }

    public void dibujarse(Entorno entorno) {
        entorno.dibujarImagen(img, x, y, 0, 0.08);
    }

    public void mover() {
        this.x += velocidad;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}

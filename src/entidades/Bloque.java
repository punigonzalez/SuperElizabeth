package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Bloque {
	
	double x;
	double y;
	boolean rompible;
	Image img1;
	Image img2;
	
	
	
	public Bloque(double x, double y,boolean rompible) {
		this.x = x;
		this.y = y;
		this.rompible = rompible;
		this.img1 = Herramientas.cargarImagen("sprites/bloque.png");
		this.img2 = Herramientas.cargarImagen("sprites/bloque2.png");

	}
	
	
	public void dibujarse(Entorno entorno)
	{
		if (rompible) {
			entorno.dibujarImagen(img2, this.x, this.y, 0, 0.17);
			
		}else {
			entorno.dibujarImagen(img1, this.x, this.y, 0, 0.17);
		}
		
	}
	
	public void desaparecer() {
        this.x = -1000; // Mover el bloque fuera de la pantalla para que parezca que desaparece
        this.y = -1000;
    }
	
	
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean esRompible() {
        return rompible;
    }

}

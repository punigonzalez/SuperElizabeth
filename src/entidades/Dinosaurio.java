package entidades;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Dinosaurio {
	
	double x;
    double y;
    Image img1;
    Image img2;
    Image img3;
    Image img4;
    int direccion; // 0: derecha, 1: izquierda
    Integer ciclo;
    double velocidadY = 0;
    double gravedad = 0.9;
    
    public Dinosaurio(double x, double y) {
        this.x = x;
        this.y = y;
        this.direccion = 1;
        this.ciclo = 0;
        this.img1 = Herramientas.cargarImagen("sprites/dyno-quieto-izq.png");
        this.img2 = Herramientas.cargarImagen("sprites/dyno-quieto-der.png");
        this.img3 = Herramientas.cargarImagen("sprites/dyno-corriendo-izq.png");
        this.img4 = Herramientas.cargarImagen("sprites/dyno-corriendo-der.png");
    }

    public double getY() {
        return y;
    }
    public double getX() {
        return x;
    }

    public void moverDerecha() {
        this.x += 1.5;
        this.direccion = 0;
    }

    public void moverIzquierda() {
        this.x -= 1.5;
        this.direccion = 1;
    }

    public void moverAutomaticamente() {
        //cambia dirreción
        if (this.x <= 10) {
            direccion = 0;
        } else if (this.x >= 790) {
            direccion = 1;
        }
        //mueve el dinosaurio
        if (direccion == 1) {
            moverIzquierda();
        } else if (direccion == 0) {
            moverDerecha();
        }
    }

    public void dibujarse(Entorno entorno, Integer ticks) {
    	// Alterna entre 0 y 1 cada 10 ticks para cambiar la imagen de caminata
    	ciclo = (ticks / 10) % 2;
        moverAutomaticamente();
        if (direccion == 1) {
            dibujarIzquierda(entorno);
        } else if (direccion == 0) {
            dibujarDerecha(entorno);
        }
    }
    
    public void dibujarDerecha(Entorno entorno) {
        if (ciclo == 0) {
            entorno.dibujarImagen(img2, this.x, this.y, 0, 0.08);
        } else {
            entorno.dibujarImagen(img4, this.x, this.y, 0, 0.08);
        }
    }
    
    public void dibujarIzquierda(Entorno entorno) {
        if (ciclo == 0) {
            entorno.dibujarImagen(img1, this.x, this.y, 0, 0.08);
        } else {
            entorno.dibujarImagen(img3, this.x, this.y, 0, 0.08);
        }
    }

    // Método para aplicar la gravedad al dinosaurio
       public void aplicarGravedad(Bloque[] bloques) {
           boolean hayBloqueDebajo = false;

           for (Bloque bloque : bloques) {
               if (bloque != null && colisionaConParteInferior(bloque)) {
                   hayBloqueDebajo = true;
                   aterrizar(bloque.getY() - 30);
                   break;
               }
           }

           if (!hayBloqueDebajo) {
               this.y += velocidadY;
               velocidadY += gravedad;
           } else {
               velocidadY = 0;
           }
       }

       public boolean colisionaConParteInferior(Bloque bloque) {
           return (x < bloque.getX() + 30 && x + 30 > bloque.getX() &&
                   y + 30 <= bloque.getY() && y + 30 + velocidadY >= bloque.getY());
       }

       public void aterrizar(double nuevaY) {
           this.y = nuevaY;
           velocidadY = 0;
       }

}
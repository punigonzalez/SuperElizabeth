package entidades;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;

import entorno.Entorno;
import entorno.Herramientas;

public class Princesa {
	
	double x;
	double y;
	Image img1;
	Image img2;
	Image img3;
	Image img4;
	int direccion = 4;
	Integer ciclo = 0;
	boolean saltando = false;
	double velocidadSalto = 17;
	double gravedad = 0.9;
	double velocidadY = 12;
	double posicionInicialY;
	private boolean teclaSaltoPresionada = false;
	
	
	
	public Princesa(double x, double y) {
		this.setX(x);
		this.y = y;
		this.posicionInicialY = y;
		direccion = 4;
		this.img1 = Herramientas.cargarImagen("sprites/princesa-quieta-der.png");
		this.img2 = Herramientas.cargarImagen("sprites/princesa-corriendo-der.png");
		this.img3 = Herramientas.cargarImagen("sprites/princesa-quieta-izq.png");
		this.img4 = Herramientas.cargarImagen("sprites/princesa-corriendo-izq.png");
		
	}
	public double getX() {
		return x;
	}

	public void setX(double x) {
		this.x = x;
	}

	private boolean viva = true;


	public boolean estaViva() {
		return viva;
	}

	public void muerta() {
		viva = false;
	}


	public void dibujarse(Entorno entorno,Integer ticks){		
		if (saltando) {
	        this.y += velocidadY;
	        velocidadY += gravedad;
	    }
		ciclo = ticks % 40;
		
		switch (direccion) {
        case 0:
            if (ciclo < 21) {
                dibujarDerecha1(entorno);
            } else {
                dibujarDerecha2(entorno);
            }
            break;
        case 1:
            if (ciclo < 21) {
                dibujarIzquierda1(entorno);
            } else {
                dibujarIzquierda2(entorno);
            }
            break;
        case 4:
            dibujarDerecha1(entorno);
            break;
		}
		
	}
	
	public void dibujarDerecha1(Entorno entorno) {
		entorno.dibujarImagen(img1, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarDerecha2(Entorno entorno) {
		entorno.dibujarImagen(img2, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarIzquierda1(Entorno entorno) {
		entorno.dibujarImagen(img3, this.getX(), this.y, 0, 0.08);
	}
	public void dibujarIzquierda2(Entorno entorno) {
		entorno.dibujarImagen(img4, this.getX(), this.y, 0, 0.08);
	}

	
	
	public void moverDerecha() {
        this.setX(this.getX() + 3);
        this.direccion = 0;
        
    }

    public void moverIzquierda() {
        this.setX(this.getX() - 3);
        this.direccion = 1;
    }



	
	//Esta funcion es para poder hacer que cuando no se presionen teclas
	//No haga la animacion de estar corriendo
	public void estaQuieta() {
		this.direccion=4;
	}


	public void saltar() {
		if (!saltando && !teclaSaltoPresionada) {
			saltando = true;
			velocidadY = -velocidadSalto;
			teclaSaltoPresionada = true;
		}
	}

	public void liberarTeclaSalto() {
		teclaSaltoPresionada = false;
	}


	 public void detenerSalto(double posicionBloque) {
	        if (this.velocidadY < 0) {
	            this.y = posicionBloque + 30; // Ajusta la posición de la princesa para que no pase a través del bloque
	            this.velocidadY = 0; // Detén el movimiento vertical
	        }
	    }


	    public void aplicarGravedad(Bloque[] bloques) {
	        boolean hayBloqueDebajo = false;

	        for (Bloque bloque : bloques) {
	            if (bloque != null && colisionaConParteInferior(bloque)) {
	                hayBloqueDebajo = true;
	                if (saltando && velocidadY > 0) {
	                    aterrizar(bloque.getY() - 30);
	                }
	                break;
	            }
	        }

	        if (!hayBloqueDebajo && !saltando) {
	            saltando = true;
	            velocidadY = 0; // Reiniciar velocidad de caída
	        }

	        if (saltando || estaSobreBloqueDestruido(bloques)) {
	            this.y += velocidadY;
	            velocidadY += gravedad;
	        }
	    }

	    public boolean colisionaConParteInferior(Bloque bloque) {
	        return (getX() < bloque.getX() + 30 && getX() + 30 > bloque.getX() &&
	                getY() + 30 <= bloque.getY() && getY() + 30 + velocidadY >= bloque.getY());
	    }

	    public boolean estaSaltando() {
	        return saltando;
	    }

	    public void aterrizar(double nuevaY) {
	        this.y = nuevaY;
	        saltando = false;
	        velocidadY = 0;
	    }
	    public boolean estaSobreBloqueDestruido(Bloque[] bloques) {
	        for (Bloque bloque : bloques) {
	            if (bloque != null && colisionaConParteInferior(bloque) && bloque.esRompible() && bloque.getX() == -1000) {
	                return true;
	            }
	        }
	        return false;
	    }
		public double getY() {
	        return y;
	    }

	private List<DisparoPrincesa> disparosActivos = new ArrayList<>();
	private boolean puedeDisparar = true; // Nueva variable para controlar si se puede disparar

	public void disparar(Entorno entorno) {
		if (puedeDisparar) {
			DisparoPrincesa disparo = new DisparoPrincesa(this.x, this.y, direccion == 0 || direccion == 4);
			disparosActivos.add(disparo);
			puedeDisparar = false; // Bloquear disparo hasta que se suelte la tecla
		}
	}

	public void dibujarDisparos(Entorno entorno, List<Dinosaurio> dinosaurios) {
		List<DisparoPrincesa> disparosParaEliminar = new ArrayList<>();
		for (DisparoPrincesa disparo : disparosActivos) {
			disparo.dibujarse(entorno);
			disparo.mover();
			for (Dinosaurio dinosaurio : dinosaurios) {
				if (colisiona(disparo, dinosaurio)) {
					dinosaurios.remove(dinosaurio);
					disparosParaEliminar.add(disparo);
					break; // Termina el disparo después de eliminar un dinosaurio
				}
			}
			if (disparo.getX() < 0 || disparo.getX() > entorno.ancho()) {
				disparosParaEliminar.add(disparo);
			}
		}
		disparosActivos.removeAll(disparosParaEliminar);
	}

	private boolean colisiona(DisparoPrincesa disparo, Dinosaurio dinosaurio) {
		return disparo.getX() < dinosaurio.getX() + 30 &&
				disparo.getX() + 10 > dinosaurio.getX() &&
				disparo.getY() < dinosaurio.getY() + 30 &&
				disparo.getY() + 10 > dinosaurio.getY();
	}

	public void resetDisparo() {
		puedeDisparar = true; // Permitir disparar de nuevo
	}

}


package meujogo.Modelo;

import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class Player {
	private int x,y;
	private int dX, dY;
	private Image imagem;
	private int altura,largura;
	private List <Bomb> tiros;
	private boolean isVisivel;
	
	public Player() {
		this.x = 100;
		this.y = 100;
		isVisivel = true;
		
		tiros = new ArrayList<Bomb>();
	}

	public void Load() {
		ImageIcon referencia = new ImageIcon("res\\nave1.png");
		imagem = referencia.getImage();
		
		altura = imagem.getHeight(null);
		largura = imagem.getWidth(null);
	}
	
	public void Update() {
		x += dX;
		y += dY;
	}
	
	public void tiroSimples() {
		this.tiros.add(new Bomb(x+largura - 30, y + (altura/2) - 10));
	}
		
	public Rectangle getBounds() {
		return new Rectangle(x,y,largura,altura);
	}

	public void KeyPressed(KeyEvent tecla) {
		int codigo =  tecla.getKeyCode();
		
		if(codigo == KeyEvent.VK_NUMPAD0) {
			tiroSimples();
		}
		if(codigo == KeyEvent.VK_UP) {
			dY = -5;
		}
		if(codigo == KeyEvent.VK_DOWN) {
			dY = 5;
		}
		if(codigo == KeyEvent.VK_LEFT) {
			dX = -5;
		}
		if(codigo == KeyEvent.VK_RIGHT) {
			dX = 5;
		}
	}
	
	public void KeyRelease(KeyEvent tecla) {
		int codigo =  tecla.getKeyCode();
		
		if(codigo == KeyEvent.VK_UP) {
			dY = 0;
		}
		if(codigo == KeyEvent.VK_DOWN) {
			dY = 0;
		}
		if(codigo == KeyEvent.VK_LEFT) {
			dX = 0;
		}
		if(codigo == KeyEvent.VK_RIGHT) {
			dX = 0;
		}
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public Image getImagem() {
		return imagem;
	}
	
	public List<Bomb> getTiros() {
		return tiros;
	}

	public boolean isVisivel() {
		return isVisivel;
	}

	public void setVisivel(boolean isVisivel) {
		this.isVisivel = isVisivel;
	}
	
	
}

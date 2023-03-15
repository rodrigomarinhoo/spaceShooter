package meujogo.Modelo;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {
	private Image fundo;
	private Player player;
	private Timer timer;
	private List<Enemy1> enemy1;
	private boolean emJogo;
	
	
	public Fase() {
		setFocusable(true);
		setDoubleBuffered(true);
		
		//ImageIcon referencia = new ImageIcon("res\\background.png");
		ImageIcon referencia = new ImageIcon("res\\outerspace1.gif");
		fundo = referencia.getImage();
		
		player = new Player();
		player.Load();
		
		addKeyListener(new TecladoAdpter());
		
		timer = new Timer(5, this);
		timer.start();
		
		inicializaInimigos();
		emJogo = true;
	}
	
	public void inicializaInimigos(){
		int coordenadas[] = new int[40];
		enemy1 = new ArrayList<Enemy1>();
		
		for(int i = 0; i < coordenadas.length; i++) {
			int x = (int)(Math.random() * 8000 + 1024);
			int y = (int)(Math.random() * 650 + 30);
			enemy1.add(new Enemy1(x,y));
		}
	}
	
	
	
	
	public void paint(Graphics g) {
		Graphics2D graficos = (Graphics2D) g;
		if(emJogo == true) {
		graficos.drawImage(fundo,0,0,null);
		graficos.drawImage(player.getImagem(), player.getX(),player.getY(),this);
		
		
		List<Bomb>tiros = player.getTiros();
		for(int i = 0; i < tiros.size(); i++) {
			Bomb m = tiros.get(i);
			m.load();
			graficos.drawImage(m.getImagem(),m.getX(),m.getY(),this);
		}
		
		for(int o = 0; o < enemy1.size(); o++) {
			Enemy1 in = enemy1.get(o);
			in.load();
			graficos.drawImage(in.getImagem(),in.getX(),in.getY(),this);
		}
		}else {
			ImageIcon fimJogo = new ImageIcon("res\\gameover.png");
			graficos.drawImage(fimJogo.getImage(),0,0,null);
		}
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		player.Update();
		List<Bomb>tiros = player.getTiros();
		for(int i = 0; i < tiros.size(); i++) {
			Bomb m = tiros.get(i);
				if(m.isVisivel()) {
					m.update();
				}else {
					tiros.remove(i);
				}
		}
		
		for(int o=0; o < enemy1.size(); o++) {
			Enemy1 in = enemy1.get(o);
			if(in.isVisivel()) {
				in.update();
			}else {
				enemy1.remove(o);
			}
		}
		
		checarColisoes();
		repaint();
		
	}
	
	public void checarColisoes() {
		Rectangle formaNave = player.getBounds();
		Rectangle formaEnemy1;
		Rectangle formaTiro;
		
		for(int i = 0; i < enemy1.size(); i++) {
			Enemy1 tempEnemy1 = enemy1.get(i);
			formaEnemy1 = tempEnemy1.getBounds();
				if(formaNave.intersects(formaEnemy1)) {
					player.setVisivel(false);
					tempEnemy1.setVisivel(false);
					emJogo = false;
				}
		}
		
		List<Bomb> tiros = player.getTiros();
		for(int j = 0; j < tiros.size(); j++) {
			Bomb tempTiro = tiros.get(j);
			formaTiro = tempTiro.getBounds();
			for(int m = 0; m < enemy1.size(); m++) {
				Enemy1 tempEnemy1 = enemy1.get(m);
				formaEnemy1 = tempEnemy1.getBounds();
					if(formaTiro.intersects(formaEnemy1)) {
						tempEnemy1.setVisivel(false);
						tempTiro.setVisivel(false);
					}
			}
		}
		
	}
	
	
	
	
	
	
	private class TecladoAdpter extends KeyAdapter{
		@Override
		public void keyPressed(KeyEvent e) {
			player.KeyPressed(e);
		}
		@Override
		public void keyReleased(KeyEvent e) {
			player.KeyRelease(e);
		}
	}
}

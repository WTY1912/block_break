import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements Runnable, MouseListener,MouseMotionListener{
	static final int WIDTH = 360;//画面サイズ横
	static final int HEIGHT = 280;//縦
	private Ball ball;
	private Thread ballLoop;//ゲームのループ
	private int xx;//マウス座標
	static int Mpush;//マウスクリック
	private int rx,ry;//イベントブロック用座標
	private Racket racket;//
	private static final int NUM_BLOCK_ROW = 8;//ブロック配置縦
	private static final int NUM_BLOCK_COL = 9; //横
	static final int NUM_BLOCK = NUM_BLOCK_ROW * NUM_BLOCK_COL;//ブロック総数
	int xyz = 0;//勝敗判定
	int numCount = 0;//ブロック消した数
	int numCountSub = NUM_BLOCK;//残りブロック数
	int sleeptime = 5;//ボールの速度
	int racCount = 0;//ラケット衝突回数
	int racCount2 = 0;//ラケット衝突回数2
	int playCount = 0;//ゲームで遊んだかどうか
	Random rand;

	private Block[][] block = new Block[NUM_BLOCK_ROW][NUM_BLOCK_COL];
	public void mouseClicked(MouseEvent e) {}
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mousePressed(MouseEvent e) {
		Mpush = e.getButton();
	}
	public void mouseReleased(MouseEvent e) {}
	public void mouseMoved(MouseEvent e) {
		xx = e.getX();
	}
	public void mouseDragged(MouseEvent e) {}

	public MainPanel() {
		rand = new Random(System.currentTimeMillis());
		rx = NUM_BLOCK_ROW-2;
		ry = rand.nextInt(NUM_BLOCK_COL);
		setPreferredSize(new Dimension(WIDTH,HEIGHT));
		addMouseListener(this);
		addMouseMotionListener(this);

		ball = new Ball();
		racket = new Racket();
		ballLoop = new Thread(this);

		for(int i = 0; i<NUM_BLOCK_ROW;i++) {//ブロック配置
			for(int j = 0; j<NUM_BLOCK_COL;j++) {
				int x = j*Block.WIDTH;
				int y = i*Block.HEIGHT;
				block[i][j] = new Block(x,y);
			}
		}
		ballLoop.start();
	}

	public void run() {//ゲーム動かすところ
		JLabel label = new JLabel("左クリックでゲームスタート");
		label.setFont(new Font("ＭＳ ゴシック", Font.BOLD, 22));
		label.setForeground(Color.YELLOW);
		label.setPreferredSize(new Dimension(MainPanel.WIDTH, 300));
		label.setHorizontalAlignment(JLabel.CENTER);
		add(label);
		Music.BGM();
		while(true){
			System.out.print("");
			if(Mpush == 1 && playCount == 0){
				playCount++;
				while(true) {
					Mpush = 0;
					label.setText("");
					ball.move();//ボールの移動
					racket.move(xx);
					if(racket.bump(ball) == true) {
						ball.bound();
						if(racCount == racCount2){
							racCount2++;
							racCount++;
						}else if(racCount2 >= -10 && racCount2 <= -9){
							racCount2++;
						}else{
							racCount2 = racCount;
						}
					}
					for(int i = 0; i<NUM_BLOCK_ROW;i++) {
						for(int j = 0; j<NUM_BLOCK_COL;j++) {
							int Pos = block[i][j].bump(ball);
							if(block[i][j].isDeleted == false){
								if (i==rx && j==ry) {//特殊イベント発生
									if(Pos > 0){
										block[i][j].delete();
										ball.BKbound(Pos);
										racCount2 = -10;
										numCount++;
										numCountSub--;
									}
								}
								else{
									if(Pos > 0) {
										if(racCount2 < 0){
											block[i][j].delete();
												Music.BreakSE();
											numCount++;
											numCountSub--;
										}else{
											block[i][j].delete();
											ball.BKbound(Pos);
											numCount++;
											numCountSub--;
										}
									}
								}
							}
						}
					}
					repaint();
					xyz = ball.GameOver(numCount);
					if(xyz == 1) {
						label.setText("<html>Game Win!<br>右クリックでステージリセット</html>");
						Music.WinSE();
						break;
					}
					if(xyz == 2) {
						label.setText("<html>Game Lose...<br>右クリックでステージリセット</html>");
						Music.LoseSE();
						break;
					}
					if(numCountSub <= NUM_BLOCK/5*4){sleeptime = 4;}//ボールの速度調整
					if(numCountSub <= NUM_BLOCK/5*3){sleeptime = 3;}
					if(numCountSub <= NUM_BLOCK/5*2){sleeptime = 2;}
					else {}
					try {
							Thread.sleep(sleeptime);
					}catch(InterruptedException e) {
						e.printStackTrace();
					}
				}
			}else if(Mpush == 3){//ゲームリセット
				Mpush = 0;
				playCount = 0;
				label.setText("左クリックでゲームスタート");
				ball = new Ball();
				xyz = 0;
				numCount = 0;
				sleeptime = 5;
				racCount = 0;
				racCount2 = 0;
				playCount = 0;
				numCountSub = NUM_BLOCK;
				ry = rand.nextInt(NUM_BLOCK_COL);
				for(int i = 0; i<NUM_BLOCK_ROW;i++) {
					for(int j = 0; j<NUM_BLOCK_COL;j++) {
						block[i][j].isDeleted = false;
					}
				}
			}
		}
	}

	public void paintComponent(Graphics g) {//いろいろ描画
		super.paintComponent(g);
		g.setColor(Color.black);
		g.fillRect(0, 0, WIDTH, HEIGHT);
		ball.draw(g);
		racket.draw(g);
		for(int i = 0; i<NUM_BLOCK_ROW;i++) {
			for(int j = 0; j<NUM_BLOCK_COL;j++) {
				if (block[i][j].isDeleted == false && i==rx && j==ry) {
					block[i][j].chColor(g);
				}
				else if (block[i][j].isDeleted == false) {
					block[i][j].draw(g);
				}
			}
		}
	}
}
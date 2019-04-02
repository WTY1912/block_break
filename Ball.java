import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

public class Ball {
	private final int SIZE = 10;//直径
	private int x,y;//ボールの中心座標
	private int vx,vy;//ボールの移動方向と速さ
	Racket rac;
	//以下16行までボールの座標と判定
	int getX() {return x;}
	int left() {return x-SIZE/2;}
	int right() {return x+SIZE/2;}
	int getY() {return y;}
	int up() {return y-SIZE/2;}
	int down() {return y+SIZE/2;}
	MainPanel count;

	public Ball() {
		Random rand = new Random(System.currentTimeMillis());
		x = rand.nextInt(MainPanel.WIDTH - SIZE);
		y = 100;
		vx = 1;//直角に跳ねる
		vy = 1;
	}
	public void draw(Graphics g) {//ボールの描画設定
		g.setColor(Color.red);
		g.fillOval(x-SIZE/2,y-SIZE/2,SIZE,SIZE);
	}
	public void move() {
		x = x+vx;
		y = y+vy;
		if(x+SIZE/2 == MainPanel.WIDTH) {vx = -1; Music.BoundSE();}
		if(y+SIZE/2 == MainPanel.HEIGHT) {vy = -1;}
		if(x-SIZE/2 == 0) {vx = 1; Music.BoundSE();}
		if(y-SIZE/2 == 0) {vy = 1; Music.BoundSE();}
	}
	void bound() {//壁かラケット衝突時の処理
		Music.BoundSE();
		y = y+vy;
		vy = -1;
	}
	public void BKbound(int Pos) {//ブロック衝突時の処理
		x = x+vx;
		y = y+vy;
		Music.BreakSE();
		switch(Pos) {
		case Block.DOWN :{vy = 1; break;}
		case Block.LEFT :{vx = -1;break;}
		case Block.RIGHT :{vx = 1;break;}
		case Block.UP :{vy = -1;break;}
		case Block.DOWN_LEFT :{vx = -1;vy = 1; break;}
		case Block.DOWN_RIGHT :{vx = 1;vy = 1;break;}
		case Block.UP_LEFT :{vx = -1;vy = -1;break;}
		case Block.UP_RIGHT :{vx = 1;vy = -1;break;}
		}
	}
	int GameOver(int x) {
		if(x == MainPanel.NUM_BLOCK) {return 1;}//win
		if(this.y+SIZE/2 == MainPanel.HEIGHT) {return 2;}//lose
		else {return 0;}
	}
}
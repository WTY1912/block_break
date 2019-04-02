import java.awt.Color;
import java.awt.Graphics;

public class Block {

	public static final int WIDTH = 40;		  //ブロック横幅
	public static final int HEIGHT = 9;		  //縦幅
	int x,y;
	boolean isDeleted;							 //真ならブロックは存在しない
	public static final int NO_COLLISION = 0;//判定無し
	public static final int DOWN = 1;		  //下
	public static final int LEFT = 2;		  //左
	public static final int RIGHT = 3;		  //右
	public static final int UP = 4;			  //上
	public static final int DOWN_LEFT = 5;	  //左下
	public static final int DOWN_RIGHT = 6;  //右下
	public static final int UP_LEFT = 7;	  //左上
	public static final int UP_RIGHT = 8;	  //右上


	Block(int x , int y){
		this.x = x;
		this.y = y;
		isDeleted = false;
	}
	void draw(Graphics g) {//ブロックの描画処理
		g.setColor(Color.orange);
		g.fillRect(x,y, WIDTH, HEIGHT);
	}
	void chColor(Graphics g){//イベント発生ブロックの描画処理
		g.setColor(Color.red);
		g.fillRect(x,y, WIDTH, HEIGHT);
	}
	int bump(Ball ball) {//ボール衝突時処理
		if(ball.up() == y+HEIGHT && ball.getX() < x+WIDTH && ball.getX() > x) {return DOWN;}
		else if(ball.right() == x && ball.getY() < y+HEIGHT && ball.getY() > y) {return LEFT;}
		else if(ball.left() == x+WIDTH && ball.getY() < y+HEIGHT && ball.getY() > y) {return RIGHT;}
		else if(ball.down() == y && ball.getX() < x+WIDTH && ball.getX() > x) {return UP;}
		else if(ball.right() >= x && ball.getX() <= x && ball.up() <= y+HEIGHT && ball.getY() >= y+HEIGHT) {return DOWN_LEFT;}
		else if(ball.left() <= x+WIDTH && ball.getX() >= x+WIDTH && ball.up() <= y+HEIGHT && ball.getY() >= y+HEIGHT) {return DOWN_RIGHT;}
		else if(ball.right() >= x && ball.getX() <= x && ball.down() >= y && ball.getY() <= y) {return UP_LEFT;}
		else if(ball.left() <= x+WIDTH && ball.getX() >= x+WIDTH && ball.down() >= y && ball.getY() <= y) {return UP_RIGHT;}
		return NO_COLLISION;
	}
	void delete() {
		isDeleted = true;
	}
}
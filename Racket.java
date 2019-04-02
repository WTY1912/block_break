import java.awt.Color;
import java.awt.Graphics;

public class Racket {
	public static final int WIDTH = 80;//ラケットのサイズ横
	public static final int HEIGHT = 25;//縦
	private int centerPos;//ラケットの中心位置
	public Racket() {
		centerPos = MainPanel.WIDTH/2;
	}
	public void draw(Graphics g) {//ラケットの描画処理
		g.setColor(Color.blue);
		g.fillRect(centerPos-WIDTH/2,MainPanel.HEIGHT-HEIGHT, WIDTH, HEIGHT);
	}
	public void move(int pos) {//ラケットの操作処理
		if(pos <= MainPanel.WIDTH-WIDTH/2 && pos >= MainPanel.WIDTH-MainPanel.WIDTH+WIDTH/2) {
			centerPos = pos;
		}
		if(pos >= MainPanel.WIDTH-WIDTH/2){
			centerPos = MainPanel.WIDTH-WIDTH/2;
		}
		if(pos <= MainPanel.WIDTH-MainPanel.WIDTH+WIDTH/2){
			centerPos = WIDTH/2;
		}
	}
	public boolean bump(Ball ball) {//ボール衝突時処理
		if(ball.down() == MainPanel.HEIGHT-HEIGHT) {
			if(ball.getX() >= centerPos-WIDTH/2 && ball.getX() <= centerPos+WIDTH/2) {
				return true;
			}
		}
		return false;
	}
}
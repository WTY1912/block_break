import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Music {

	public static void BGM(){
		InputStream stream = Music.class.getClassLoader().getResourceAsStream("BGM-1.wav");
		InputStream bufferedIn = new BufferedInputStream(stream);
		Clip clip = createClip( bufferedIn );
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(ctrl, 0.1);
		clip.loop(Clip.LOOP_CONTINUOUSLY);
	}
	public static void BoundSE(){
		InputStream stream = Music.class.getClassLoader().getResourceAsStream("bound.wav");
		InputStream bufferedIn = new BufferedInputStream(stream);
		Clip clip = createClip( bufferedIn );
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(ctrl, 0.1);
		clip.start();
	}
	public static void BreakSE(){
		InputStream stream = Music.class.getClassLoader().getResourceAsStream("break.wav");
		InputStream bufferedIn = new BufferedInputStream(stream);
		Clip clip = createClip( bufferedIn );
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(ctrl, 0.2);
		clip.start();
	}
	public static void WinSE(){
		InputStream stream = Music.class.getClassLoader().getResourceAsStream("win.wav");
		InputStream bufferedIn = new BufferedInputStream(stream);
		Clip clip = createClip( bufferedIn );
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(ctrl, 0.2);
		clip.start();
	}
	public static void LoseSE(){
		InputStream stream = Music.class.getClassLoader().getResourceAsStream("lose.wav");
		InputStream bufferedIn = new BufferedInputStream(stream);
		Clip clip = createClip( bufferedIn );
		FloatControl ctrl = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
		controlByLinearScalar(ctrl, 0.2);
		clip.start();
	}

	private static void controlByLinearScalar(FloatControl control, double linearScalar) {
		control.setValue((float)Math.log10(linearScalar) * 20);//ボリューム調整用の式
	}

	public static Clip createClip(InputStream is) {
		AudioInputStream ais = null;
		try{
			 ais = AudioSystem.getAudioInputStream(is);
			AudioFormat af = ais.getFormat();
			DataLine.Info dataLine = new DataLine.Info(Clip.class,af);
			Clip c = (Clip)AudioSystem.getLine(dataLine);

			c.open(ais);

			return c;
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
		return null;
	}
}
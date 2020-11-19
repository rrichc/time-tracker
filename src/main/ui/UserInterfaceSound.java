package ui;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

//https://stackoverflow.com/questions/15526255/best-way-to-get-sound-on-button-press-for-a-java-calculator
public class UserInterfaceSound {
    public static final String SELECT_SOUND = "./data/Ding.wav";
    public static AudioInputStream audioInputStream;
    public static Clip clip;

    public static void playSelectSound() {
        try {
            audioInputStream = AudioSystem.getAudioInputStream(new File(SELECT_SOUND).getAbsoluteFile());
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        }
    }

}

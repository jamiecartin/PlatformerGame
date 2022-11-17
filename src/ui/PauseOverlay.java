package ui;

import gamestates.Gamestate;
import main.Game;
import utils.LoadSave;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import static utils.Constants.UI.PauseButtons.*;

public class PauseOverlay {
    private BufferedImage backgroundImg;
    private int bgX, bgY, bgW, bgH;
    private SoundButton musicButton, sFxButton;

    public PauseOverlay() {
        loadBackground();
        createSoundButtons();
    }

    private void createSoundButtons() {
        int soundX = (int)(450 * Game.SCALE);
        int musicY = (int)(140 * Game.SCALE);
        int sfxY = (int)(186 * Game.SCALE);
        musicButton = new SoundButton(soundX, musicY, SOUND_SIZE, SOUND_SIZE);
        sFxButton = new SoundButton(soundX, sfxY, SOUND_SIZE, SOUND_SIZE);
    }

    private void loadBackground() {
        backgroundImg = LoadSave.GetSpriteAtlas(LoadSave.PAUSE_BACKGROUND);
        bgW = (int)(backgroundImg.getWidth() * Game.SCALE);
        bgH = (int)(backgroundImg.getHeight() * Game.SCALE);
        bgX = Game.GAME_WIDTH / 2 - bgW / 2;
        bgY = (int)(25 * Game.SCALE);
    }

    public void update() {
        musicButton.update();;
        sFxButton.update();
    }

    public void draw(Graphics g) {
        //background
        g.drawImage(backgroundImg, bgX, bgY, bgW, bgH, null);

        //sound buttons
        musicButton.draw(g);
        sFxButton.draw(g);
    }

    public void mouseDragged(MouseEvent e) {

    }

    public void mousePressed(MouseEvent e) {
        if(isIn(e, musicButton))
            musicButton.setMousePressed(true);
        else if (isIn(e, sFxButton))
            sFxButton.setMousePressed(true);
    }

    public void mouseReleased(MouseEvent e) {
        if(isIn(e, musicButton)) {
            if (musicButton.isMousePressed())
                musicButton.setMuted(!musicButton.isMuted());
        } else if (isIn(e, sFxButton))
            if (sFxButton.isMousePressed())
                sFxButton.setMuted(!sFxButton.isMuted());

        musicButton.resetBools();
        sFxButton.resetBools();
    }

    public void mouseMoved(MouseEvent e) {
        musicButton.setMouseOver(false);
        sFxButton.setMouseOver(false);

        if(isIn(e, musicButton))
            musicButton.setMouseOver(true);
        else if (isIn(e, sFxButton))
            sFxButton.setMouseOver(true);
    }

    private boolean isIn(MouseEvent e, PauseButton b) {
        return b.getBounds().contains(e.getX(), e.getY());
    }
}

package com.khoben.cb.patterns.Visitor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.players.Player;


public class MakeSoundByEntity implements Visitor {

    Sound bottleSound;
    Sound playerSound;

    final static float volume = 0.2f;

    public MakeSoundByEntity(){
        bottleSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/bottleSound.wav"));
        playerSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/playerSound.wav"));
    }

    @Override
    public void visit(Bottle bottle) {
        bottleSound.play(volume);
    }

    @Override
    public void visit(Player player) {
        playerSound.play();
    }
}

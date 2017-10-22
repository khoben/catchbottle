package com.khoben.cb.screens.Button;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by extle on 21.10.2017.
 */

public class MyButton {
    private Sprite img;
    float x,
        y,
        w,
        h;
    public MyButton(String textureName, float x, float y, float w, float h )
    {
        img = new Sprite(new Texture(Gdx.files.internal("buttons/"+textureName)));
        img.setSize(w,h);
        img.setPosition(x,y);
    }

    public void draw (SpriteBatch batch) {
       img.draw(batch);
    }

    public boolean wasClicked (float ix, float iy) {
        if (ix > img.getX() && ix < img.getX() + img.getWidth()) {
            if (iy < img.getY() + img.getHeight() && iy > img.getY()) {
                return true;
            }
        }
        return false;
    }
}

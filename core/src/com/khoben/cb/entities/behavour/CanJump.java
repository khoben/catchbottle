package com.khoben.cb.entities.behavour;

import com.khoben.cb.entities.Entity;

/**
 * Created by extle on 02.10.2017.
 */

public class CanJump implements IJumpAction {
    public void jump(Entity e, float amount){
        e.setVelocityY(amount);
    }
}

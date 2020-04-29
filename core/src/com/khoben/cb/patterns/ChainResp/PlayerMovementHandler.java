package com.khoben.cb.patterns.ChainResp;

import com.badlogic.gdx.Input;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;

import java.io.IOException;

/**
 * Created by extle on 20.12.2017.
 */

public class PlayerMovementHandler extends KeyHandler {

    public PlayerMovementHandler(){
        super();
    }

    public PlayerMovementHandler(KeyHandler successor) {
        super(successor);
    }

    @Override
    public void handleRequest(int keycode, GameMap map) throws IOException {
        switch (keycode){
            case Input.Keys.SPACE:
            case Input.Keys.UP:
                map.sEntities.player.playerStateMoving = Player.PlayerStateMoving.JUMP;
                break;
            case Input.Keys.LEFT:
                map.sEntities.player.playerStateMoving = Player.PlayerStateMoving.MOVE_L;
                break;
            case Input.Keys.RIGHT:
                map.sEntities.player.playerStateMoving = Player.PlayerStateMoving.MOVE_R;
                break;
            default:
                if(successor!=null)
                    successor.handleRequest(keycode, map);
        }
    }
}

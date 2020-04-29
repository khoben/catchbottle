package com.khoben.cb.patterns.Memento;

import com.badlogic.gdx.Gdx;
import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.BottlesFromSky.CommonBottle;
import com.khoben.cb.entities.BottlesFromSky.DeadlyBottle;
import com.khoben.cb.entities.EntitiesStorage.EntityLoader;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.map.GameMap;
import com.khoben.cb.patterns.Facade.SetupEntities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class SaveHandler {

    public static void saveToFile(String name, SetupEntities setupEntities, List<BottleFromSky> bottleFromSkies, int score) throws IOException {

        Gdx.files.local(EntityLoader.PathToSaves + name + EntityLoader.ExtensionSaves).writeString("",false);

        ArrayList<Entity> entities = new ArrayList<>();
        entities.add(setupEntities.player);
        entities.addAll(bottleFromSkies);
        EntityLoader.saveEntities(name, entities,score);

    }

    public static void loadFromFile(String name, SetupEntities setupEntities, List<BottleFromSky> bottleFromSkies, GameMap map, int score) throws IOException {
        ArrayList<Entity> entities = new ArrayList<>();
        entities.addAll(EntityLoader.loadEntities(name, map, entities,score));

        bottleFromSkies.clear();

        for(Entity e: entities){
            if ( e.getClass().getSuperclass().getGenericSuperclass()  == Bottle.class){
                BottleFromSky bs = null;

                if (e.getType() == EntityType.COMMONBOTTLE) {
                    bs = new CommonBottle(map);
                } else if (e.getType() == EntityType.DEADLYBOTTLE) {
                    bs = new DeadlyBottle(map);
                }

                if (bs == null)
                    continue;

                bs.setPos(e.getPos());
                bottleFromSkies.add(bs);
            }
            else
                if(e.getClass() == Player.class){
                    setupEntities.player.setPos(e.getPos());
                }
        }
    }


}

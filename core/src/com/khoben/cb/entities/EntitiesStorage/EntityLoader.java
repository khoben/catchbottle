package com.khoben.cb.entities.EntitiesStorage;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.utils.JsonWriter;
import com.khoben.cb.entities.Entity;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.map.GameMap;

import java.io.IOException;
import java.util.ArrayList;

public class EntityLoader {

    public static final String  PathToSaves = "StorageEntities/";
    public static final String ExtensionSaves = ".se";

    private static Json json = new Json();

    public static ArrayList<? extends Entity> loadEntities(String id, GameMap map, ArrayList<? extends Entity> currentEntities, int score) throws IOException {

        Gdx.files.local(PathToSaves).file().mkdirs();
        FileHandle file = Gdx.files.local(PathToSaves+id+ExtensionSaves);

        if (file.exists()){

//            EntitySnapshot[] snapshots = json.fromJson(EntitySnapshot[].class, file.readString());
//            ArrayList<Entity> entities = new ArrayList<>();
//            for(EntitySnapshot snapshot: snapshots){
//                entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
//            }
//            return entities;

            JsonReader jsonReader = new JsonReader();
            JsonValue jsonValue = jsonReader.parse(file.reader());

            JsonValue scoreValue = jsonValue.get("score");

            map.setPoints(scoreValue.asInt());

            JsonValue objectsValue = jsonValue.get("objects");


            EntitySnapshot[] snapshots = json.fromJson(EntitySnapshot[].class,objectsValue.toJson(JsonWriter.OutputType.json));

            ArrayList<Entity> entities = new ArrayList<>();
            for(EntitySnapshot snapshot: snapshots){
                entities.add(EntityType.createEntityUsingSnapshot(snapshot, map));
            }
            return entities;
        }
        else{
            saveEntities(id, currentEntities, score);
            return currentEntities;
        }
    }

    public static void saveEntities(String id, ArrayList<? extends Entity> entities, int score) throws IOException {

        json.setOutputType(JsonWriter.OutputType.json);


        ArrayList<EntitySnapshot> snapshots = new ArrayList<>();

        for(Entity entity: entities){
            snapshots.add(entity.getSaveSnapshot());
        }

        Gdx.files.local(PathToSaves).file().mkdirs();

        FileHandle file = Gdx.files.local(PathToSaves+id+ExtensionSaves);



        JsonWriter jsonWriter = new JsonWriter(file.writer(true));
        json.setWriter(jsonWriter);

        json.writeObjectStart();

        json.writeValue("score", score);

        json.writeArrayStart("objects");

        {
            for(EntitySnapshot snapshot: snapshots){
                json.writeValue(snapshot);
            }
        }

        json.writeArrayEnd();

        json.writeObjectEnd();

        jsonWriter.close();




        //file.writeString(json.prettyPrint(snapshots), true);


    }


}

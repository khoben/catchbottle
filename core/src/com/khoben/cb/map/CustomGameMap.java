package com.khoben.cb.map;

/**
 * Created by extle on 01.10.2017.
 */

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.khoben.cb.entities.BottlesFromSky.BottleFromSky;
import com.khoben.cb.entities.BottlesFromSky.CommonBottle;
import com.khoben.cb.entities.BottlesFromSky.DeadlyBottle;
import com.khoben.cb.entities.EntityType;
import com.khoben.cb.entities.bottles.Bottle;
import com.khoben.cb.entities.bottles.IBottle;
import com.khoben.cb.entities.bottles.MidBottle;
import com.khoben.cb.entities.bottles.Pair;
import com.khoben.cb.entities.players.Player;
import com.khoben.cb.patterns.AbstractFactory.FriendlyFactory;
import com.khoben.cb.patterns.AbstractFactory.IAbstractFactory;
import com.khoben.cb.patterns.AbstractFactory.NPC.EnemyNPC;
import com.khoben.cb.patterns.AbstractFactory.NPC.NPC;
import com.khoben.cb.patterns.Command.OpenGameCommand;
import com.khoben.cb.patterns.Command.SaveGameCommand;
import com.khoben.cb.patterns.Decorator.AmountCollectedBottles;
import com.khoben.cb.patterns.Decorator.Decorator;
import com.khoben.cb.patterns.Decorator.DecoratorTitleForCollectedBottles;
import com.khoben.cb.patterns.Mediator.Colleague;
import com.khoben.cb.patterns.Mediator.MediatorRouter;
import com.khoben.cb.patterns.Observer.GameLogHandler;
import com.khoben.cb.patterns.Observer.GameLogWriter;
import com.khoben.cb.patterns.Observer.Observable;
import com.khoben.cb.patterns.SimpleFactory.SimpleBottleFromSkyFactory;
import com.khoben.cb.patterns.Strategy.FastBottleStrategy;
import com.khoben.cb.patterns.Strategy.NormalBottleStrategy;
import com.khoben.cb.patterns.Strategy.Strategiable;
import com.khoben.cb.patterns.Strategy.SlowBottleStrategy;
import com.khoben.cb.patterns.Visitor.MakeSoundByEntity;
import com.khoben.cb.screens.GameScreen;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import javax.swing.Timer;


public class CustomGameMap extends GameMap {

    final static float gravityForSkyBottles = -2.0f;
    final static long timeBetweenSpawnBottles = 100000000 * 2;
    String id;
    String name;
    int[][][] map;

    FreeTypeFontGenerator generator;
    FreeTypeFontGenerator.FreeTypeFontParameter parameter;
    BitmapFont font;

    int countJumps;
    long lastTimeDrop;
    SimpleBottleFromSkyFactory bottleFromSkyFactory;

    Decorator decoratorForTitleCountBottles;
    AmountCollectedBottles amountCollectedBottles;

    GlyphLayout layout;

    TextureRegion[][] tiles;

    Observable observable;

    int timeToGame;

    IAbstractFactory abstractFactory;

    List<NPC> nps;
    List<IBottle> bottles;

    MakeSoundByEntity soundMaker;

    Timer slowGameTimer;
    int countSlowDuration = 0;
    int slowDuration = 3;
    Strategiable strategy;
    MediatorRouter mediatorRouter;

    public void setDontSpawnNewBottles(boolean dontSpawnNewBottles) {
        this.dontSpawnNewBottles = dontSpawnNewBottles;
    }

    boolean dontSpawnNewBottles = false;

    Player defender;


    public static class Builder {
        private int fontSize = 30;
        private Color fontColor = Color.WHITE;
        private Color fontBorderColor = Color.BLACK;
        private int borderWidth = 2;
        private int timeToGame = 0;

        public Builder() {

        }

        public Builder setFontSize(int val) {
            fontSize = val;
            return this;
        }

        public Builder setTimeToGame(int val) {
            timeToGame = val;
            return this;
        }

        public Builder setFontColor(Color val) {
            fontColor = val;
            return this;
        }

        public Builder setFontBorderColor(Color val) {
            fontBorderColor = val;
            return this;
        }

        public Builder setBorderWidth(int val) {
            borderWidth = val;
            return this;
        }

        public CustomGameMap finalBuild() {
            return new CustomGameMap(this);
        }
    }

    public int getTimeToGame() {
        return timeToGame;
    }

    public void setTimeToGame(int timeToGame) {
        this.timeToGame = timeToGame;
    }

    public CustomGameMap(final Builder builder) {

        CustomGameMapData data = CustomGameMapLoader.loadMap("basic");
        //CustomGameMapLoader.saveMap("basic", map);
//        CustomGameMapData newData = CustomGameMapLoader.createTiledMap("expert");
//        CustomGameMapLoader.saveMap(newData);
        this.id = data.id;
        this.name = data.name;
        this.map = data.map;
        tiles = TextureRegion.split(new Texture("tiles.png"), TileType.TILE_SIZE, TileType.TILE_SIZE);
        generator = new FreeTypeFontGenerator(Gdx.files.internal("fonts/font.ttf"));
        parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = builder.fontSize;
        parameter.color = builder.fontColor;
        parameter.borderColor = builder.fontBorderColor;
        parameter.borderWidth = builder.borderWidth;
        timeToGame = builder.timeToGame;

        font = generator.generateFont(parameter);
        collectedBottles = 0;
        countJumps = 0;
        gameStatus = GameStatus.IN_PROGRESS;
        this.sEntities.player.playerStateAnime = Player.PlayerStateAnime.STAND_R;
        amountCollectedBottles = new AmountCollectedBottles();
        decoratorForTitleCountBottles = new DecoratorTitleForCollectedBottles(amountCollectedBottles);

        bottlesFromSky = new LinkedList<BottleFromSky>();
        bottleFromSkyFactory = new SimpleBottleFromSkyFactory();
        openGameCommand = new OpenGameCommand(this);
        saveGameCommand = new SaveGameCommand(this);

        observable = new GameLogHandler();
        observable.addObserver(new GameLogWriter());

        nps = new ArrayList<>();
        bottles = new ArrayList<>();

        abstractFactory = new FriendlyFactory();
        //resetNPC();

        soundMaker = new MakeSoundByEntity();

        slowGameTimer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (countSlowDuration == 0)  //TODO: Set some strategy
                {
                    strategy.perform();
                }

                countSlowDuration++;
                if (countSlowDuration >= slowDuration) { //TODO: set normal strategy

                    strategy = new NormalBottleStrategy(bottlesFromSky);
                    strategy.perform();

                    countSlowDuration = 0;
                    dontSpawnNewBottles = false;
                    slowGameTimer.stop();
                }
            }
        });

        mediatorRouter = new MediatorRouter();
        mediatorRouter.add(new MidBottle(new Vector2(450, 464), this,mediatorRouter,false));
        mediatorRouter.add(new MidBottle(new Vector2(380, 464), this,mediatorRouter,false));
        mediatorRouter.add(new MidBottle(new Vector2(310, 464), this,mediatorRouter,false));

        defender = new Player(mediatorRouter);
        defender.makeInvalidPlayer();
        defender.create(EntityType.PLAYER, new Vector2(450, 564), this);

        mediatorRouter.add(defender);


        spawnBottlesFromSky();

    }

    private void spawnBottlesFromSky() {
        Class randClass;
        lastTimeDrop = TimeUtils.nanoTime();
        if (lastTimeDrop % 3 == 0)
            randClass = DeadlyBottle.class;
        else
            randClass = CommonBottle.class;

        BottleFromSky genBottle = bottleFromSkyFactory.createBottle(randClass, this);
        bottlesFromSky.add(genBottle);

        // System.out.println(bottlesFromSky.size());
    }

    @Override
    public void render(OrthographicCamera camera, SpriteBatch batch) throws InstantiationException, IllegalAccessException {

        batch.setProjectionMatrix(camera.combined);
        batch.begin();

        for (int layer = 0; layer < getLayers(); layer++) {
            for (int row = 0; row < getHeight(); row++) {
                for (int col = 0; col < getWidth(); col++) {
                    TileType type = this.getTileTypeByCoordinate(layer, col, row);
                    if (type != null)
                        batch.draw(tiles[0][type.getId() - 1], col * TileType.TILE_SIZE, row * TileType.TILE_SIZE);
                }
            }
        }
        //TODO: Decorator
        amountCollectedBottles.SetAmount(this.getPoints());
        layout = new GlyphLayout(font, decoratorForTitleCountBottles.getFinalString());
        font.draw(batch, layout, camera.viewportWidth - layout.width, camera.viewportHeight - layout.height);
        super.render(camera, batch);

        if (timeToGame > 0) {
            layout = new GlyphLayout(font, String.valueOf(timeToGame - GameScreen.roundTime) + ".0 s");
            font.draw(batch, layout, layout.width / 2, camera.viewportHeight - layout.height);
        }

        if (TimeUtils.nanoTime() - lastTimeDrop > timeBetweenSpawnBottles && !dontSpawnNewBottles) {
            spawnBottlesFromSky();
        }

        for (NPC npc : nps) {
            npc.doActions();
            npc.render(batch);
            if (npc.doesIntersects(sEntities.player) == true && npc.getClass() == EnemyNPC.class)
                gameStatus = GameStatus.ENDED;
        }

        for (IBottle b : bottles) {
            b.doActions();
            b.render(batch);
            if (b.doesIntersects(sEntities.player) == true)
                gameStatus = GameStatus.ENDED;
        }


        defender.render(batch);


        for (Colleague b : mediatorRouter.get()) {
            if (b.getClass().getSimpleName().contains("MidBottle")) {
                MidBottle mb = (MidBottle) b;
                if (mb.isVisible)
                    mb.render(batch);
                else
                if (mb.isVisible == false && mb.doesIntersects(sEntities.player) == true) {
                    b.send();
                    //defender.setPos(new Vector2(mb.getX() - mb.getWeight() / 2.0f, mb.getY() + 150));
                }
            }
        }


        //Draw bottles from sky

        Iterator<BottleFromSky> iterator = bottlesFromSky.listIterator();

        while (iterator.hasNext()) {
            boolean delete = false;
            BottleFromSky b = iterator.next();
            if (b.isGrounded()) {
                delete = true;
                bottleFromSkyFactory.getPool(b.getClass()).checkIn(b);
                b.accept(soundMaker);

            } else {
                Pair<Bottle, Boolean> check = b.doesCollisionWithPlayer(this.sEntities.player);
                Pair<Bottle, Boolean> check1 = b.doesCollisionWithPlayer(defender);
                if (check.getRight() || check1.getRight()) {
                    if (b.isDeadly()) {
                        if (check.getRight() == true) {
                            b.accept(soundMaker);
                            gameStatus = GameStatus.ENDED;
                        }
                        else{
                            delete = true;
                            collectedBottles++;
                        }
                    } else {
                        collectedBottles++;
                        bottleFromSkyFactory.getPool(b.getClass()).checkIn(b);
                        delete = true;
                        if (check.getRight()==true) {
                            b.accept(soundMaker);
                            if (TimeUtils.nanoTime() % 2 == 0 && !dontSpawnNewBottles) {
                                if (TimeUtils.nanoTime() % 3 == 0)
                                    strategy = new SlowBottleStrategy(bottlesFromSky); //TODO: SetStrategy
                                else
                                    strategy = new FastBottleStrategy(bottlesFromSky);
                                slowGameTimer.start();
                                dontSpawnNewBottles = true;
                            }
                        }
                    }
                }
                if (delete)
                    iterator.remove();
                else
                    b.render(batch);
            }
        }

        batch.end();
    }

    public void resetNPC() {
        nps.clear();
        bottles.clear();
        nps.add(abstractFactory.createNPC(this));
        bottles.add(abstractFactory.createBottle(this));
    }

    @Override
    public void update(float delta) {
        sEntities.player.checkStatePlayer();

        super.update(delta);

        for (Bottle b : bottlesFromSky) {
            b.update(delta, gravityForSkyBottles);
        }

        for (Colleague b : mediatorRouter.get()) {
            if (b.getClass().getSimpleName().contains("MidBottle")) {
                MidBottle mb = (MidBottle)b;
                mb.update(delta, 0);
            }
        }

        sEntities.player.update(delta, -9.8f);
        defender.update(delta, 0);
    }

    @Override
    public void dispose() {
    }

    @Override
    public TileType getTileTypeByLocation(int layer, float x, float y) {
        return this.getTileTypeByCoordinate(layer, (int) (x / TileType.TILE_SIZE), getHeight() - (int) (y / TileType.TILE_SIZE) - 1);
    }

    @Override
    public TileType getTileTypeByCoordinate(int layer, int col, int row) {
        if (col < 0 || col >= getWidth() || row < 0 || row >= getHeight())
            return null;

        return TileType.getTileTypeById(map[layer][getHeight() - row - 1][col]);
    }

    @Override
    public int getWidth() {
        return map[0][0].length;
    }

    @Override
    public int getHeight() {
        return map[0].length;
    }

    @Override
    public int getLayers() {
        return map.length;
    }

    public int calculatePoints(int howManyAdd) {
        collectedBottles += howManyAdd;
        return collectedBottles;
    }

    public int getPoints() {
        return collectedBottles;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }


}

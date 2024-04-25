/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Heart;
import bricker.ui.Life;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The HeartStrategy class implements a collision strategy for bricks that provides an extra
 * Life upon collision with a ball. It uses the basic collision behavior specified
 * by another CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal
 */
public class HeartStrategy implements CollisionStrategy{
    private CollisionStrategy basic;
    private Vector2 windowDimensions;
    private Life life;
    private GameObjectCollection gameObjectCollection;
    private Vector2 dimensions;
    private ImageReader imageReader;

    /**
     * Constructs a GiveHeartStrategy with the specified parameters.
     * @param gameObjectCollection The collection of GameObjects in the game.
     * @param dimensions           The dimensions for the Heart GameObject.
     * @param imageReader          The image reader for loading images.
     * @param windowDimensions     The dimensions of the game window.
     * @param life                 The Life object to manage player lives.
     * @param basic                The basic collision strategy to extend.
     */
    public HeartStrategy(GameObjectCollection gameObjectCollection,
                         Vector2 dimensions, ImageReader imageReader,
                         Vector2 windowDimensions, Life life, CollisionStrategy basic) {
        this.basic = basic;
        this.dimensions = dimensions;
        this.imageReader = imageReader;
        this.gameObjectCollection = gameObjectCollection;
        this.windowDimensions = windowDimensions;
        this.life = life;
    }

    /**
     * Defines the behavior when a collision occurs between a brick and a ball.
     * Extends the basic collision behavior and introduces the addition of a Heart GameObject.
     * @param thisObj   The current GameObject involved in the collision (brick).
     * @param otherObj  The other GameObject involved in the collision (ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basic.onCollision(thisObj,otherObj);
        Vector2 pos = thisObj.getTopLeftCorner();
        Vector2 center = new Vector2(pos.x() + thisObj.getDimensions().x()/2 ,
                pos.y() + thisObj.getDimensions().y()/2 );
        createHeart(center);
    }

    private void createHeart(Vector2 center){
        Renderable heartImage =
                imageReader.readImage("assets/heart.png", true);
        GameObject heart = new Heart(center, dimensions , heartImage, windowDimensions,
                gameObjectCollection, life);
        gameObjectCollection.addGameObject(heart);
            heart.setVelocity(new Vector2(0, 100));
    }
}


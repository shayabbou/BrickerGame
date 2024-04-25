/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Puck;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.Sound;
import danogl.gui.SoundReader;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;
import java.util.Random;

/**
 * The PuckStrategy class implements a collision strategy for bricks that creates additional
 * Two Puck GameObjects upon collision with a ball.
 * It uses the basic collision behavior specified by another CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal.
 */
public class PuckStrategy implements CollisionStrategy {
    private static final int NUM_OF_PUCKS = 2;
    private CollisionStrategy basic;
    private Vector2 windowDimensions;
    private float ballSpeed;
    private SoundReader soundReader;
    private GameObjectCollection gameObjectCollection;
    private Vector2 dimensions;
    private ImageReader imageReader;

    /**
     * Constructs a CreatePuckStragety with the specified parameters.
     *
     * @param gameObjectCollection The collection of GameObjects in the game.
     * @param dimensions           The dimensions for the Puck GameObjects.
     * @param imageReader          The image reader for loading images.
     * @param soundReader          The sound reader for loading sounds.
     * @param ballSpeed            The speed of the ball.
     * @param windowDimensions     The dimensions of the game window.
     * @param basic                The basic collision strategy to extend.
     */
    public PuckStrategy(GameObjectCollection gameObjectCollection,
                        Vector2 dimensions, ImageReader imageReader,
                        SoundReader soundReader, float ballSpeed,
                        Vector2 windowDimensions, CollisionStrategy basic) {
        this.basic = basic;
        this.dimensions = dimensions;
        this.imageReader = imageReader;
        this.soundReader = soundReader;
        this.gameObjectCollection = gameObjectCollection;
        this.ballSpeed = ballSpeed;
        this.windowDimensions = windowDimensions;
    }

    /**
     * Defines the behavior when a collision occurs between a brick and a ball.
     * Extends the basic collision behavior and introduces the creation of additional 2
     * Puck GameObjects.
     * @param thisObj   The current GameObject involved in the collision (brick).
     * @param otherObj  The other GameObject involved in the collision (ball).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basic.onCollision(thisObj,otherObj);
        Vector2 position = thisObj.getTopLeftCorner();
        Vector2 center = new Vector2(position.x() + thisObj.getDimensions().x()/2 ,
                position.y() + thisObj.getDimensions().y()/2 );
        createPucks(center);
    }


    private void createPucks(Vector2 center){
        Renderable puckImage =
                imageReader.readImage("assets/mockBall.png", true);
        Sound collisionSound = soundReader.readSound("assets/blop.wav");
        GameObject puck1 = new Puck(center, dimensions , puckImage, collisionSound,
                windowDimensions,gameObjectCollection);
        GameObject puck2 = new Puck(center, dimensions , puckImage, collisionSound,
                windowDimensions,gameObjectCollection);
        GameObject [] pucks = {puck1,puck2} ;
        Random rand = new Random();
        boolean dirX = rand.nextBoolean();
        boolean dirY = rand.nextBoolean();
        for (int i = 0; i < NUM_OF_PUCKS; i++) {
            gameObjectCollection.addGameObject(pucks[i]);
            float ballVelX = ballSpeed;
            float ballVelY = ballSpeed;
            if(dirX)
                ballVelX *= -1;
            if(dirY)
                ballVelY *= -1;
            pucks[i].setVelocity(new Vector2(ballVelX, ballVelY));
            dirX = !dirX;
            dirY = !dirY;
        }
    }
}

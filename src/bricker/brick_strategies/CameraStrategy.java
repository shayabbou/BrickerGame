/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import bricker.gameobjects.Puck;
import danogl.GameManager;
import danogl.GameObject;
import danogl.gui.rendering.Camera;
import danogl.util.Vector2;

/**
 * The CameraCollisionStrategy class implements a collision strategy for handling collision
 * between game objects (ball and not a puck) and a brick.
 * It uses the basic collision behavior specified by another CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal
 */
public class CameraStrategy implements CollisionStrategy{
    private final CollisionStrategy basic;
    private GameManager gameManager;
    private Camera camera;

    /**
     * Constructs a CameraCollisionStrategy with the specified parameters.
     *
     * @param gameManager          The game manager responsible for managing game state.
     * @param windowDimensions     The dimensions of the game window.
     * @param ball                 The ball GameObject.
     * @param basic                The basic collision strategy to extend.
     */
    public CameraStrategy(GameManager gameManager , Vector2 windowDimensions ,
                          Ball ball, CollisionStrategy basic) {
        camera = new Camera(ball,Vector2.ZERO, windowDimensions.mult(1.2f),
                windowDimensions);
        this.gameManager = gameManager;
        this.basic = basic;
    }


    /**
     * Defines the behavior when a collision occurs between this GameObject(brick) and
     * another GameObject (ball and not a puck).
     * Extends the basic collision behavior and introduces camera-specific logic.
     * @param thisObj   The current GameObject involved in the collision - brick .
     * @param otherObj  The other GameObject involved in the collision - ball and not a puck.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basic.onCollision(thisObj,otherObj);
        if (otherObj instanceof Puck){
            return;
        }
        if(gameManager.camera() == null){
            gameManager.setCamera(camera);
        }
    }
}

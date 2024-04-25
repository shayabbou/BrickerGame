/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.ExtraPaddle;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.gui.ImageReader;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Counter;
import danogl.util.Vector2;

/**
 * The ExtraPaddleStrategy class implements a collision strategy for bricks that introduces
 * an extra paddle GameObject upon collision. It uses the basic collision behavior specified
 * by another CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal
 */
public class ExtraPaddleStrategy implements CollisionStrategy{
    private GameObjectCollection gameObjects;
    private CollisionStrategy basic;
    private ExtraPaddle extraPaddle;

    /**
     * Constructs an ExtraPaddleStrategy with the specified parameters.
     * @param gameObjects       The collection of GameObjects in the game.
     * @param imageReader       The image reader for loading images.
     * @param windowDimensions  The dimensions of the game window.
     * @param paddleWidth       The width of the extra paddle.
     * @param paddleHeight      The height of the extra paddle.
     * @param inputListener     The user input listener for handling paddle controls.
     * @param basic             The basic collision strategy to extend.
     */
    public ExtraPaddleStrategy(GameObjectCollection gameObjects,
                               ImageReader imageReader, Vector2 windowDimensions,
                               int paddleWidth, int paddleHeight,
                               UserInputListener inputListener, CollisionStrategy basic) {
        this.basic = basic;
        this.gameObjects = gameObjects;
        Renderable paddleImage = imageReader.readImage(
                "assets/paddle.png", true);
        this.extraPaddle = new ExtraPaddle(Vector2.ZERO, new Vector2(paddleWidth, paddleHeight),
                paddleImage, inputListener, windowDimensions, gameObjects);
        extraPaddle.setCenter(new Vector2(windowDimensions.x()/2, windowDimensions.y()/2));
    }

    /**
     * Defines the behavior when a collision occurs between a brick and a ball (or puck).
     * Extends the basic collision behavior and introduces the addition of an extra paddle.
     * Add a new paddle on collision only if number of current extra paddle is 0.
     * @param thisObj   The current GameObject involved in the collision (brick).
     * @param otherObj  The other GameObject involved in the collision (ball or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        basic.onCollision(thisObj,otherObj);
        if(extraPaddle.getPaddleCounter() == 0){
            gameObjects.addGameObject(extraPaddle);
            extraPaddle.incrementPaddleCounter();
        }
    }
}

/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The ExtraPaddle class represents a special type of paddle in the game that can be added temporarily.
 * It extends the Paddle class and provides additional functionality for managing its status
 * and collision behavior.
 * @author Shay Abbou & Shaked Hartal
 */
public class ExtraPaddle extends Paddle{
    private GameObjectCollection gameObjectCollection;
    private int collisionCounter;
    private int paddleCounter;

    /**
     * Constructs an ExtraPaddle instance with the specified parameters.
     *
     * @param topLeftCorner        The position of the ExtraPaddle, in window coordinates (pixels).
     * @param dimensions           The width and height of the ExtraPaddle in window coordinates.
     * @param renderable           The renderable representing the ExtraPaddle.
     * @param inputListener        The user input listener for handling paddle controls.
     * @param windowDimensions     The dimensions of the game window.
     * @param gameObjectCollection The collection of GameObjects in the game.
     */
    public ExtraPaddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                       UserInputListener inputListener, Vector2 windowDimensions,
                       GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions, renderable, inputListener, windowDimensions);
        this.gameObjectCollection = gameObjectCollection;
        this.setTag("extra");
    }

    /**
     * Updates the ExtraPaddle's state over time.
     * If the extra paddle has 4 collision with ball(also puck) , remove the extra paddle.
     * @param deltaTime The time passed since the last frame update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        if(collisionCounter >= 4){
            gameObjectCollection.removeGameObject(this);
            collisionCounter = 0;
            paddleCounter = 0;
        }
    }
    /**
     * Handles the event when a collision occurs with another game object.
     * Overrides the base class method to count collisions with balls.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        if(other instanceof Ball)
            collisionCounter++;
    }
    /**
     * Gets the current paddle counting status of the ExtraPaddle.
     * @return the number of ExtraPaddle that are active
     */
    public int getPaddleCounter(){
        return paddleCounter;
    }

    /**
     * Increment the number of active ExtraPaddle by 1
     */
    public void incrementPaddleCounter(){
        paddleCounter++;
    }
}

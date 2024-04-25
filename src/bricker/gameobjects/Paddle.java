/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import danogl.GameObject;
import danogl.gui.UserInputListener;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;
import java.awt.event.KeyEvent;

/**
 * The Paddle class represents a game object that serves as the player-controlled paddle in the game.
 * It extends the base GameObject class and adds functionality for user input control and movement.
 * The paddle can be moved horizontally based on keyboard input to the left or right.
 * @author Shay Abbou & Shaked Hartal
 */
public class Paddle extends GameObject {

    private static final float MOVEMENT_SPEED = 300;
    private UserInputListener inputListener;
    private Vector2 windowDimensions;

    /**
     * Constructs a new Paddle object with the specified parameters.
     * @param topLeftCorner   The initial position of the paddle in window coordinates (pixels).
     * @param dimensions      The width and height of the paddle in window coordinates.
     * @param renderable      The renderable representing the paddle. Can be null if the paddle
     *                        should not be rendered.
     * @param inputListener   The user input listener for detecting keyboard input.
     * @param windowDimensions The dimensions of the game window.
     */
    public Paddle(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                  UserInputListener inputListener, Vector2 windowDimensions) {
        super(topLeftCorner, dimensions, renderable);
        this.inputListener = inputListener;
        this.windowDimensions = windowDimensions;
        this.setTag("paddle");
    }

    /**
     * Updates the paddle's state based on user input and enforces movement boundaries.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        Vector2 movementDir = Vector2.ZERO;
        if(inputListener.isKeyPressed(KeyEvent.VK_LEFT)){
            movementDir = movementDir.add(Vector2.LEFT);
        }
        if(inputListener.isKeyPressed(KeyEvent.VK_RIGHT)){
            movementDir = movementDir.add(Vector2.RIGHT);
        }
        setVelocity(movementDir.mult(MOVEMENT_SPEED));
        if(this.getTopLeftCorner().x() < 0){
            Vector2 vec = new Vector2(0, this.getTopLeftCorner().y());
            this.setTopLeftCorner(vec);
        }
        if(this.getTopLeftCorner().x() > windowDimensions.x()-getDimensions().x()){
            Vector2 vec = new Vector2(windowDimensions.x()-getDimensions().x(),
                    this.getTopLeftCorner().y());
            this.setTopLeftCorner(vec);
        }
    }
}

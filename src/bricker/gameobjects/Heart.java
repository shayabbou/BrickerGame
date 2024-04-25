/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import bricker.ui.Life;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.collisions.GameObjectCollection;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Heart class represents a game object that provides an extra life when collected.
 * It extends the base GameObject class and includes logic for updating its position,
 * checking collisions with the paddle, and handling collision events.
 * @author Shay Abbou & Shaked Hartal
 */
public class Heart extends GameObject {
    private Vector2 windowDimensions;
    private GameObjectCollection gameObjectCollection;
    private Life life;

    /**
     * Constructs a Heart object with the specified parameters.
     * @param topLeftCorner         The position of the heart, in window coordinates (pixels).
     * @param dimensions            The width and height of the heart in window coordinates.
     * @param renderable            The renderable representing the heart.
     * @param windowDimensions      The dimensions of the game window.
     * @param gameObjectCollection  The collection of GameObjects in the game.
     * @param life                  The Life object representing the player's life count.
     */
    public Heart(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 Vector2 windowDimensions, GameObjectCollection gameObjectCollection, Life life) {
        super(topLeftCorner, dimensions, renderable);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
        this.life = life;
    }


    /**
     * Updates the Heart's state over time.
     * Check if the heart has moved below the window's bottom edge ,if yes
     * remove it from the gameobjects.
     * @param deltaTime The time passed since the last frame update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float heartHeight = getTopLeftCorner().y();
        if(heartHeight > windowDimensions.y()) {
            gameObjectCollection.removeGameObject(this);
        }
    }


    /**
     * Determines what is a legal collision.
     * Overrides the base class method to allow collisions only with paddles
     * and not extra paddle.
     * @param other The other game object involved in the potential collision.
     * @return True if the Heart should collide with the specified object, false otherwise.
     */
    @Override
    public boolean shouldCollideWith(GameObject other) {
        return other.getTag().equals("paddle");
    }

    /**
     * Handles the event when a collision occurs with paddle game object.
     * Overrides the base class method to add a heart to the player's life count
     * and remove the Heart from the game.
     * @param other     The other game object involved in the collision (paddle).
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        life.addLife();
        gameObjectCollection.removeGameObject(this);
    }
}

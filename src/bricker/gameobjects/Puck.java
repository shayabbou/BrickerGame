/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import danogl.collisions.GameObjectCollection;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Puck class represents a game object that extends the Ball class.
 * The puck balls can brake bricks but not function as the main ball of the game.
 * @author Shay Abbou & Shaked Hartal
 */
public class Puck extends Ball{
    private static final float PUCK_SCALE = 0.75f;
    private GameObjectCollection gameObjectCollection;
    private Vector2 windowDimensions;
    /**
     * Constructs a new Puck object with the specified parameters.
     *
     * @param topLeftCorner         The initial position of the puck in window coordinates (pixels).
     * @param dimensions            The width and height of the puck in window coordinates.
     * @param renderable            The renderable representing the puck. Can be null if the puck
     *                              should not be rendered.
     * @param collisionSound        The sound played upon collision with other game objects.
     * @param windowDimensions      The dimensions of the game window.
     * @param gameObjectCollection  The collection of game objects to manage puck removal.
     */
    public Puck(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound, Vector2 windowDimensions, GameObjectCollection gameObjectCollection) {
        super(topLeftCorner, dimensions.mult(PUCK_SCALE), renderable, collisionSound);
        this.windowDimensions = windowDimensions;
        this.gameObjectCollection = gameObjectCollection;
    }

    /**
     * Updates the puck's state, enforcing removal when it reached the window bottom border.
     * @param deltaTime The time elapsed since the last update.
     */
    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        float ballHeight = getTopLeftCorner().y();
        if(ballHeight > windowDimensions.y()) {
            gameObjectCollection.removeGameObject(this);
        }
    }
}

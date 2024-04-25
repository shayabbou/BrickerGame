/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import bricker.brick_strategies.CollisionStrategy;
import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Brick class represents a game object that serves as a brick within the game.
 * It extends the base GameObject class and delegates collision handling to a specified
 * CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal
 */
public class Brick extends GameObject {

    private CollisionStrategy collisionStrategy;

    /**
     * Constructs a Brick object with the specified parameters.
     * @param topLeftCorner       The position of the brick, in window coordinates (pixels).
     * @param dimensions          The width and height of the brick in window coordinates.
     * @param renderable          The renderable representing the brick.
     * @param collisionStrategy   The collision strategy to handle collisions with other game objects.
     */
    public Brick(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                 CollisionStrategy collisionStrategy) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionStrategy = collisionStrategy;
    }

    /**
     * Handles the event when a collision occurs with another game object.
     * Overrides the base class method to delegate collision handling to the specified
     * CollisionStrategy.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        collisionStrategy.onCollision(this, other);
    }
}

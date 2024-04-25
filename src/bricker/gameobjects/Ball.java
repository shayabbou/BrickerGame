/**
 * Package bricker.gameobjects contains classes of different
 * game objects.
 */
package bricker.gameobjects;

import danogl.GameObject;
import danogl.collisions.Collision;
import danogl.gui.Sound;
import danogl.gui.rendering.Renderable;
import danogl.util.Vector2;

/**
 * The Ball class represents a game object that serves as a ball within the game.
 * It extends the base GameObject class and handles collisions with other game objects.
 * @author Shay Abbou & Shaked Hartal
 */
public class Ball extends GameObject {
    private int collisionCounter;
    private Sound collisionSound;

    /**
     * Constructs a Ball object with the specified parameters.
     * @param topLeftCorner    The position of the ball, in window coordinates (pixels).
     * @param dimensions       The width and height of the ball in window coordinates.
     * @param renderable       The renderable representing the ball.
     * @param collisionSound   The sound to play upon collision with other game objects.
     */
    public Ball(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                Sound collisionSound) {
        super(topLeftCorner, dimensions, renderable);
        this.collisionSound = collisionSound;
    }

    /**
     * Handles the event when a collision occurs with another game object.
     * Overrides the base class method to update the ball's velocity, play a collision sound,
     * and increment the collision counter.
     * @param other     The other game object involved in the collision.
     * @param collision The collision information.
     */
    @Override
    public void onCollisionEnter(GameObject other, Collision collision) {
        super.onCollisionEnter(other, collision);
        Vector2 newVel = getVelocity().flipped(collision.getNormal());
        setVelocity(newVel);
        collisionSound.play();
        collisionCounter++;
    }
    /**
     * Gets the current collision counter value.
     * @return The number of collisions the ball has encountered.
     */
    public int getCollisionCounter(){
        return collisionCounter;
    }
}

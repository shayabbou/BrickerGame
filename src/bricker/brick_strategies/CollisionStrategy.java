/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import danogl.GameObject;
/**
 * The CollisionStrategy interface defines a contract for classes implementing collision strategies.
 * Classes that implement this interface are responsible for specifying the behavior when
 * a collision occurs between two GameObjects.
 * @author Shay Abbou & Shaked Hartal
 */
public interface CollisionStrategy {
    /**
     * Defines the behavior when a collision occurs between two GameObjects.
     * @param thisObj   The current GameObject involved in the collision.
     * @param otherObj  The other GameObject involved in the collision.
     */
    public void onCollision(GameObject thisObj, GameObject otherObj);

}

/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.util.Counter;

/**
 * The DoubleBehaviorStrategy class implements a collision strategy for bricks that combines
 * the behaviors of two different collision strategies upon collision with a ball.
 * It uses the basic collision behavior specified by another CollisionStrategy.
 * @author Shay Abbou & Shaked Hartal
 */
public class DoubleBehaviorStrategy implements CollisionStrategy {

    private CollisionStrategy collisionStrategy1;
    private CollisionStrategy collisionStrategy2;
    private Counter counter;

    /**
     * Constructs a DoubleBehaviorStrategy with the specified parameters.
     *
     * @param counter              The counter to keep track of relevant number of bricks.
     * @param object1              The first collision strategy to combine.
     * @param object2              The second collision strategy to combine.
     */
    public DoubleBehaviorStrategy(Counter counter, CollisionStrategy object1,
                                  CollisionStrategy object2) {
        this.counter = counter;
        this.collisionStrategy1 = object1;
        this.collisionStrategy2 = object2;
    }

    /**
     * Defines the behavior when a collision occurs between a brick and a ball (or puck).
     * Combines the behaviors of two different collision strategies and increases the counter.
     * @param thisObj   The current GameObject involved in the collision (brick).
     * @param otherObj  The other GameObject involved in the collision (ball or puck).
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {

        if (otherObj instanceof Ball) {
            collisionStrategy1.onCollision(thisObj, otherObj);
            collisionStrategy2.onCollision(thisObj, otherObj);
            counter.increaseBy(1);
        }
    }
}

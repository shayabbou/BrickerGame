/**
 * Package bricker.brick_strategies contains classes related to
 * different collision strategies for bricks.
 */
package bricker.brick_strategies;

import bricker.gameobjects.Ball;
import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.util.Counter;

/**
 * The BasicCollisionStrategy class defines the collision behavior for bricks when
 * colliding with a Ball GameObject.
 * It implements the CollisionStrategy interface.
 * @author Shay Abbou & Shaked Hartal
 */
public class BasicCollisionStrategy implements CollisionStrategy{
    private GameObjectCollection gameObjectCollection;
    private Counter counter;


    /**
     * Constructs a BasicCollisionStrategy with the specified parameters.
     * @param gameObjectCollection The collection of GameObjects in the game.
     * @param counter              The counter to keep track on the number of bricks.
     */
    public BasicCollisionStrategy(GameObjectCollection gameObjectCollection, Counter counter){
        this.gameObjectCollection = gameObjectCollection;
        this.counter = counter;
    }

    /**
     * Defines the behavior when a collision occurs between brick (GameObject) and Ball GameObject.
     * The current brick (GameObject) is removed from the collection of objects,
     * and the bricks' counter is decremented.
     * @param thisObj   The current GameObject involved in the collision - brick.
     * @param otherObj  The other GameObject involved in the collision - Ball.
     */
    @Override
    public void onCollision(GameObject thisObj, GameObject otherObj) {
        if(otherObj instanceof Ball) {
            gameObjectCollection.removeGameObject(thisObj, Layer.STATIC_OBJECTS);
            counter.decrement();
        }
    }
}

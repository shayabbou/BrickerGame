/**
 * Package bricker.ui contains classes of UI objects.
 */
package bricker.ui;

import danogl.GameObject;
import danogl.collisions.GameObjectCollection;
import danogl.collisions.Layer;
import danogl.gui.rendering.Renderable;
import danogl.gui.rendering.TextRenderable;
import danogl.util.Vector2;
import java.awt.*;

/**
 * The Life class manage all the aspects of the player lives in the game.
 * It includes logic for updating the display of remaining lives, adding and removing
 * hearts,and adjusting the text color based on the number of remaining lives.
 * @author Shay Abbou & Shaked Hartal
 */
public class Life {
    private static final int IMAGE_SPACE = 5;
    private GameObjectCollection gameObjectCollection;
    private int remainingLives, maxLives;
    private GameObject[] lifeArray;
    private TextRenderable textRenderable;

    /**
     * Constructs a Life object with the specified parameters.
     *
     * @param topLeftCorner         The position of the object, in window coordinates (pixels).
     * @param dimensions            The width and height of the object in window coordinates.
     * @param renderable            The renderable representing the object.
     * @param gameObjectCollection  The collection of GameObjects in the game.
     * @param remainingLives        The initial number of remaining lives.
     * @param maxLives              The maximum number of lives allowed.
     */
    public Life(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable,
                GameObjectCollection gameObjectCollection,  int remainingLives, int maxLives) {
        this.remainingLives = remainingLives;
        this.lifeArray = new GameObject[maxLives + 1];
        this.gameObjectCollection = gameObjectCollection;
        this.maxLives = maxLives;
        this.textRenderable = new TextRenderable(String.format("%d",remainingLives));
        initiateLifeArray(topLeftCorner, dimensions, renderable);
    }


    /**
     * Adds a heart to represent an additional life if there are remaining lives available.
     * Updates the text display accordingly.
     */
    public void addLife() {
        if (remainingLives < maxLives) {
            gameObjectCollection.addGameObject(lifeArray[remainingLives + 1], Layer.UI);
            remainingLives++;
            setText();
        }
    }

    /**
     * Removes a heart to represent a lost life if there are remaining lives.
     * Updates the text display accordingly.
     */
    public void removeLife() {
        if (remainingLives > 0) {
            gameObjectCollection.removeGameObject(lifeArray[remainingLives], Layer.UI);
            remainingLives--;
            setText();
        }
    }

    /**
     * Returns the current number of remaining lives.
     * @return An integer representing the remaining lives of the player.
     */
    public int getRemainingLives(){
        return remainingLives;
    }

    private void setText() {
        textRenderable.setString(String.format("%d",remainingLives));
        if (remainingLives == 1) {
            textRenderable.setColor(Color.red);
            return;
        } else if (remainingLives == 2) {
            textRenderable.setColor(Color.yellow);
            return;
        }
        textRenderable.setColor(Color.green);
    }

    private void initiateLifeArray(Vector2 topLeftCorner, Vector2 dimensions, Renderable renderable){
        setText();
        GameObject text = new GameObject(topLeftCorner, dimensions, textRenderable);
        gameObjectCollection.addGameObject(text,Layer.UI);
        topLeftCorner = new Vector2(topLeftCorner.x() + dimensions.x() + IMAGE_SPACE, topLeftCorner.y());
        lifeArray[0] = text;
        for (int i = 0; i < maxLives; i++) {
            GameObject heart = new GameObject(topLeftCorner, dimensions, renderable);
            topLeftCorner = new Vector2(topLeftCorner.x() + dimensions.x() + IMAGE_SPACE, topLeftCorner.y());
            lifeArray[i + 1] = heart;
        }
        for (int i = 0; i < remainingLives; i++) {
            gameObjectCollection.addGameObject(lifeArray[i + 1],Layer.UI);
        }
    }
}

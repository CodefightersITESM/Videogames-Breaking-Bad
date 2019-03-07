package tarea4breakingbad;

import java.awt.Graphics;
import java.awt.Rectangle;

/**
 * Item
 * 
 * Defines a template for any in-game entity.
 * @author César Barraza A01176786, Isabel Cruz A01138741
 * Date 30/Jan/2019
 * @version 1.0
 */
public abstract class Item {
    /**
     * Represents the position of the entity in 2D-space.
     */
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    
    /**
     * Creates an entity at the specified position.
     * @param x coordinate
     * @param y coordinate
     * @param width the width
     * @param height the height
     */
    public Item(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * @return the x position 
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the new x to set 
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y position 
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the new y to set 
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the width 
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param width the new width to set 
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * @return the height 
     */
    public int getHeight() {
        return height;
    }

    /**
     * @param height the new height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }
    
    /**
     * @return the item's rectangle 
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, width, height);
    }
    
    /**
     * Determines if this item intersects with another item, and acts accordingly.
     * @param obj the other item that could intersect with this item
     * @return whether or not the items intersect
     */
    public boolean intersects(Object obj) {
        if(this instanceof Player && obj instanceof Ball) {
            return getRect().intersects(((Item)obj).getRect());       
        } else if(this instanceof Ball && obj instanceof Block) {
            Rectangle rect = getRect();
            rect.width += 2;
            rect.height += 2;
            return getRect().intersects(((Item)obj).getRect());       
        }
        return false;
    }
    
    public abstract void update();
    public abstract void render(Graphics g);
}

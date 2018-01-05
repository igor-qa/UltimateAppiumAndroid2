package Utils;

import io.appium.java_client.MobileElement;
import io.appium.java_client.SwipeElementDirection;

import static io.appium.java_client.SwipeElementDirection.*;

/**
 * Created by borisgurtovyy on 10/14/17.
 */
public class Scroll extends BaseTest {
    /**
     *
     * @param contentView  Frame element enclosing scrollable content.
     * @param element  Element to be scrolled to.
     * @param direction  Scroll direction: UP or DOWN,LEFT or RIGHT
     * @param timeout  Used to quit scroll after specified time (sec) when element not found.
     * @return  True if element found, false if not.
     */
    public boolean scrollToElement(MobileElement contentView, MobileElement element, SwipeElementDirection direction, int timeout) {
        boolean result = false;
        long startTime = System.currentTimeMillis();
        // Scroll until element found or timeout reached
        while ((System.currentTimeMillis() - startTime) / 1000 < timeout) {
            if (element.isDisplayed()) {
                alignElement(contentView,element,direction);
                result = true;
                break;
            }
            switch (direction) {
                case UP:
                    scrollHalfScreenUpOrDown(contentView,UP);
                    break;
                case DOWN:
                    scrollHalfScreenUpOrDown(contentView,DOWN);
                    break;
                case LEFT:
                    scrollHalfScreenLeftOrRight(contentView,LEFT);
                    break;
                case RIGHT:
                    scrollHalfScreenLeftOrRight(contentView,RIGHT);
                    break;
            }
        }
        return result;
    }
    /**
     * Scroll content to that element be located at the top
     * @param contentView  Frame element enclosing scrollable content.
     * @param element  Element to be aligned with top border.
     */
    public void alignElement(MobileElement contentView, MobileElement element,SwipeElementDirection direction) {
        if(direction == LEFT || direction == RIGHT){
            int startX = element.getCenter().getX();
            int startY = element.getCenter().getY();
            int endX = contentView.getCenter().getX();
            // Swipe rate is 0.3 px/millisecond to avoid momentum scrolling
            int duration = Math.abs(endX - startX) * 3;
            driver.swipe(startX, startY, endX, startY, duration);
        }else {
            int startX = element.getCenter().getX();
            int startY = element.getCenter().getY();
            int endY = contentView.getCenter().getY();
            // Swipe rate is 0.3 px/millisecond to avoid momentum scrolling
            int duration = Math.abs(endY - startY) * 3;
            driver.swipe(startX, startY, startX, endY, duration);
        }
    }
    /**
     * Scrolls content in frame for 1/2 width in specified direction.
     * @param contentView  Frame element enclosing scrollable content.
     * @param direction  Scroll direction: UP or DOWN.
     */
    public void scrollHalfScreenLeftOrRight(MobileElement contentView, SwipeElementDirection direction) {
        int startY = contentView.getCenter().getY();
        int startX = contentView.getCenter().getX();
        int endX;
        if (direction == SwipeElementDirection.RIGHT) {
            endX = contentView.getLocation().getX() + contentView.getSize().getWidth() - 5;
        }
        else {
            endX = contentView.getLocation().getX() + 5;
        }
        // Swipe rate is 0.3 px/millisecond to avoid momentum scrolling
        int duration = Math.abs(endX - startX) * 3;
        int endY = startY;
        driver.swipe(startX, startY, endX, endY, duration);
    }
    /**
     * Scrolls content in frame for 1/2 height in specified direction.
     * @param contentView  Frame element enclosing scrollable content.
     * @param direction  Scroll direction: UP or DOWN.
     */
    public void scrollHalfScreenUpOrDown(MobileElement contentView,SwipeElementDirection direction){
        int startX = contentView.getCenter().getX();
        int startY = contentView.getCenter().getY();
        int endY ;
        if(direction == SwipeElementDirection.DOWN){
            endY = contentView.getLocation().getY() + contentView.getSize().getHeight()-5;
        }else {
            endY = contentView.getLocation().getY() + 5;
        }
        int duration = Math.abs(endY - startY) * 3;
        driver.swipe(startX,startY,startX,endY,duration);
    }
}



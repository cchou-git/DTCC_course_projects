package edu.edu.csc241.aircraft;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.util.Observable;
 
 

// Create a class extends with TimerTask
public class KeyboardObservable extends Observable {
	private static int wPressed = 0;
    public int isWPressed() {
        synchronized (KeyboardObservable.class) {
        	if (wPressed != 0) {
        		setChanged();
        		notifyObservers(new Integer(wPressed));
        		wPressed = 0;
        	}
            return wPressed;
        }
    } 
    
    void setup() { 
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {

            @Override
            public boolean dispatchKeyEvent(KeyEvent ke) {
                synchronized (KeyboardObservable.class) {
                    switch (ke.getID()) {
                    case KeyEvent.KEY_PRESSED: 
                    	switch( ke.getKeyCode() ) { 
                        case KeyEvent.VK_UP:
                        	wPressed = KeyEvent.VK_UP;
                            break;
                        case KeyEvent.VK_DOWN:
                        	wPressed = KeyEvent.VK_DOWN;
                            break;
                        case KeyEvent.VK_LEFT:
                        	wPressed = KeyEvent.VK_LEFT;
                            break;
                        case KeyEvent.VK_RIGHT :
                        	wPressed = KeyEvent.VK_RIGHT;
                            break;
                        default:
                        	break;
                     } 
                    }
                    return false;
                }
            }
        });
	}
	 
}

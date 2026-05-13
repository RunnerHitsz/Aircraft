package edu.hitsz.observer;

import edu.hitsz.prop.AbstractProp;
import java.util.ArrayList;
import java.util.List;

public abstract class PropSubject extends AbstractProp {

    protected List<PropObserver> observers = new ArrayList<>();

    public PropSubject(int locationX, int locationY, int speedX, int speedY) {
        super(locationX, locationY, speedX, speedY);
    }

    public void attach(PropObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(PropObserver observer) {
        observers.remove(observer);
    }

    public void notifyBombEffect() {
        for (PropObserver observer : observers) {
            observer.onBombEffect();
        }
    }

    public void notifyIceEffect() {
        for (PropObserver observer : observers) {
            observer.onIceEffect();
        }
    }
}
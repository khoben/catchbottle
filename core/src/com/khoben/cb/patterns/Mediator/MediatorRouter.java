package com.khoben.cb.patterns.Mediator;
import java.util.HashSet;

/**
 * Created by extle on 21.12.2017.
 */

public class MediatorRouter implements Mediator{
    HashSet<Colleague> colleagues = new HashSet<>();

    public void add(Colleague colleague) {
        colleagues.add(colleague);
    }

    public void remove(Colleague colleague) {
        colleagues.remove(colleague);
    }

    public HashSet<Colleague> get(){
        return colleagues;
    }

    @Override
    public void send(Colleague e) {
        for(Colleague colleague: colleagues) {
            if(colleague != e) {
                colleague.receive(e);
            }
        }
    }
}

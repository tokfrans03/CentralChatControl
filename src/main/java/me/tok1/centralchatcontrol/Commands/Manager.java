package me.tok1.centralchatcontrol.Commands;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class Manager {
    List<Base> c = new ArrayList<>();

    public Manager() {
        c.add(new Help());
        c.add(new SetUrl());
        c.add(new SetPort());
        c.add(new Test());
    }

    public List<Base> get() {
        return c;
    }

    public Base getByName(String name) {
        AtomicReference<Base> b = new AtomicReference<>(new CommandNotFound());
        this.get().forEach(base -> {
            boolean good = false;
            for (String a : base.aliases) {
                if (a.equalsIgnoreCase(name)) {
                    good = true;
                    break;
                }
            }
            if (good) b.set(base);
        });
        return b.get();
    }
}

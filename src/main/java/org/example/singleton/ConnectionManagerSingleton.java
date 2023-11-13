package org.example.singleton;

import org.example.util.ConnectionManager;

public final class ConnectionManagerSingleton {
    private static volatile ConnectionManager instance;
    private static final Object lock = new Object();

    public static ConnectionManager getInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new ConnectionManager();
                }
            }
        }
        return instance;
    }
}

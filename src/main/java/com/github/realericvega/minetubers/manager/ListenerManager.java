package com.github.realericvega.minetubers.manager;

public class ListenerManager {

    private static ListenerManager instance;

    /**
     *
     * @return A single instance of this class
     */
    public static ListenerManager get() {
        if (instance == null)
            instance = new ListenerManager();

        return instance;
    }

    public void registerListeners(String path) {

    }
}

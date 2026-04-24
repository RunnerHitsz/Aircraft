package edu.hitsz.application;

public enum Difficulty {
    EASY("easy"),
    NORMAL("normal"),
    HARD("hard");

    private final String name;

    Difficulty(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Difficulty fromString(String text) {
        for (Difficulty d : Difficulty.values()) {
            if (d.name.equalsIgnoreCase(text)) {
                return d;
            }
        }
        return NORMAL;
    }
}
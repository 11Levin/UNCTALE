package edu.unc.comp301;

import edu.unc.comp301.model.board.Board;
import edu.unc.comp301.model.exception.LevelNotLoadedException;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.*;

public class GameElement {
    private final static Map<Character, String> fullName = new HashMap<>();
    private final static Map<String, Character> shortName = new HashMap<>();
    private final Map<Character, Image> shortToImage = new HashMap<>();
    private final Map<String, Image> fullToImage = new HashMap<>();
    private final List<Character> loadList = new ArrayList<>();
    private static int verticalRadius;
    private static int horizontalRadius;
    public final static int tileSize = 64;
    public final static Random random = new Random();
    public final String[] enemyNames = {"student", "professor", "bullet","ramses"};
    public final int MAX_NUMBER_BUFFS = 3;

    public GameElement() {
        InputStream inputStream = Board.class.getClassLoader().getResourceAsStream("misc/entity_data.txt");
        if (inputStream == null) throw new LevelNotLoadedException("Error loading resources.");
        Scanner scanner = new Scanner(inputStream);
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            line = line.replace(" ", "");
            String[] splitted = line.split(",");

            char c = splitted[0].charAt(0);
            fullName.put(c, splitted[1]);
            shortName.put(splitted[1], c);
            Image image = new Image(splitted[2]);
            shortToImage.put(c, image);
            fullToImage.put(splitted[1], image);
            loadList.add(c);
        }
        scanner.close();
    }

    public String fullName(Character c) {
        if (!fullName.containsKey(c)) throw new IllegalArgumentException("missing key " + c);
        return fullName.get(c);
    }

    public Character shortName(String name) {
        if (!shortName.containsKey(name)) throw new IllegalArgumentException("missing key " + name);
        return shortName.get(name);
    }

    public Image shortToImage(Character c) {
        if (!shortToImage.containsKey(c)) throw new IllegalArgumentException("missing key " + c);
        return shortToImage.get(c);
    }

    public Image fullToImage(String name) {
        if (!fullToImage.containsKey(name)) throw new IllegalArgumentException("missing key " + name);
        return fullToImage.get(name);
    }

    public List<Character> getToLoad() {return loadList;}

    public static int getVerticalRadius() {return verticalRadius;}

    public static int getHorizontalRadius() {return horizontalRadius;}

    /**
     * Gets the distance in tiles from the center (non-inclusive) to either the top or bottom row of tiles
     * @param radius in number of tiles
     * @return whether the change occurred
     */
    public static boolean setVerticalRadius(int radius) {
        if (radius <= 2) return false;
        verticalRadius = radius;
        return true;
    }

    /**
     * Gets the distance in tiles from the center (non-inclusive) to either the left or right column of tiles
     * @param radius in number of tiles
     * @return whether the change occurred
     */
    public static boolean setHorizontalRadius(int radius) {
        if (radius <= 2) return false;
        horizontalRadius = radius;
        return true;
    }
}

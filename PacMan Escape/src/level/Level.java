package level;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Level {
    private ArrayList<Point2D.Double> bananas = new ArrayList<>(), ghosts = new ArrayList<>();
    private String solution;

    private String hint;
    public int delay = 6;

    public void addBanana(Point2D.Double b) {
        bananas.add(b);
    }

    public void addGhost(Point2D.Double g) {
        ghosts.add(g);
    }

    public ArrayList<Point2D.Double> getBananas() {
        return bananas;
    }

    public ArrayList<Point2D.Double> getGhosts() {
        return ghosts;
    }

    public void setSolution(String s) {
        solution = s;
    }

    public String getSolution() {
        return solution;
    }

    public String getHint() {
        return hint;
    }

    public void setHint(String hint) {
        this.hint = hint;
    }
}

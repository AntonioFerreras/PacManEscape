package graphics;

import level.Level;
import math.PointGenerator;
import math.Utils;
import ui.ModernColors;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Graph extends JPanel {
    private Point2D.Double[] points;
    private ArrayList<Point2D.Double> bananas = new ArrayList<>(), ghosts = new ArrayList<>();
    private int monkeyPosx, monkeyPosy;
    private double dx=0, dy=0;
    private double monkeyRot = 0;
    private Timer timer;
    private int delay;

    private int p;

    public Graph(Point2D.Double[] points, Level level) {
        this.points = points;
        this.bananas.addAll(level.getBananas());
        this.ghosts.addAll(level.getGhosts());
        this.delay = level.delay;

        setBackground(new Color(10, 10, 10));

        go(points, level, true);
    }

    public void go(Point2D.Double[] points, Level level, boolean animate) {
        this.points = points;
        this.bananas.removeAll(bananas);
        this.bananas.addAll(level.getBananas());
        this.ghosts.removeAll(ghosts);
        this.ghosts.addAll(level.getGhosts());
        this.delay = level.delay;

        if(animate) {
            p = 0;
            timer = new Timer(delay, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Point2D.Double point = points[p];

                    monkeyPosx = (int) Utils.map(point.x, -5, 5, 0, getWidth());
                    monkeyPosy = (int) Utils.map(point.y, -5, 5, getHeight(), 0);

                    //Check for collision with any bananas
                    for(int b = 0; b < bananas.size(); b++) {
                        Point2D.Double banana = bananas.get(b);
                        if(point.distance(banana) < 0.6) {
                            bananas.remove(b);
                        }
                    }

                    //Check collision with ghosts
                    for(int b = 0; b < ghosts.size(); b++) {
                        Point2D.Double ghost = ghosts.get(b);
                        if(point.distance(ghost) < 0.4) {
                            p = PointGenerator.precision-1;
                            monkeyPosx = -200;
                            monkeyPosy = -200;
                        }
                    }

                    if(p > 0) {
                        dx = points[p-1].x - point.x;
                        dy = points[p-1].y - point.y;
                        monkeyRot = Math.atan2(dy, dx);
                    }

                    repaint();

                    p++;

                    if(p >= PointGenerator.precision-1) {
                        monkeyPosx = -200;
                        monkeyPosy = -200;
                        timer.stop();
                        Main.goButton.setEnabled(true);
                        Main.goButton.setEnabled(true);
                        Main.helpButton.setEnabled(true);
                        Main.prevLevelButton.setEnabled(true);
                        Main.nextLevelButton.setEnabled(true);

                        if(Main.level == 0)
                            Main.prevLevelButton.setEnabled(false);

                        if(bananas.size() == 0) {
                            JOptionPane.showMessageDialog(null, "Well done, on to the next level!");
                            Main.nextLevelAction.actionPerformed(null);
                        }
                    }

                }
            });
            timer.start();
        } else {
            repaint();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        //Draw axes
        g2d.setColor(new Color(25, 25, 170));
        g2d.fillRect(getWidth() / 2 - 1, 0, 3, getHeight());
        g2d.fillRect(0, getHeight() / 2 - 1, getWidth(), 3);

        //Draw grid
        g2d.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 14));
        for (int i = -5; i <= 5; i++) {
            //Draw columns
            int drawX = (int) Utils.map(i, -5, 5, 0, getWidth());
            if(i != 0) {
                g2d.setColor(new Color(25, 25, 170).darker());
                g2d.fillRect(drawX, 0, 1, getHeight());
            }

            //Draw column numbers
            g2d.setColor(Color.white);
            g2d.drawString(Integer.toString(i), drawX - 15, getHeight() / 2 + 15);

            //Draw rows
            int drawY = (int) Utils.map(i, -5, 5, 0, getHeight());
            if(i != 0) {
                g2d.setColor(new Color(25, 25, 170).darker());
                g2d.fillRect(0, drawY, getWidth(), 1);
            }
            //Draw row numbers
            g2d.setColor(Color.white);
            if (i > 0)
                g2d.drawString(Integer.toString(-i), getWidth() / 2 - 20, drawY + 15);
            else if (i < 0)
                g2d.drawString(Integer.toString(-i), getWidth() / 2 - 15, drawY + 15);


        }
        //Draw graph
        g2d.setColor(Color.red);
        for (int p = 0; p < points.length; p++) {
            Point2D.Double point = points[p];
            Point2D.Double previousPoint = point;
            if (p > 0)
                previousPoint = points[p - 1];

            if(!Double.isNaN(previousPoint.y) && !Double.isNaN(point.y)) {
                int drawXPrev = (int) Utils.map(previousPoint.x, -5, 5, 0, getWidth());
                int drawYPrev = (int) Utils.map(previousPoint.y, -5, 5, getHeight(), 0);
                int drawX = (int) Utils.map(point.x, -5, 5, 0, getWidth());
                int drawY = (int) Utils.map(point.y, -5, 5, getHeight(), 0);
                int radius = 2;
                g2d.setStroke(new BasicStroke(2));
                g2d.drawLine(drawXPrev, drawYPrev, drawX, drawY);
            }


        }

        //Draw bananas
        g2d.setColor(Color.white);
        for(Point2D.Double banana : bananas) {
            int drawX = (int) Utils.map(banana.x, -5, 5, 0, getWidth());
            int drawY = (int) Utils.map(banana.y, -5, 5, getHeight(), 0);
            if(monkeyPosx % 100 < 60 || monkeyPosx > getWidth()-10) {
                g2d.fillOval(drawX - 8, drawY - 8, 16, 16);
            }
        }

        //Draw ghosts
        int ghostSize = TextureLoading.ghost.getWidth();
        for(Point2D.Double ghost : ghosts) {
            int drawX = (int) Utils.map(ghost.x, -5, 5, 0, getWidth());
            int drawY = (int) Utils.map(ghost.y, -5, 5, getHeight(), 0);
            g2d.drawImage(TextureLoading.ghost, drawX-ghostSize/2, drawY-ghostSize/2, this);
        }

        //Draw pac-man
        //g2d.fillOval(monkeyPosx - 8, monkeyPosy - 8, 16, 16);
        int pacmanSize = TextureLoading.pacman0.getWidth();
        g2d.rotate(-monkeyRot, monkeyPosx, monkeyPosy);
        if(monkeyPosx % 40 < 20) {
            g2d.drawImage(TextureLoading.pacman0, monkeyPosx-pacmanSize/2, monkeyPosy-pacmanSize/2, this);
        } else {
            g2d.drawImage(TextureLoading.pacman1, monkeyPosx-pacmanSize/2, monkeyPosy-pacmanSize/2, this);
        }
        g2d.rotate(monkeyRot, monkeyPosx, monkeyPosy);
    }


}

package graphics;

import level.Level;
import math.PointGenerator;
import math.Utils;
import ui.MButton;
import ui.MButtonFilled;
import ui.MOptionPane;
import ui.ModernColors;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class Main extends JFrame {
    public static MButtonFilled goButton, helpButton;
    public static MButton prevLevelButton, nextLevelButton;
    public static Graph graph;
    public static JTextField functionField;
    public static JLabel levelLabel;
    public static JTextArea hintArea;
    public static Level[] levels = new Level[10];
    public static int level = 0;

    public Main() {
        setSize(846, 638);
        setResizable(false);
        setTitle("Pac-Man Escape | A function playground! | By: Snurf");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        //Frame panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(null);

        //Background Panel
        JPanel backPane = new JPanel(null);
        backPane.setSize(250, 600);
        backPane.setBackground(ModernColors.clPane.darker());

        //Control panel
        JPanel controlPane = new JPanel(null);
        controlPane.setBounds(10, 10, backPane.getWidth()-20, backPane.getHeight()-20);
        controlPane.setBackground(ModernColors.clPane);

        //Graph display
        graph = new Graph(PointGenerator.generate("x"), levels[level]);
        graph.setLocation(250, 0);
        graph.setSize(600, 600);

        // F(x) label
        JLabel functionLabel = new JLabel("f(x) = ");
        functionLabel.setForeground(ModernColors.cl250);
        functionLabel.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));
        functionLabel.setBounds(15, 15, 50, 30);

        //Input field
        functionField = new JTextField(17);
        functionField.setBackground(ModernColors.clPane);
        functionField.setForeground(ModernColors.cl250);
        functionField.setBorder(new LineBorder(ModernColors.cl250, 2));
        functionField.setCaretColor(ModernColors.cl250);
        functionField.setSelectionColor(ModernColors.cl250);
        functionField.setFont(new Font("Consolas", Font.PLAIN, 15));
        functionField.setBounds(60, 15, 155, 30);
        functionField.setText("x");
        functionField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode() == KeyEvent.VK_ENTER && goButton.isEnabled()) {
                    goButton.setEnabled(false);
                    helpButton.setEnabled(false);
                    prevLevelButton.setEnabled(false);
                    nextLevelButton.setEnabled(false);
                    boolean isValid = true;
                    try {
                        Utils.evaluate(functionField.getText(), 3.332232343);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, "Invalid Function! Make sure the syntax is correct!");
                        isValid = false;
                        goButton.setEnabled(true);
                        helpButton.setEnabled(true);
                        prevLevelButton.setEnabled(true);
                        nextLevelButton.setEnabled(true);
                    }
                    if(isValid)
                        graph.go(PointGenerator.generate(functionField.getText()), levels[level], true);

                }
            }
        });


        helpButton = new MButtonFilled("I Give Up!");
        helpButton.setBounds(120, 145, 95, 30);
        helpButton.setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 14));
        helpButton.setBackColor(ModernColors.clCrimson);
        helpButton.setEnabled(false);
        helpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                functionField.setText(levels[level].getSolution());
                goButton.setEnabled(false);
                helpButton.setEnabled(false);
                prevLevelButton.setEnabled(false);
                nextLevelButton.setEnabled(false);
                graph.go(PointGenerator.generate(functionField.getText()), levels[level], true);
            }
        });

        prevLevelButton = new MButton("<");
        prevLevelButton.setEnabled(false);
        prevLevelButton.setFont(new Font("Microsoft Tai Le", Font.BOLD, 24));
        prevLevelButton.setBounds(15, controlPane.getHeight()-15-35, 35, 35);
        prevLevelButton.setEnabled(false);
        prevLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(level > 0) {
                    hintArea.setText("");
                    level--;
                    levelLabel.setText("Level: " + (level+1) + "/" + levels.length);
                    functionField.setText("x");
                    graph.go(PointGenerator.generate(functionField.getText()), levels[level], false);
                    if(level == 0)
                        prevLevelButton.setEnabled(false);
                }
            }
        });

        nextLevelButton = new MButton(">");
        nextLevelButton.setFont(new Font("Microsoft Tai Le", Font.BOLD, 24));
        nextLevelButton.setBounds(controlPane.getWidth()-35-15, controlPane.getHeight()-15-35, 35, 35);
        nextLevelButton.setEnabled(false);
        nextLevelButton.addActionListener(nextLevelAction);

        //Graph button
        goButton = new MButtonFilled("Go!");
        goButton.setBounds(15, 60, 200, 35);
        goButton.setEnabled(false);
        goButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                goButton.setEnabled(false);
                helpButton.setEnabled(false);
                prevLevelButton.setEnabled(false);
                nextLevelButton.setEnabled(false);
                boolean isValid = true;
                try {
                    Utils.evaluate(functionField.getText(), 3.332232343);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, "Invalid Function! Make sure the syntax is correct!");
                    isValid = false;
                    goButton.setEnabled(true);
                    helpButton.setEnabled(true);
                    prevLevelButton.setEnabled(true);
                    nextLevelButton.setEnabled(true);
                }
                if(isValid)
                    graph.go(PointGenerator.generate(functionField.getText()), levels[level], true);

            }
        });

        hintArea = new JTextArea();
        hintArea.setLineWrap(true);
        hintArea.setWrapStyleWord(true);
        hintArea.setBounds(15, 195, 200, 150);
        hintArea.setBackground(ModernColors.clPane);
        hintArea.setForeground(ModernColors.cl250);
        hintArea.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));

        MButton hintButton = new MButton("Hint");
        hintButton.setBounds(15, 145, 95, 30);
        hintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, levels[level].getHint());
                hintArea.setText("Hint: " + levels[level].getHint());
            }
        });

        MButton functionsListButton = new MButton("List of functions");
        functionsListButton.setBounds(15, 480, 200, 30);
        functionsListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel();
                panel.setSize(380, 330);
                panel.setBackground(ModernColors.clPane);

                JTextArea txtDesc = new JTextArea("abs(): absolute value\n" + "acos() - arc cosine\n"
                        + "asin() - arc sine\n" + "atan() - arc tangent\n" + "cbrt() - cubic root\n"
                        + "ceil() - nearest upper integer\n" + "cosh() - hyperbolic cosine\n"
                        + "exp() - euler's number raised to the power (e^x)\n"
                        + "floor() - nearest lower integer\n" + "log() - logarithmus naturalis (base e)\n"
                        + "log10() - logarithm (base 10)\n" + "log2() - logarithm (base 2)\n"
                        + "sinh() - hyperbolic sine\n" + "tanh() - hyperbolic tangent\n");
                txtDesc.setBackground(ModernColors.clPane);
                txtDesc.setForeground(ModernColors.cl250);
                txtDesc.setEditable(false);
                txtDesc.setBorder(new LineBorder(ModernColors.cl250, 0));
                txtDesc.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 17));
                panel.add(txtDesc);

                MOptionPane oPane = new MOptionPane("Other Functions", panel);
                oPane.getFrame().setAlwaysOnTop(true);
                oPane.setCloseAction(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        oPane.getFrame().dispose();
                    }
                });
            }
        });

        MButton howToPlayButton = new MButton("How to play");
        howToPlayButton.setBounds(15, 440, 200, 30);
        howToPlayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JPanel panel = new JPanel(null);
                panel.setSize(380, 270);
                panel.setBackground(ModernColors.clPane);

                JTextArea txtDesc = new JTextArea("Pac-Man Escape is a math puzzle game where the player is given a grid with goal points laid out on it. The player must write a mathematical function that creates a curve for Pac-Man to follow in order to eat all the goals while avoiding the ghosts. It is comprised of several levels, each getting progressively more difficult and requiring more knowledge of different kinds of math functions.");
                txtDesc.setBackground(ModernColors.clPane);
                txtDesc.setBounds(10, 10, 360, 260);
                txtDesc.setForeground(ModernColors.cl250);
                txtDesc.setLineWrap(true);
                txtDesc.setWrapStyleWord(true);
                txtDesc.setEditable(false);
                txtDesc.setBorder(new LineBorder(ModernColors.cl250, 0));
                txtDesc.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 17));
                panel.add(txtDesc);

                MOptionPane oPane = new MOptionPane("Other Functions", panel);
                oPane.getFrame().setAlwaysOnTop(true);
                oPane.setCloseAction(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        oPane.getFrame().dispose();
                    }
                });
            }
        });

        levelLabel = new JLabel("Level: " + (level+1) + "/" + levels.length);
        levelLabel.setForeground(ModernColors.cl250);
        levelLabel.setFont(new Font("Microsoft Tai Le", Font.PLAIN, 16));
        levelLabel.setBounds(15+35+30, controlPane.getHeight()-15-35, 95, 35);

        controlPane.add(functionLabel);
        controlPane.add(functionField);
        controlPane.add(goButton);
        controlPane.add(hintButton);
        controlPane.add(hintArea);
        controlPane.add(helpButton);
        controlPane.add(prevLevelButton);
        controlPane.add(levelLabel);
        controlPane.add(nextLevelButton);
        controlPane.add(functionsListButton);
        controlPane.add(howToPlayButton);

        //Add components to frame
        backPane.add(controlPane);
        mainPanel.add(backPane);
        mainPanel.add(graph);
        getContentPane().add(mainPanel);

        setVisible(true);

    }

    public static ActionListener nextLevelAction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(level < levels.length-1) {
                hintArea.setText("");
                level++;
                levelLabel.setText("Level: " + (level+1) + "/" + levels.length);
                functionField.setText("x");
                graph.go(PointGenerator.generate(functionField.getText()), levels[level], false);
                prevLevelButton.setEnabled(true);
            }
        }
    };

    public static void main(String[] args) {
        createLevels();

        new Main();
    }

    private static void createLevels() {
        //Load assets
        TextureLoading.loadImages();

        //Create levels
        levels[0] = new Level();
        levels[0].addBanana(new Point2D.Double(-2,0));
        levels[0].addBanana(new Point2D.Double(0,1));
        levels[0].addBanana(new Point2D.Double(4,3));
        levels[0].setSolution("0.5x + 1");
        levels[0].setHint("Remember y = mx+b?");

        levels[1] = new Level();
        levels[1].delay = 10;
        levels[1].addBanana(new Point2D.Double(-2,4));
        levels[1].addBanana(new Point2D.Double(-1,1));
        levels[1].addBanana(new Point2D.Double(0,0));
        levels[1].addBanana(new Point2D.Double(1,1));
        levels[1].addBanana(new Point2D.Double(2,4));
        levels[1].setSolution("x^2");
        levels[1].setHint("This is the standard parabola.");

        levels[2] = new Level();
        levels[2].delay = 10;
        levels[2].addBanana(new Point2D.Double(-1,0));
        levels[2].addBanana(new Point2D.Double(1,4));
        levels[2].addBanana(new Point2D.Double(3,0));
        levels[2].setSolution("-(x-1)^2 + 4");
        levels[2].setHint("Apply transformations to a parabola.");

        levels[3] = new Level();
        levels[3].delay = 14;
        levels[3].addBanana(new Point2D.Double(-1.5,0));
        levels[3].addBanana(new Point2D.Double(1,0));
        levels[3].addBanana(new Point2D.Double(2,0));
        levels[3].addGhost(new Point2D.Double(0,0));
        levels[3].setSolution("(x+1.5)(x-1)(x-2)");
        levels[3].setHint("Try making a cubic function in factored form.");

        levels[4] = new Level();
        levels[4].delay = 10;
        levels[4].addBanana(new Point2D.Double(-4,-0.25));
        levels[4].addBanana(new Point2D.Double(-1,-1));
        levels[4].addBanana(new Point2D.Double(-0.25,-4));
        levels[4].addBanana(new Point2D.Double(4,0.25));
        levels[4].addBanana(new Point2D.Double(1,1));
        levels[4].addBanana(new Point2D.Double(0.25,4));
        levels[4].setSolution("1/x");
        levels[4].setHint("The reciprocal function");

        levels[5] = new Level();
        levels[5].delay = 10;
        levels[5].addBanana(new Point2D.Double(-2,2));
        levels[5].addBanana(new Point2D.Double(0,-1));
        levels[5].addBanana(new Point2D.Double(4,4));
        levels[5].addGhost(new Point2D.Double(1,2));
        levels[5].setSolution("(0.7x - 0.5)^2 - 1");
        levels[5].setHint("Apply transformations to a parabola.");

        levels[6] = new Level();
        levels[6].delay = 10;
        levels[6].addBanana(new Point2D.Double(-4.71238,3));
        levels[6].addBanana(new Point2D.Double(-3.14159,0));
        levels[6].addBanana(new Point2D.Double(0,0));
        levels[6].addBanana(new Point2D.Double(3.141592/2.,3));
        levels[6].addBanana(new Point2D.Double(4,-2.27));
        levels[6].setSolution("3sin(x)");
        levels[6].setHint("This can be done using the sin() function.");

        levels[7] = new Level();
        levels[7].delay = 14;
        levels[7].addBanana(new Point2D.Double(-2,-1));
        levels[7].addBanana(new Point2D.Double(2,1));
        levels[7].addBanana(new Point2D.Double(4,2));
        levels[7].addGhost(new Point2D.Double(0,0));
        levels[7].addGhost(new Point2D.Double(3,1.5));
        levels[7].setSolution("-x^3 + 4x^2 + 3x - 11");
        levels[7].setHint("Think cubic.");

        levels[8] = new Level();
        levels[8].delay = 14;
        levels[8].addBanana(new Point2D.Double(-3,0.11));
        levels[8].addBanana(new Point2D.Double(-0.5,4));
        levels[8].addBanana(new Point2D.Double(0.5,4));
        levels[8].addBanana(new Point2D.Double(0,0));
        levels[8].addBanana(new Point2D.Double(3,0.11));
        levels[8].addGhost(new Point2D.Double(-3.2,-0.8));
        levels[8].addGhost(new Point2D.Double(3.2,-0.8));
        for(int y = 0; y < 5; y++)
            levels[8].addGhost(new Point2D.Double(0,y));
        levels[8].setSolution("1/x^2");
        levels[8].setHint("The reciprocal of the quadratic function.");

        levels[9] = new Level();
        levels[9].delay = 14;
        levels[9].addBanana(new Point2D.Double(-2.542,-0.167));
        levels[9].addBanana(new Point2D.Double(-1.5,0.346));
        levels[9].addBanana(new Point2D.Double(-.448,-0.714));
        levels[9].addBanana(new Point2D.Double(0.6,1.476));
        levels[9].addBanana(new Point2D.Double(1.646,-3.05));
        levels[9].addGhost(new Point2D.Double(1.5,3));
        levels[9].addGhost(new Point2D.Double(-2.8,-1.0));
        levels[9].setSolution("(2^x)sin(3x)");
        levels[9].setHint("Sin wave with exponential vertical scale factor.");
    }
}

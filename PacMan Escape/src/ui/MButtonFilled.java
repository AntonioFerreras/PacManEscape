package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;

public class MButtonFilled extends JButton {

    private Color backColor = ModernColors.clLtBlue;
    public MButtonFilled() {
        setOpaque(true);
        setFocusPainted(false);
        setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 16));
        setForeground(ModernColors.cl250);
        setBackground(ModernColors.clLtBlue);
        setBorder(new LineBorder(ModernColors.cl250, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(backColor.darker());
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(backColor);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }
    public MButtonFilled(String name) {
        setText(name);
        setOpaque(true);
        setFocusPainted(false);
        setFont(new Font("Microsoft JhengHei UI Light", Font.BOLD, 16));
        setForeground(ModernColors.cl250);
        setBackground(ModernColors.clLtBlue);
        setBorder(new LineBorder(ModernColors.cl250, 0));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(backColor.darker());
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(backColor);
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if(!b) {
            setBackground(backColor.darker());
        } else {
            setBackground(backColor);
        }

    }

    public Color getBackColor() {
        return backColor;
    }

    public void setBackColor(Color backColor) {
        setBackground(backColor);
        this.backColor = backColor;
    }
}

package ui;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;


public class MButton extends JButton {
    private Color backColor = ModernColors.clPane;
    private Color highlightColor = ModernColors.cl250;

    public MButton() {
        setOpaque(true);
        setFocusPainted(false);
        setFont(new Font("Microsoft Tai Le", Font.PLAIN, 15));
        setForeground(highlightColor);
        setBackground(backColor);
        setBorder(new LineBorder(highlightColor, 2));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(highlightColor);
                    setForeground(backColor);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                setBackground(backColor);
                setForeground(highlightColor);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }
    public MButton(String name) {
        setText(name);
        setOpaque(true);
        setFocusPainted(false);
        setFont(new Font("Microsoft Tai Le", Font.PLAIN, 15));
        setForeground(highlightColor);
        setBackground(backColor);
        setBorder(new LineBorder(highlightColor, 2));
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                if(isEnabled()) {
                    setBackground(highlightColor);
                    setForeground(backColor);
                    setCursor(new Cursor(Cursor.HAND_CURSOR));
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                    setBackground(backColor);
                setForeground(highlightColor);
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            }
        });
    }

    public void setEnabled(boolean b) {
        super.setEnabled(b);
        if(!b) {
            setBorder(new LineBorder(highlightColor.darker(), 2));
        } else {
            setBorder(new LineBorder(highlightColor, 2));
        }

    }
}

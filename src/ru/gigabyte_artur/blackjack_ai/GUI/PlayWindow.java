package ru.gigabyte_artur.blackjack_ai.GUI;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.*;
import javax.imageio.ImageIO;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class PlayWindow extends JFrame
{
    private JButton button = new JButton("Старт");

    public PlayWindow() throws IOException
    {
        // Отрисовка окна.
        super("Black Jack AI - Playing");
        this.setBounds(100,100,300,300);
        this.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        // Добавление контейнера.
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5,1,2,2));
        // Добавление изображений.
        Container imgContainer = new Container();
        imgContainer.setLayout(new GridLayout(1,2,2,2));
        BufferedImage acePicture = ImageIO.read(new File("src/ru/gigabyte_artur/blackjack_ai/res/ace.jpg"));
        JLabel picLabelAce = new JLabel(new ImageIcon(acePicture));
        imgContainer.add(picLabelAce);
        BufferedImage kingPicture = ImageIO.read(new File("src/ru/gigabyte_artur/blackjack_ai/res/king.jpg"));
        JLabel kingLabelAce = new JLabel(new ImageIcon(kingPicture));
        imgContainer.add(kingLabelAce);
        container.add(imgContainer);
        // Добавление кнопки.
        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Playing");
        }
    }
}
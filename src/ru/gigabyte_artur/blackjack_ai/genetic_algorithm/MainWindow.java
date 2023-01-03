package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainWindow extends JFrame
{
    private JButton ButtonFit = new JButton("Обучение");
    private JButton ButtonPlay = new JButton("Играть");

    public MainWindow() throws IOException
    {
        // Отрисовка окна.
        super("Black Jack AI");
        this.setBounds(100, 100, 300, 300);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Добавление контейнера-сетки.
        Container container = this.getContentPane();
        container.setLayout(new GridLayout(5,1,2,2));
        // Добавление событий для кнопок.
        ButtonFit.addActionListener(new MainWindow.ButtonFitEventListener());
        ButtonPlay.addActionListener(new MainWindow.ButtonPlayEventListener());
        // Добавление кнопок в контейнер.
        container.add(ButtonFit);
        container.add(ButtonPlay);
    }

    class ButtonFitEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Fit");
        }
    }

    class ButtonPlayEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            System.out.println("Play");
        }
    }
}

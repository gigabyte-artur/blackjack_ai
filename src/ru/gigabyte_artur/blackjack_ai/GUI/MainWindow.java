package ru.gigabyte_artur.blackjack_ai.GUI;

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
        super("Black Jack AI - Main");
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
        public void actionPerformed(ActionEvent event)
        {
            try
            {
                FitWindow Window = new FitWindow();
                Window.setVisible(true);
            }
            catch (IOException exception)
            {
                System.out.println(exception.toString());
            }
        }
    }

    class ButtonPlayEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                PlayWindow Window = new PlayWindow();
                Window.setVisible(true);
            }
            catch (IOException exception)
            {
                System.out.println(exception.toString());
            }
        }
    }
}

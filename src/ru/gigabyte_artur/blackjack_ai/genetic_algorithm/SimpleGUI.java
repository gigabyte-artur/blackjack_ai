package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import org.xml.sax.SAXException;
import ru.gigabyte_artur.blackjack_ai.black_jack.BlackJackPlayer;
import ru.gigabyte_artur.blackjack_ai.black_jack.GameBlackJack;
import ru.gigabyte_artur.blackjack_ai.black_jack.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import javax.swing.*;
import javax.xml.parsers.ParserConfigurationException;

public class SimpleGUI extends JFrame
{
    private JButton button = new JButton("Старт");
    private JTextField input = new JTextField("100", 5);
    private JLabel label = new JLabel("Размер поколения:");
    private JCheckBox check = new JCheckBox("загружать из файла", false);

    // Осуществляет селекцию игроков с нуля.
    private static void MakeWorkNewSelection(int generation_size_in, boolean is_from_file_in)
    {
        // Инициализация.
        String filename = "D:\\cars_last.xml";
        GameBlackJack BlackJack1 = new GameBlackJack();
        Generation generation1 = new Generation();
        NeuroNet model_black_jack;
        // Генерация модели.
        model_black_jack = BlackJack1.GenerateModel();
        Player Player1 = new Player(model_black_jack);
        BlackJackPlayer BlackJackPlayer1 = new BlackJackPlayer(Player1);
        // Загрузка игроков.
        if (is_from_file_in)
        {
            System.out.println("Загрузка игроков...");
            try
            {
                generation1.LoadFromFile(filename);
            }
            catch (ParserConfigurationException e)
            {
                throw new RuntimeException(e);
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
            catch (SAXException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            System.out.println("Инициализация случайного поколения...");
            generation1.InitRandom(generation_size_in, model_black_jack, BlackJackPlayer1);
        }
        // Инициализация селекции.
        System.out.println("Начата селекция. В поколении " + generation_size_in + " особей.");
        Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        // Непосредcтвенно селекция.
        for (int i = 0; i < 100000; i++)
        {
//            if (selection_running)
//            {
                System.out.print(i + ": ");
                generation1.SetGamesInSeries(10);
                generation1.Play(BlackJack1);
                generation1.ShowStatic();
                generation2 = selection1.MakeSelection(generation1, BlackJack1);
                generation1 = generation2;
                //if (i % 100 == 0)
                // generation1.SaveToFile(filename);
//            }
//            else
//            {
//                // Процесс остановлен.
//            }
        }
    }

    public SimpleGUI()
    {
        super("Black Jack AI");
        this.setBounds(100,100,300,150);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = this.getContentPane();
        container.setLayout(new GridLayout(4,2,2,2));
        container.add(label);
        container.add(input);
        container.add(check);

        button.addActionListener(new ButtonEventListener());
        container.add(button);
    }

    class ButtonEventListener implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            int new_generation_size;
            new_generation_size = Integer.parseInt(input.getText());
            Runnable task = () ->
            {
                MakeWorkNewSelection(new_generation_size, check.isSelected());
            };
            task.run();
            Thread thread = new Thread(task);
            thread.start();
        }
    }
}
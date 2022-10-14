package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import org.xml.sax.SAXException;
import ru.gigabyte_artur.blackjack_ai.black_jack.BlackJackPlayer;
import ru.gigabyte_artur.blackjack_ai.black_jack.GameBlackJack;
import ru.gigabyte_artur.blackjack_ai.black_jack.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

import javax.xml.parsers.ParserConfigurationException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Random;
import javax.swing.*;



public class Main
{
    private static boolean Runnig;

    // Осуществляет селекцию игроков с нуля.
    private static void MakeWorkNewSelection()
    {
        String filename = "D:\\cars_last.xml";
        GameBlackJack BlackJack1 = new GameBlackJack();
        Generation generation1 = new Generation();
        System.out.println("Загрузка игроков...");
//        generation1.LoadFromFile(filename);
//        Player FirstPlayer = generation1.GetRandomPlayer();
        NeuroNet model_black_jack;
        model_black_jack = BlackJack1.GenerateModel();
        Player Player1 = new Player(model_black_jack);
        BlackJackPlayer BlackJackPlayer1 = new BlackJackPlayer(Player1);
        generation1.InitRandom(100, model_black_jack, BlackJackPlayer1);
        Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        for (int i = 0; i < 100000; i++)
        {
            if (Runnig)
            {
                System.out.print(i + ": ");
                generation1.SetGamesInSeries(10);
                generation1.Play(BlackJack1);
                generation1.ShowStatic();
                generation2 = selection1.MakeSelection(generation1, BlackJack1);
                generation1 = generation2;
                //if (i % 100 == 0)
                // generation1.SaveToFile(filename);
            }
            else
            {
                // Процесс остановлен.
            }
        }
    }

    private static void button_listener_command()
    {
        Runnig = true;
        MakeWorkNewSelection();
    }

    private static void ShowGUI()
    {
        // Frame.
        JFrame frame = new JFrame("BlackJack AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300,300);
        // Button Start.
        JButton button = new JButton("Start");
        frame.getContentPane().add(button); // Adds Button to content pane of frame
        frame.setVisible(true);
        ActionListener button_listener = new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                button_listener_command();
            }

        };
        button.addActionListener(button_listener);
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
    {
        final Random random = new Random();
        Runnig = false;
        ShowGUI();

        /*int rez_game;
        String filename = "D:\\cars_last.xml";
        GameBlackJack BlackJack1 = new GameBlackJack();
        Generation generation1 = new Generation();
        System.out.println("Загрузка игроков...");
        generation1.LoadFromFile(filename);
        Player FirstPlayer = generation1.GetRandomPlayer();
        BlackJackPlayer BlackJackPlayer1 = new BlackJackPlayer(FirstPlayer);
        System.out.println("Загрузка завершена. Ваша игра:");
        rez_game = BlackJack1.PlayWithUser(BlackJackPlayer1);
        System.out.println("-------");
        switch (rez_game)
        {
            case  (1):
                System.out.print("Победил пользователь");
                break;
            case (2):
                System.out.print("Победил компьютер");
                break;
            case (-1):
                System.out.print("Ничья");
                break;
            default:
                System.out.print("Неизвестный победитель");
                break;
        }*/

//        System.out.println("Победил игрок " + rez_game);
//        ArrayList<Player> Players = generation1.GetPlayers();
//        if (Players.size() > 0)
//        {
//            PlayerNum = random.nextInt(Players.size());
//            Player FirstPlayer = Players.get(PlayerNum);
//        }
//        else
//        {
//            System.out.println("Нет игроков в поколении");
//        }

//        if (new File(filename).isFile())
//            generation1.LoadFromFile(filename);
//        else

        /*Hand Deck = new Hand();
        int PlayerRez, PlayerNum;
        Deck.InitDeck();
        Deck.Shuffle();
        generation1.LoadFromFile(filename);
        ArrayList<Player> Players = generation1.GetPlayers();
        if (Players.size() > 0)
        {
            PlayerNum = random.nextInt(Players.size());
            Player FirstPlayer = Players.get(PlayerNum);
            PlayerRez = FirstPlayer.Play(Deck);
            System.out.println(PlayerRez);
        }
        else
        {
            System.out.println("Нет игроков в поколении");
        }*/
    }
}

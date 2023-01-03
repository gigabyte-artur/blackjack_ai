package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import org.xml.sax.SAXException;
import ru.gigabyte_artur.blackjack_ai.GUI.MainWindow;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main
{

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
    {
        MainWindow app = new MainWindow();
        app.setVisible(true);
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

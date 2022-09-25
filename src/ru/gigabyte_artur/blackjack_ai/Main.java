package ru.gigabyte_artur.blackjack_ai;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main
{
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
    {
        final Random random = new Random();
        int rez_game;
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
        }
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
        /*

        NeuroNet model_black_jack;
        model_black_jack = BlackJack1.GenerateModel();
        generation1.InitRandom(100, model_black_jack);
        Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        for (int i = 0; i < 100000; i++)
        {
            System.out.print(i + ": ");
            generation1.SetGamesInSeries(10);
            generation1.Play(BlackJack1);
            generation1.ShowStatic();
            generation2 = selection1.MakeSelection(generation1);
            generation1 = generation2;
            if (i % 100 == 0)
                generation1.SaveToFile(filename);
        }*/

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

package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import org.xml.sax.SAXException;
import ru.gigabyte_artur.blackjack_ai.GUI.MainWindow;
import ru.gigabyte_artur.blackjack_ai.black_jack.BlackJackPlayer;
import ru.gigabyte_artur.blackjack_ai.black_jack.GameBlackJack;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;
import ru.gigabyte_artur.blackjack_ai.xo.GameXO;
import ru.gigabyte_artur.blackjack_ai.xo.XoBoard;
import ru.gigabyte_artur.blackjack_ai.xo.XoPlayer;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class Main
{

    // Игра крестики-нолики. В консоли с обучением.
    private static void main_XO()
    {
        XoBoard board1 = new XoBoard();
        board1.SetXToCell(0 , 1);
        board1.SetOToCell(1 , 2);
        board1.Show();
        // Инициализация.
        String filename = "D:\\cars_last.xml";
        GameXO XO1 = new GameXO();
        Generation generation1 = new Generation();
        NeuroNet model_XO;
        // Генерация модели.
        model_XO = GameXO.GenerateModel();
        Player Player1 = new Player(model_XO);
        XoPlayer XOPlayer1 = new XoPlayer(Player1);
        System.out.println("Загрузка игроков...");
//        // Загрузка игроков.
//        try
//        {
//            generation1.LoadFromFile(filename);
//        }
//        catch (ParserConfigurationException e)
//        {
//            throw new RuntimeException(e);
//        }
//        catch (IOException e)
//        {
//            throw new RuntimeException(e);
//        }
//        catch (SAXException e)
//        {
//            throw new RuntimeException(e);
//        }
        System.out.println("Инициализация случайного поколения...");
        generation1.InitRandom(100, model_XO, XOPlayer1);
        // Инициализация селекции.
        System.out.println("Начата селекция. В поколении " + 100 + " особей.");
        Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        // Непосредcтвенно селекция.
        for (int i = 0; i < 100000; i++)
        {
            System.out.print(i + ": ");
            generation1.SetGamesInSeries(10);
            generation1.Play(XO1);
            generation1.ShowStatic();
            generation2 = selection1.MakeSelection(generation1, XO1);
            generation1 = generation2;
//            // Сохранение поколений.
//            if (i % 100 == 0)
//            {
//            generation1.SaveToFile(filename);
//            }
//            else
//            {
//                // Процесс остановлен.
//            }
        }
    }

    // Игра Блек-Джек. С графическим интерфейсом.
    private static void main_BlackJack_GUI() throws IOException
    {
        MainWindow app = new MainWindow();
        app.setVisible(true);
    }

    // Игра Блэк-Джек с пользователем. В консоли без обучения.
    private static void main_BlackJack_WithUser() throws IOException, SAXException, ParserConfigurationException
    {
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
    }

    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException
    {
        //main_BlackJack_GUI();
        main_XO();
        //main_BlackJack_WithUser();
    }
}

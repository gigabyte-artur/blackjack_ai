package ru.gigabyte_artur.blackjack_ai.black_jack;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.gaming.TwoPlayersGame;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

import java.util.Scanner;

public class GameBlackJack extends TwoPlayersGame
{

    public Hand deck;       // Колода текущей игры.

    // Генерирует пустую колоду в текущую руку.
    private Hand GenerateDeck()
    {
        Hand rez = new Hand();
        rez.InitDeck();
        rez.Shuffle();
        return rez;
    }

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Play()
    {
        int rez = -1, score1, score2;
        this.deck = this.GenerateDeck();
        // Игра первого игрока.
        score1 = this.getPlayer1().Play(this, true);
        // Игра второго игрока.
        score2 = this.getPlayer2().Play(this, true);
        // Сравнение.
        rez = GameBlackJack.ChooseWinnerByScore(score1, score2);
        return rez;
    }

    // Играет игру игрока player_in с пользователем (в консольном режиме). Если победил пользователь - возвращает 1,
    //  если победил игрок - возвращает 2. В случае ничьи возвращает -1.
    public int PlayWithUser(BlackJackPlayer player_in)
    {
        int rez = -1;
        int score1 = 0, score2, input = 0;
        Hand deck, user_hand = new Hand();
        Scanner scanner = new Scanner(System.in);
        deck = this.GenerateDeck();
        this.setDeck(deck);
        user_hand.Clear();
        // Игра пользователя.
        while ((score1 < 22) & (input != 2))
        {
            System.out.println("Тяните карты. 1 - взять ещё, 2 - остановиться:");
            input = scanner.nextInt();
            if (input == 1) {
                user_hand.DrawCard(deck);
                user_hand.Show();
                score1 = GameBlackJack.SummHand(user_hand);
                System.out.println(score1);
            }
        }
        // Игра игрока.
        System.out.println("------");
        System.out.println("Игра соперника:");
        score2 = player_in.Play(this, false);
        // Сравнение.
        rez = GameBlackJack.ChooseWinnerByScore(score1, score2);
        return rez;
    }

    // Инициализирует игру.
    public void Init()
    {

    }

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        NeuroNet rez = new NeuroNet();
        rez.GenerateAddDenseLayer(54, true, false);
        rez.GenerateAddDenseLayer(54, false, false);
        rez.GenerateAddDenseLayer(54, false, false);
        rez.GenerateAddDenseLayer(54, false, false);
        rez.GenerateAddDenseLayer(1, false, true);
        rez.Compile();
        return rez;
    }

    // Определяет победителя по набранным очкам score1_in и score2_in. Возвращает номер победителя.
    // В случае ничьи возвращает -1.
    public static int ChooseWinnerByScore(int score1_in, int score2_in)
    {
        int rez = -1;
        if (score1_in < 22)
        {
            if (score2_in < 22)
            {
                if (score1_in > score2_in)
                    rez = 1;
                else if (score1_in == score2_in)
                    rez = -1;
                else
                    rez = 2;
            }
            else
            {
                rez = 1;
            }
        }
        else
        {
            if (score2_in < 22)
                rez = 2;
            else
                rez = -1;
        }
        return rez;
    }

    @Override
    public Player NewPlayer()
    {
        BlackJackPlayer rez = new BlackJackPlayer();
        return rez;
    }

    public void setDeck(Hand deck_in)
    {
        this.deck = deck_in;
    }

    // Подсчитывает сумму карт в колоде deck_in.
    public static int SummHand(Hand deck_in)
    {
        int rez = 0;
        int curr_value = 0;
        for (Card curr_card : deck_in.getCards())
        {
            curr_value = curr_card.GetValue();
            switch (curr_value)
            {
                case  (Card.Value_Jack):
                    rez = rez + 2;
                    break;
                case (Card.Value_Queen):
                    rez = rez + 3;
                    break;
                case (Card.Value_King):
                    rez = rez + 4;
                    break;
                case (Card.Value_Ace):
                    rez = rez + 11;
                    break;
                default:
                    rez = rez + curr_value;
                    break;
            }
        }
        return rez;
    }
}

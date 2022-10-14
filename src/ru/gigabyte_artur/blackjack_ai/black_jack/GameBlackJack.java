package ru.gigabyte_artur.blackjack_ai.black_jack;

import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

import java.util.Scanner;

public class GameBlackJack extends TwoPlayersGame
{

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
        Hand deck;
        deck = this.GenerateDeck();
        // Игра первого игрока.
        score1 = this.player1.Play(deck, false);
        // Игра второго игрока.
        score2 = this.player2.Play(deck, false);
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
        user_hand.Clear();
        // Игра пользователя.
        while ((score1 < 22) & (input != 2))
        {
            System.out.println("Тяните карты. 1 - взять ещё, 2 - остановиться:");
            input = scanner.nextInt();
            if (input == 1) {
                user_hand.DrawCard(deck);
                user_hand.Show();
                score1 = user_hand.SummHand();
                System.out.println(score1);
            }
        }
        // Игра игрока.
        System.out.println("------");
        System.out.println("Игра соперника:");
        score2 = player_in.Play(deck, true);
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
        rez.GenerateAddLayer(54, true, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(1, false, true);
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
    public Player NewPlayer() {
        BlackJackPlayer rez = new BlackJackPlayer();
        return rez;
    }
}

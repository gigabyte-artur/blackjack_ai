package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Hand;

public class GameBlackJack
{
    Player player1;
    Player player2;

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
        score1 = this.player1.Play(deck);
        System.out.println("-------");
        // Игра второго игрока.
        score2 = this.player2.Play(deck);
        // Сравнение.
        rez = GameBlackJack.ChooseWinnerByScore(score1, score2);
        return rez;
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

    // Устанавливает игроков player1_in и player2_in.
    public void SetPlayers(Player player1_in, Player player2_in)
    {
        this.player1 = player1_in;
        this.player2 = player2_in;
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
}

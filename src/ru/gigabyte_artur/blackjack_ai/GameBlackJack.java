package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Hand;

public class GameBlackJack
{
    // Играет текущую игру. Возвращает номер победителя.
    public int Play()
    {
        int rez = -1;
        int summ1, summ2;
        Hand deck = new Hand();
        Hand hand1 = new Hand();
        Hand hand2 = new Hand();
        deck.InitDeck();
        deck.Shuffle();
        deck.Show();
        deck.PullCard(0, hand1);
        deck.PullCard(0, hand1);
        deck.PullCard(0, hand1);
        hand1.Show();
        deck.PullCard(0, hand2);
        deck.PullCard(0, hand2);
        deck.PullCard(0, hand2);
        hand2.Show();
        summ1 = hand1.SummHand();
        summ2 = hand2.SummHand();
        System.out.println(summ1 + " " + summ2);
        if (summ1 > summ2)
            rez = 1;
        else
            rez = 2;
        return rez;
    }
}

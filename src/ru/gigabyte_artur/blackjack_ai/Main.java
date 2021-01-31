package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Card;
import ru.gigabyte_artur.blackjack_ai.Hand;

public class Main
{

    public static void main(String[] args)
    {
        Hand hand1 = new Hand();
        Card card1 = new Card();
        card1.Set(4, 9);
        Card card2 = new Card();
        card2.Set(2, 8);
        hand1.Clear();
        hand1.AddCard(card1);
        hand1.AddCard(card2);
        hand1.Show();
    }
}

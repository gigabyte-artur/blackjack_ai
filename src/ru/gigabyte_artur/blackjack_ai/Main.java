package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Card;
import ru.gigabyte_artur.blackjack_ai.Hand;

public class Main
{

    public static void main(String[] args)
    {
//        Hand hand1 = new Hand();
//        Hand hand2 = new Hand();
        Card card1 = new Card();
//        card1.Set(Card.Suite_Spades(), 9);
//        Card card2 = new Card();
//        card2.Set(Card.Suite_Diamonds(), 8);
//        Card card3 = new Card();
//        card3.Set(Card.Suite_Hearts(), 7);
//        hand1.Clear();
//        hand1.AddCard(card1);
//        hand1.AddCard(card2);
//        hand1.Show();
//        System.out.println();
//        hand2.AddCard(card3);
//        hand2.Show();
//        System.out.println("------");
//        hand1.move_card(card2, hand2);
//        hand1.Show();
//        System.out.println();
//        hand2.Show();
//        System.out.println();
        Hand deck = new Hand();
        Hand hand1 = new Hand();
        deck.InitDeck();
        deck.Show();
        System.out.println("------");
        deck.Shuffle();
        deck.Show();
        System.out.println("------");
        deck.PullCard(0, hand1);
        deck.PullCard(0, hand1);
        hand1.Show();
        System.out.println("------");
        deck.Show();
    }
}

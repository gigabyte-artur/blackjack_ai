package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.Collections;

import ru.gigabyte_artur.blackjack_ai.Card;

public class Hand
{
    private ArrayList<Card> Cards = new ArrayList<Card>();

    // Добавляет карту card_in в руку.
    public void AddCard(Card card_in)
    {
        Cards.add(card_in);
    }

    // Выводит содержимое руки на экран.
    public void Show()
    {
        for (Card curr_card: Cards)
        {
            curr_card.Show();
            System.out.print(", ");
        }
    }

    // Очищает содержимое руки.
    public void Clear()
    {
        Cards.clear();
    }

    // Перемещает карту
    public void move_card(Card card_in, Hand hand_chng)
    {
        ArrayList<Card> new_list = new ArrayList<Card>();
        boolean moved = false;
        for (Card curr_card:Cards)
        {
            if (!curr_card.equals(card_in))
            {
                new_list.add(curr_card);
            }
            else
            {
                moved = true;
            }
        }
        if (moved)
        {
            this.Cards.clear();
            for (Card curr_card : new_list) {
                this.AddCard(curr_card);
            }
            hand_chng.AddCard(card_in);
        }
        else
        {
            // Перемещение не состоялось. Оставляем как есть.
        }
    }

    // Инициалихзирует текущую руку стандартной колодой.
    public void InitDeck()
    {
        for (int i = 2; i <= 11; i++)
        {
            // Червы.
            Card new_card_hearts = new Card();
            new_card_hearts.Set(Card.Suite_Hearts(), i);
            this.AddCard(new_card_hearts);
            // Буби.
            Card new_card_diamonds = new Card();
            new_card_diamonds.Set(Card.Suite_Diamonds(), i);
            this.AddCard(new_card_diamonds);
            // Трефы.
            Card new_card_clubs = new Card();
            new_card_clubs.Set(Card.Suite_Clubs(), i);
            this.AddCard(new_card_clubs);
            // Пики.
            Card new_card_spades = new Card();
            new_card_spades.Set(Card.Suite_Spades(), i);
            this.AddCard(new_card_spades);
        }
    }

    //
    public void Shuffle()
    {
        Collections.shuffle(this.Cards);
    }
}

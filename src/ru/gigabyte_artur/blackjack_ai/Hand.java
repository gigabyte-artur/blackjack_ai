package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
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

}

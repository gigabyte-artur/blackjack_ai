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

}

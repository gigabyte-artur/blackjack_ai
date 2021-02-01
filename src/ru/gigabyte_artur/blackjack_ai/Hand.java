package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.Collections;

import ru.gigabyte_artur.blackjack_ai.Card;

public class Hand
{
    private ArrayList<Card> Cards = new ArrayList<>();

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
        System.out.println();
    }

    // Очищает содержимое руки.
    public void Clear()
    {
        Cards.clear();
    }

    // Перемещает карту card_in из текущей руки в руку hand_chng.
    public void MoveCard(Card card_in, Hand hand_chng)
    {
        ArrayList<Card> new_list = new ArrayList<>();
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
            new_card_hearts.Set(Card.Suite_Hearts, i);
            this.AddCard(new_card_hearts);
            // Буби.
            Card new_card_diamonds = new Card();
            new_card_diamonds.Set(Card.Suite_Diamonds, i);
            this.AddCard(new_card_diamonds);
            // Трефы.
            Card new_card_clubs = new Card();
            new_card_clubs.Set(Card.Suite_Clubs, i);
            this.AddCard(new_card_clubs);
            // Пики.
            Card new_card_spades = new Card();
            new_card_spades.Set(Card.Suite_Spades, i);
            this.AddCard(new_card_spades);
        }
    }

    // Перемешивает карты в текущей руке.
    public void Shuffle()
    {
        Collections.shuffle(this.Cards);
    }

    // Возвращает карту из текущей колоды по идентификатору id_in.
    public Card GetCardById(int id_in)
    {
        Card rez = new Card();
        for (int i = 0; i <= id_in; i++)
        {
            rez = this.Cards.get(i);
        }
        return rez;
    }

    // Перемещает карту с номером id_in из текущей руки в hand_chng.
    public void PullCard(int id_in, Hand hand_chng)
    {
        Card card1 = new Card();
        card1 = this.GetCardById(id_in);
        this.MoveCard(card1, hand_chng);
    }

    // Подсчитывает сумму очков карт на руках.
    public int SummHand()
    {
        int rez = 0;
        int summ = 0;
        for (Card curr_card : this.Cards)
        {
            summ = summ + curr_card.GetValue();
        }
        if (summ > 21)
            rez = 0;
        else
            rez = summ;
        return rez;
    }
}

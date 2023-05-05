package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.black_jack.Card;
import java.util.ArrayList;

public class PokerCombination
{
    private int CombinationType;         // Тип комбинации.
    private int HightestCard;            // Старшая карта.

    final public int Type_None = 0;          // Пустая комбинация.
    final public int Type_HightCard = 1;     // Старшая карта.
    final public int Type_OnePair = 2;       // Одна пара.
    final public int Type_TwoPair = 3;       // Две пары.
    final public int Type_Set = 4;           // Сет (три карты).
    final public int Type_StreetSimple = 5;  // (простой) Стрит.
    final public int Type_FlashSimple = 6;   // (простой) Флеш (5 карт одной масти).
    final public int Type_FullHouse = 7;     // Фулл хаус (3+2).
    final public int Type_Four = 8;          // Каре.
    final public int Type_StreetFlash = 9;   // Стрит-флеш.
    final public int Type_RoyalFlash = 11;   // Роял-флеш.

    public PokerCombination()
    {
        setCombinationType(Type_None);
        setHightestCard(Card.Value_None);
    }

    public PokerCombination(int CombinationType_in, int HightestCard_in)
    {
        setCombinationType(CombinationType_in);
        setHightestCard(HightestCard_in);
    }

    public PokerCombination(ArrayList<Card> cards_in)
    {
        final int CardValueNone = Card.Value_None;
        int CardValueOnePair;
        setCombinationType(Type_None);
        setHightestCard(CardValueNone);
        CardValueOnePair = IsOnePair(cards_in);
        if (CardValueOnePair != CardValueNone)
        {
            setCombinationType(Type_OnePair);
            setHightestCard(CardValueOnePair);
        }
        else
        {
            // Комбинаций не найдено.
            setCombinationType(Type_None);
            setHightestCard(CardValueNone);
        }
    }

    public void setCombinationType(int CombinationType_in)
    {
        this.CombinationType = CombinationType_in;
    }

    public void setHightestCard(int HightestCard_in)
    {
        this.HightestCard = HightestCard_in;
    }

    public int getCombinationType()
    {
        return this.CombinationType;
    }

    public int getHightestCard()
    {
        return this.HightestCard;
    }

    // Определяет, что в массиве карт cards_in есть комбинация Одна пара. Вовзвращает старшую карту в случае успеха и
    // пустую карту в случае неуспеха.
    private int IsOnePair(ArrayList<Card> cards_in)
    {
        int rez = Card.Value_None;       // Пустая карта.
        int value1, value2;
        for (Card curr_cards_in1:cards_in)
        {
            value1 = curr_cards_in1.GetValue();
            for (Card curr_cards_in2:cards_in)
            {
                value2 = curr_cards_in2.GetValue();
                if ((value1 == value2) && (!curr_cards_in1.equals(curr_cards_in2)))
                {
                    if (value1 > rez)
                    {
                        rez = value1;
                    }
                    else
                    {
                        // Текущий результат выше, либо аналогичный. Не обновляем.
                    }
                }
                else
                {
                    // Не совпадают значения, либо это одна и та же карта.
                }
            }
        }
        return rez;
    }

    // Отображает комбинацию.
    public void Show()
    {
        switch (this.getCombinationType())
        {
            case (Type_None):
                break;
            case (Type_OnePair):
                System.out.print("One pair (");
                Card.PrintValue(this.getHightestCard());
                System.out.println(")");
                break;
        }
    }
}

package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.card_game.Card;
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
        // Инициализация.
        final int CardValueNone = Card.Value_None;
        int CardValueOnePair, CardValueHightCard, CardValueSimpleFlash, CardValueSet;
        this.Set(Type_None, CardValueNone);
        // Простой Флеш.
        CardValueSet = IsSet(cards_in);
        if (CardValueSet != CardValueNone)
        {
            this.Set(Type_Set, CardValueSet);
        }
        else {
            // Простой Флеш.
            CardValueSimpleFlash = IsFlashSimple(cards_in);
            if (CardValueSimpleFlash != CardValueNone) {
                this.Set(Type_FlashSimple, CardValueSimpleFlash);
            } else {
                // Одна пара.
                CardValueOnePair = IsOnePair(cards_in);
                if (CardValueOnePair != CardValueNone) {
                    this.Set(Type_OnePair, CardValueOnePair);
                } else {
                    // Старшая карта.
                    CardValueHightCard = IsHightCard(cards_in);
                    if (CardValueHightCard != CardValueNone) {
                        this.Set(Type_HightCard, CardValueHightCard);
                    } else {
                        // Комбинаций не найдено.
                        this.Set(Type_None, CardValueNone);
                    }
                }
            }
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

    // Устанавливает в текущую комбинацию значения полей.
    public void Set(int CombinationType_in, int HightestCard_in)
    {
        this.CombinationType = CombinationType_in;
        this.HightestCard = HightestCard_in;
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

    // Определяет, что в массиве карт cards_in есть комбинация Старшая карта. Вовзвращает старшую карту в случае успеха и
    // пустую карту в случае неуспеха.
    private int IsHightCard(ArrayList<Card> cards_in)
    {
        int rez = Card.Value_None;       // Пустая карта.
        int value1;
        for (Card curr_cards_in1:cards_in)
        {
            value1 = curr_cards_in1.GetValue();
            if ((value1 == Card.Value_Jack) || (value1 == Card.Value_Queen) || (value1 == Card.Value_King) || (value1 == Card.Value_Ace))
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
        return rez;
    }

    // Определяет, что в массиве карт cards_in есть комбинация Простой Флеш. Вовзвращает старшую карту в случае успеха и
    // пустую карту в случае неуспеха.
    private int IsFlashSimple(ArrayList<Card> cards_in)
    {
        // Инициализация.
        int rez = Card.Value_None;       // Пустая карта.
        int max_card_hearts = Card.Value_None, max_card_diamonds = Card.Value_None,
                max_card_clubs = Card.Value_None, max_card_spades = Card.Value_None;        // Максимальные значения карт по мастям.
        int count_hearts = 0, count_diamonds = 0, count_clubs = 0, count_spades = 0;        // Количество карт с мастью в комбинации.
        int suite1 = Card.Suite_None, value1 = Card.Value_None;
        // Обход комбиации, определение длины максимальной очереди по каждой масти.
        for (Card curr_cards_in1:cards_in)
        {
            suite1 = curr_cards_in1.GetSuite();
            value1 = curr_cards_in1.GetValue();
            switch (suite1)
            {
                case Card.Suite_Hearts:
                    count_hearts = count_hearts + 1;
                    if (max_card_hearts < value1)
                        max_card_hearts = value1;
                    break;
                case Card.Suite_Diamonds:
                    count_diamonds = count_diamonds + 1;
                    if (max_card_diamonds < value1)
                        max_card_diamonds = value1;
                    break;
                case Card.Suite_Clubs:
                    count_clubs = count_clubs + 1;
                    if (max_card_clubs < value1)
                        max_card_clubs = value1;
                    break;
                case Card.Suite_Spades:
                    count_spades = count_spades + 1;
                    if (max_card_spades < value1)
                        max_card_spades = value1;
                    break;
            }
        }
        // Анализ полученных длин очередей и вывод результата.
        if (count_hearts >= 5)
            rez = max_card_hearts;
        if (count_diamonds >= 5)
            rez = max_card_diamonds;
        if (count_clubs >= 5)
            rez = max_card_clubs;
        if (count_spades >= 5)
            rez = max_card_spades;
        return rez;
    }

    // Определяет, что в массиве карт cards_in есть комбинация Сет. Вовзвращает старшую карту в случае успеха и
    // пустую карту в случае неуспеха.
    private int IsSet(ArrayList<Card> cards_in)
    {
        int rez = Card.Value_None;       // Пустая карта.
        int value1, value2, value3;
        for (Card curr_cards_in1:cards_in)
        {
            value1 = curr_cards_in1.GetValue();
            for (Card curr_cards_in2:cards_in)
            {
                value2 = curr_cards_in2.GetValue();
                for (Card curr_cards_in3:cards_in) {
                    value3 = curr_cards_in3.GetValue();
                    if ((value1 == value2) && (value1 == value3) && (!curr_cards_in1.equals(curr_cards_in2))
                    && (!curr_cards_in1.equals(curr_cards_in3)) && (!curr_cards_in2.equals(curr_cards_in3)))
                    {
                        if (value1 > rez) {
                            rez = value1;
                        } else {
                            // Текущий результат выше, либо аналогичный. Не обновляем.
                        }
                    } else {
                        // Не совпадают значения, либо это одна и та же карта.
                    }
                }
            }
        }
        return rez;
    }

    // TODO: Реализовать прочие комбинации.

    // Печатает в консоль комбинацию с текстовым представлением CombinationName_in и значением старшей карты
    // CardValue_in.
    private void PrintCombinationByName(String CombinationName_in, int CardValue_in)
    {
        System.out.print("* " + CombinationName_in + " (");
        Card.PrintValue(CardValue_in);
        System.out.println(")");
    }

    // Отображает комбинацию.
    public void Show()
    {
        switch (this.getCombinationType())
        {
            case (Type_None):
                System.out.println("<none>");
                break;
            case (Type_FlashSimple):
                PrintCombinationByName("Flash Simple", this.getHightestCard());
                break;
            case (Type_Set):
                PrintCombinationByName("Set", this.getHightestCard());
                break;
            case (Type_OnePair):
                PrintCombinationByName("One pair", this.getHightestCard());
                break;
            case (Type_HightCard):
                PrintCombinationByName("Hight card", this.getHightestCard());
                break;
        }
    }
}

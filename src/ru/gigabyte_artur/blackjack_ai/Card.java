package ru.gigabyte_artur.blackjack_ai;

public class Card {
    private int suite;      // Масть.
    private int value;      // Значение.

    // Значение масти Червы.
    public static int Suite_Hearts()
    {
        return 1;
    }

    // Значение масти Буби.
    public static int Suite_Diamonds()
    {
        return 2;
    }

    // Значение масти Трефы.
    public static int Suite_Clubs()
    {
        return 3;
    }

    // Значение масти Пики.
    public static int Suite_Spades()
    {
        return 4;
    }

    // Выводит карту на экран.
    public void Show()
    {
        System.out.print("(" + this.suite + " " + this.value + ")");
    }

    // Устанавливамет карту с мастью suite_in и значением value_in.
    public void Set(int suite_in, int value_in)
    {
        this.suite = suite_in;
        this.value = value_in;
    }

    // Возвращает масть.
    public int GetSuite()
    {
        return this.suite;
    }

    // Возвращает значение карты.
    public int GetValue()
    {
        return this.value;
    }

    // Переопределение оперции сравнения.
    public boolean equals(Card card_in)
    {
        boolean rez = false;
        if (card_in == null)
            rez = false;
        else
        {
            boolean equals_suite, equals_value;
            equals_suite = (card_in.GetSuite() == this.GetSuite());
            equals_value = (card_in.GetValue() == this.GetValue());
            rez = ((equals_suite) && (equals_value));
        }
        return rez;
    }


}

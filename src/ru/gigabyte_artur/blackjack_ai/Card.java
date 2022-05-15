package ru.gigabyte_artur.blackjack_ai;

public class Card {
    private int suite;      // Масть.
    private int value;      // Значение.

    public static final int Suite_Hearts = 1;       // Значение масти Червы.
    public static final int Suite_Diamonds = 2;     // Значение масти Буби.
    public static final int Suite_Clubs = 3;        // Значение масти Трефы.
    public static final int Suite_Spades = 4;       // Значение масти Пики.
    public static final int MAX_VALUE = 11;         // Максимальное значение карты.

    // Выводит карту на экран.
    public void Show()
    {
        System.out.print("(");
        switch (this.suite)
        {
            case  (Suite_Hearts):
                System.out.print("H");
                break;
            case (Suite_Diamonds):
                System.out.print("D");
                break;
            case (Suite_Clubs):
                System.out.print("C");
                break;
            case (Suite_Spades):
                System.out.print("S");
                break;
            default:
                break;
        }
        System.out.print(" " + this.value + ")");
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

package ru.gigabyte_artur.blackjack_ai;

public class Card {
    private int suite;      // Масть.
    private int value;      // Значение.

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

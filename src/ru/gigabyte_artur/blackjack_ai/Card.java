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

}

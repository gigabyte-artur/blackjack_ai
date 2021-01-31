package ru.gigabyte_artur.blackjack_ai;

public class Card {
    private int suite;
    private int value;

    public void Show()
    {
        System.out.println("(" + this.suite + " " + this.value + ")");
    }

    public void Set(int suite_in, int value_in)
    {
        this.suite = suite_in;
        this.value = value_in;
    }

    public int GetSuite()
    {
        return this.suite;
    }

    public int GetValue()
    {
        return this.value;
    }

}

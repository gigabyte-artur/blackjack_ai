package ru.gigabyte_artur.blackjack_ai;

public class Card {
    int suite;
    int value;

    public void Show()
    {
        System.out.println("(" + this.suite + " " + this.value + ")");
    }

    public void Set(int suite_in, int value_in)
    {
        this.suite = suite_in;
        this.value = value_in;
    }
}

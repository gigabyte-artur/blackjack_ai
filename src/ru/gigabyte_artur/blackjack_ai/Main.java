package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.GameBlackJack;

public class Main
{
    public static void main(String[] args)
    {
        int rez;
        GameBlackJack game1 = new GameBlackJack();
        rez = game1.Play();
        System.out.println(rez);
    }
}

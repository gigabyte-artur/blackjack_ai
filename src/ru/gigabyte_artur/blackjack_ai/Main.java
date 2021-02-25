package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;

public class Main
{
    public static void main(String[] args)
    {
        Generation generation1 = new Generation();
        Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        generation1.InitRandom(100);
        generation1.Play();
        generation2 = selection1.MakeSelection(generation1);
    }
}

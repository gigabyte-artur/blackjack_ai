package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.GameBlackJack;

public class Main
{

    public static void main(String[] args)
    {
//        int rez_game;
//        GameBlackJack game1 = new GameBlackJack();
//        rez_game = game1.Play();
//        System.out.println(rez_game);
        Layer layer1 = new Layer();
        layer1.GenerateLayer(54);
        layer1.ShowLayer();
    }
}

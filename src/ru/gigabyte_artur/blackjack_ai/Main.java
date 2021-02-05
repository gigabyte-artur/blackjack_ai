package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.GameBlackJack;

public class Main
{

    public static void main(String[] args)
    {
        NeuroNet my_net = new NeuroNet();
        my_net.GenerateAddLayer(54);
        my_net.GenerateAddLayer(54);
        my_net.GenerateAddLayer(54);
        my_net.GenerateAddLayer(1);
        my_net.Show();
        my_net.Compile();
    }
}

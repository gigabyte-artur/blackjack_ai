package ru.gigabyte_artur.blackjack_ai;

public class Player
{
    private Hand hand = new Hand();
    private NeuroNet neuro_net = new NeuroNet();

    // Запускает новую игру.
    public void NewGame(NeuroNet neuro_net_in)
    {
        this.hand.Empty();
        this.neuro_net.CopyModel(neuro_net_in);
    }
}


package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Hand;

public class GameBlackJack
{
    // Играет текущую игру. Возвращает номер победителя.
    public int Play()
    {
        int rez = -1;
        boolean decision;
        Hand deck = new Hand();
        NeuroNet model = new NeuroNet();
        NeuroNet player_net;
        deck.InitDeck();
        deck.Shuffle();
        model = GameBlackJack.GenerateModel();
        Player player1 = new Player();
        player1.NewGame(model);
        player1.DrawCard(deck);
        player1.DrawCard(deck);
        player1.ShowHand();
        player1.HandToInputSignals();
        player_net = player1.GetNeuroNet();
        player_net.RandomSignals();
        decision = player1.Decide();
        System.out.println(decision);
        return rez;
    }

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        NeuroNet rez = new NeuroNet();
        rez.GenerateAddLayer(54);
        rez.GenerateAddLayer(54);
        rez.GenerateAddLayer(54);
        rez.GenerateAddLayer(1);
        rez.Compile();
        return rez;
    }

}

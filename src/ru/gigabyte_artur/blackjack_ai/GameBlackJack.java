package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Hand;

public class GameBlackJack
{
    // Играет текущую игру. Возвращает номер победителя.
    public int Play()
    {
        int rez = -1;
        Hand deck = new Hand();
        NeuroNet model;
        NeuroNet player_net;
        deck.InitDeck();
        deck.Shuffle();
        model = GameBlackJack.GenerateModel();
        Player player1 = new Player();
        player1.NewGame(model);
        player_net = player1.GetNeuroNet();
        player_net.RandomWeights();
        player1.Play(deck);
        return rez;
    }

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        NeuroNet rez = new NeuroNet();
        rez.GenerateAddLayer(54, true, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(54, false, false);
        rez.GenerateAddLayer(1, false, true);
        rez.Compile();
        return rez;
    }

}

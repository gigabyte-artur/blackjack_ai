package ru.gigabyte_artur.blackjack_ai.gaming;

import ru.gigabyte_artur.blackjack_ai.black_jack.Hand;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public class Player
{
//    private Hand hand = new Hand();
    private NeuroNet neuro_net = new NeuroNet();

    // Конструктор нового.
    public Player()
    {

    }

    // Конструктор копирования.
    public Player(Player player_in)
    {

    }

    public Player(NeuroNet neuro_net_in)
    {
        this.SetNeuroNetCopyFrom(neuro_net_in);
    }

    public Player copy()
    {
        return new Player(this);
    }

    // Запускает новую игру.
    public void NewGame(NeuroNet neuro_net_in)
    {
//        this.hand.Empty();
        // TODO: Написать контруктор копирования.
//        this.neuro_net.CopyModel(neuro_net_in);
        this.neuro_net = neuro_net_in;
    }

    // Решает, нужно ли взять карту.
    public boolean Decide()
    {
        boolean rez = false;
        double output_signal;
        neuro_net.CalcSignals();
        output_signal = neuro_net.GetOutputSignal();
        rez = (output_signal > 0.0);
        return rez;
    }

    // Возвращает нейронную сеть текущего игрока.
    public NeuroNet GetNeuroNet()
    {
        return neuro_net;
    }


    // Играет текущим игроком для колоды deck_chng. Возвращает итоговую сумму очков.
    public int Play(Hand deck_chng, boolean show_hand_in)
    {
        int rez;
        rez = 0;
        return rez;
    }

    // Инициализирует текущего игрока случайными весами.
    public void RandomPlayer(NeuroNet model_in)
    {
        NeuroNet player_net;
        this.NewGame(model_in);
        player_net = this.GetNeuroNet();
        player_net.RandomWeights();
    }

    // Устанавливает текущему игроку нейросеть neuro_net_in.
    public void SetNeuroNet(NeuroNet neuro_net_in)
    {
        this.neuro_net = neuro_net_in;
    }

    // Устанавливает в текущую нейронную сеть копию нейросети neuro_net_in.
    public void SetNeuroNetCopyFrom(NeuroNet neuro_net_in)
    {
        NeuroNet new_neuro_net = new NeuroNet(neuro_net_in);
        this.SetNeuroNet(new_neuro_net);
    }
}


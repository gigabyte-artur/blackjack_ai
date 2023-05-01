package ru.gigabyte_artur.blackjack_ai.black_jack;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.gaming.TwoPlayersGame;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public class BlackJackPlayer extends Player {
    private Hand hand = new Hand();

    // Основной конструктор.
    public BlackJackPlayer()
    {
        NeuroNet neuronet1 = GameBlackJack.GenerateModel();
        this.SetNeuroNet(neuronet1);
    }

    // Контруктор на основании родителя.
    public BlackJackPlayer(Player player_in)
    {
        this.SetNeuroNetCopyFrom(player_in.GetNeuroNet());
        this.hand.Empty();
    }

    // Конструктор копирования.
    public BlackJackPlayer(BlackJackPlayer player_in)
    {
        NeuroNet new_neuro_net = player_in.GetNeuroNet().copy();
        this.SetNeuroNet(new_neuro_net);
        this.hand.Empty();
    }

    @Override
    public BlackJackPlayer copy() {
        return new BlackJackPlayer(this);
    }

    // Решает, нужно ли взять карту.
    public boolean Decide()
    {
        boolean rez = false;
        double output_signal;
        this.neuro_net.CalcSignals();
        output_signal = this.neuro_net.GetOutputSignal();
        rez = (output_signal > 0.0);
        return rez;
    }

    public void NewGame(NeuroNet neuro_net_in)
    {
        this.hand.Empty();
        // TODO: Написать контруктор копирования.
//        this.neuro_net.CopyModel(neuro_net_in);
//        this.neuro_net = neuro_net_in;
        super.NewGame(neuro_net_in);
    }

    // Переосит руку в сигналы входных нейронов.
    public void HandToInputSignals()
    {
        int hand_size, points_multiplication, curr_suite, curr_value;
        NeuroNet net1;
        Card card1 = new Card();
        hand_size = this.hand.Size();
        for (int i = 0; i < hand_size; i++)
        {
            card1 = hand.GetCardById(i);
            curr_suite = card1.GetSuite();
            curr_value = card1.GetValue();
            points_multiplication = (curr_suite - 1)*Card.MAX_VALUE + curr_value;
            net1 = super.GetNeuroNet();
            net1.SetInputSignal(points_multiplication, 1);
            super.SetNeuroNet(net1);
        }
    }

    // Играет текущим игроком для колоды deck_chng. Возвращает итоговую сумму очков.
    @Override
    public int Play(TwoPlayersGame game_in, boolean silent_in)
    {
        int rez;
        boolean decision;
        decision = true;
        rez = 0;
        this.hand.Empty();
        while ((decision) && (rez < 21))
        {
            if (game_in instanceof GameBlackJack)
                this.DrawCard(((GameBlackJack) game_in).deck);
            this.HandToInputSignals();
            decision = false;
            if (this.SummHand() > 11)
            {
                decision = this.Decide();
            }
            else
            {
                decision = true;
            }
            rez = this.SummHand();
            if (!silent_in)
            {
                this.ShowHand();
                System.out.println(rez);
            }
            else
            {
                // Не выводим руку.
            }
        }
        return rez;
    }

    // Возвращает сумму карт в руке.
    public int SummHand()
    {
        int rez = 0;
        rez = this.hand.SummHand();
        return rez;
    }

    // Тянет верхнюю карту из колоды hand_chng в руку текущего игрока.
    public void DrawCard(Hand hand_chng)
    {
        this.hand.DrawCard(hand_chng);
    }

    // Отображает руку текущего игрока на экране.
    public void ShowHand()
    {
        this.hand.Show();
    }


}

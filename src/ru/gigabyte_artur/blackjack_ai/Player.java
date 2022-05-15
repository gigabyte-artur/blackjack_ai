package ru.gigabyte_artur.blackjack_ai;

public class Player
{
    private Hand hand = new Hand();
    private NeuroNet neuro_net = new NeuroNet();

    // Запускает новую игру.
    public void NewGame(NeuroNet neuro_net_in)
    {
        this.hand.Empty();
        // TODO: Написать контруктор копирования.
//        this.neuro_net.CopyModel(neuro_net_in);
        this.neuro_net = neuro_net_in;
    }

    // Переосит руку в сигналы входных нейронов.
    public void HandToInputSignals()
    {
        int hand_size, points_multiplication, curr_suite, curr_value;
        Card card1 = new Card();
        hand_size = this.hand.Size();
        for (int i = 0; i < hand_size; i++)
        {
            card1 = hand.GetCardById(i);
            curr_suite = card1.GetSuite();
            curr_value = card1.GetValue();
            points_multiplication = (curr_suite - 1)*Card.MAX_VALUE + curr_value;
            this.neuro_net.SetInputSignal(points_multiplication, 1);
        }
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

    // Возвращает сумму карт в руке.
    public int SummHand()
    {
        int rez = 0;
        rez = this.hand.SummHand();
        return rez;
    }

    // Играет текущим игроком для колоды deck_chng. Возвращает итоговую сумму очков.
    public int Play(Hand deck_chng, boolean show_hand_in)
    {
        int rez;
        boolean decision;
        decision = true;
        rez = 0;
        this.hand.Empty();
        while ((decision) && (rez < 21))
        {
            this.DrawCard(deck_chng);
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
            if (show_hand_in)
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
}


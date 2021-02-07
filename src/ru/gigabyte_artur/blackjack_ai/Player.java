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

    // Переосит руку в сигналы входных нейронов.
    public void HandToInputSignals()
    {
        int hand_size;
        hand_size = this.hand.Size();
        for (int i = 0; i < hand_size; i++)
        {
            hand.GetCardById(i);
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
}


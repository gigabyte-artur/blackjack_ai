package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.card_game.Card;
import ru.gigabyte_artur.blackjack_ai.card_game.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public class PokerPlayer extends Player
{

    private Hand hand;          // Текущая рука игрока.
    private int amount;         // Сумма на счёте.

    public PokerPlayer()
    {
        // Инициализация руки и начального депозита.
        this.hand = new Hand();
        this.amount = 0;
        // Добавление нейронной сети
        NeuroNet neuronet1 = GamePoker.GenerateModel();
        this.SetNeuroNet(neuronet1);
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public Hand getHand()
    {
        Hand rez = this.hand;
        return rez;
    }

    // Переосит руку в сигналы входных нейронов.
    private void HandToInputSignals()
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

    // Переосит руку в сигналы входных нейронов.
    private void DeckToInputSignals(Hand deck_in)
    {
        int hand_size, points_multiplication, curr_suite, curr_value;
        final int SIGNALS_SHIFT = 54;
        NeuroNet net1;
        Card card1 = new Card();
        hand_size = deck_in.Size();
        for (int i = 0; i < hand_size; i++)
        {
            card1 = deck_in.GetCardById(i);
            curr_suite = card1.GetSuite();
            curr_value = card1.GetValue();
            points_multiplication = (curr_suite - 1)*Card.MAX_VALUE + curr_value + SIGNALS_SHIFT;
            net1 = super.GetNeuroNet();
            net1.SetInputSignal(points_multiplication, 1);
            super.SetNeuroNet(net1);
        }
    }

    // Считывает данные из текущей игры во входные сигналы нейронной сети.
    public void ReadInputSignals(GamePoker game_in)
    {
        HandToInputSignals();
        DeckToInputSignals(game_in.getTable());
    }
}

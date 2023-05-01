package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.black_jack.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;

public class PokerPlayer extends Player
{

    private Hand hand;          // Текущая рука игрока.
    private int amount;         // Сумма на счёте.

    public PokerPlayer()
    {
        this.hand = new Hand();
        this.amount = 0;
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
}

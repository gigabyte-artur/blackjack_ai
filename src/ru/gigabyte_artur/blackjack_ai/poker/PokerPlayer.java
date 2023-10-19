package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.card_game.Card;
import ru.gigabyte_artur.blackjack_ai.card_game.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.MultiPlayerGame;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;
import java.util.ArrayList;

public class PokerPlayer extends Player
{
    private Hand hand;          // Текущая рука игрока.
    private int amount;         // Сумма на счёте.

    public static final int Decision_None = 0;
    public static final int Decision_Blind = 1;
    public static final int Decision_Fold = 2;
    public static final int Decision_Check = 3;
    public static final int Decision_LittleRaise = 4;
    public static final int Decision_MidRaise = 5;
    public static final int Decision_BigRaise = 6;

    public PokerPlayer()
    {
        // Инициализация руки и начального депозита.
        this.hand = new Hand();
        this.amount = 0;
        // Добавление нейронной сети
        NeuroNet neuronet1 = GamePoker.GenerateModel();
        this.SetNeuroNet(neuronet1);
        neuronet1.RandomWeights();
    }

    public PokerPlayer(Player player_in)
    {
        this.hand = new Hand();
        this.amount = 0;
        this.SetNeuroNetCopyFrom(player_in.GetNeuroNet());
        this.hand.Empty();
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getAmount()
    {
        return amount;
    }

    public Hand getHand()
    {
        Hand rez = this.hand;
        return rez;
    }

    @Override
    public PokerPlayer copy()
    {
        return new PokerPlayer(this);
    }

    // Переносит руку в сигналы входных нейронов.
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
        Card card1;
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

        // TODO: Реализовать считывание прочих сигналов.
    }

    // Осуществляет принятие решениея на основе нейронной сети.
    public int Decide(GamePoker game_in)
    {
        // Инициализация.
        int rez = Decision_None, SelectedSignal;
        ArrayList<Integer> AllowedSignals = new ArrayList<Integer>();
        // Разрешенные решения.
        AllowedSignals.add(0);
        AllowedSignals.add(1);
        AllowedSignals.add(2);
        AllowedSignals.add(3);
        AllowedSignals.add(4);
        // Отберём запрещённые решения.
        AllowedSignals = FilterAllowedSignalsByGame(AllowedSignals, game_in);
        // Вычисление нейросети.
        SelectedSignal = neuro_net.CalcSignalsAndGetAllowedOutput(AllowedSignals);
        // Обход результатов вычисления.
        switch (SelectedSignal)
        {
            case (0): rez = Decision_Fold; break;
            case (1): rez = Decision_Check; break;
            case (2): rez = Decision_LittleRaise; break;
            case (3): rez = Decision_MidRaise; break;
            case (4): rez = Decision_BigRaise; break;
        }
        return rez;
    }

    // Отбирает в массиве AllowedSignals_in доступные решения, исходя из текущего состояния State_in.
    private ArrayList<Integer> FilterAllowedSignalsByCurrentState(ArrayList<Integer> AllowedSignals_in, int State_in)
    {
        ArrayList<Integer> rez = new ArrayList<>();
        boolean CanContinue = true;
        CanContinue = (State_in != Decision_Fold);
        if (!CanContinue)
        {
            // Нельзя продолжать. Доступно только решение Fold.
            rez.clear();
            rez.add(Decision_Fold);
        }
        else
        {
            // Можно продолжить. Доступны все решения.
            rez.addAll(AllowedSignals_in);
        }
        return rez;
    }

    // Удаляет из массива решений AllowedSignals_chng решение Decision_in, если новая ставка NewBid_in меньше текущей
    // ставки Decision_in.
    private void RemoveDecisionIfNotEnoughBid(int NewBid_in, int CurrentBid_in, int Decision_in, ArrayList<Integer> AllowedSignals_chng)
    {
        if (NewBid_in  < CurrentBid_in)
        {
            AllowedSignals_chng.remove((Integer) Decision_in);       // Запрещено оставлять ставку.
        }
        else
        {
            // Не требуется удаление.
        }
    }

    // Удаляет из массива решений AllowedSignals_chng решение Decision_in, если новая ставка NewBid_in больше депозита
    // Amount_in.
    private void RemoveDecisionIfNotEnoughAmount(int NewBid_in, int Amount_in, int Decision_in, ArrayList<Integer> AllowedSignals_chng)
    {
        if (NewBid_in > Amount_in)
        {
            AllowedSignals_chng.remove((Integer) Decision_in);
        }
        else
        {
            // Не требуется удаление.
        }
    }

    // Очищает из доступрных сигналов AllowedSignals_in те сигналы, которые не удовлетворяют текущей ставке, согласно
    // данным игры game_in.
    private ArrayList<Integer> FilterAllowedSignalsByBid(ArrayList<Integer> AllowedSignals_in, GamePoker game_in)
    {
        // Инициализация.
        ArrayList<Integer> rez = new ArrayList<>(AllowedSignals_in);
        int PlayerAmmount, CurrentBid, PlayerBid;
        int LittleRaiseVolume_loc = GamePoker.LittleRaiseVolume();
        int MidRaiseVolume_loc = GamePoker.MidRaiseVolume();
        int BigRaiseVolume_loc = GamePoker.BigRaiseVolume();
        // Проверка, что ставка игрока не меньше текущей ставки.
        CurrentBid = game_in.getCurrentBid();
        PlayerBid = game_in.getBids().getBidOfPlayer(this);
        if (PlayerBid < CurrentBid)
        {
            rez.remove((Integer) Decision_Check);       // Запрещено оставлять ставку.
            // Удаление решения по величине текущей ставки.
            RemoveDecisionIfNotEnoughBid(PlayerBid + LittleRaiseVolume_loc, CurrentBid, Decision_LittleRaise, rez);
            RemoveDecisionIfNotEnoughBid(PlayerBid + MidRaiseVolume_loc, CurrentBid, Decision_MidRaise, rez);
            RemoveDecisionIfNotEnoughBid(PlayerBid + BigRaiseVolume_loc, CurrentBid, Decision_BigRaise, rez);
            // Удаление решения по величине депозита.
            PlayerAmmount = this.getAmount();
            RemoveDecisionIfNotEnoughAmount(PlayerBid, PlayerAmmount, Decision_Check, rez);
            RemoveDecisionIfNotEnoughAmount(PlayerBid + LittleRaiseVolume_loc, PlayerAmmount, Decision_LittleRaise, rez);
            RemoveDecisionIfNotEnoughAmount(PlayerBid + MidRaiseVolume_loc, PlayerAmmount, Decision_MidRaise, rez);
            RemoveDecisionIfNotEnoughAmount(PlayerBid + BigRaiseVolume_loc, PlayerAmmount, Decision_BigRaise, rez);
        }
        else
        {
            // Ставка удовлетворяет. Доступны все решения.
        }
        return rez;
    }

    // Отбирает доступные решения AllowedSignals_in согласованно игре game_in.
    private ArrayList<Integer> FilterAllowedSignalsByGame(ArrayList<Integer> AllowedSignals_in, GamePoker game_in)
    {
        //Инициализация.
        ArrayList<Integer> rez = new ArrayList<>(AllowedSignals_in);
        int CurrentState;
        // Отбор по текущему состоянию.
        CurrentState = game_in.GetPlayersState(this);
        rez = FilterAllowedSignalsByCurrentState(rez, CurrentState);
        // Отбор по ставке.
        rez = FilterAllowedSignalsByBid(rez, game_in);
        return rez;
    }

    // Текстовое представление текущего состояния игрока.
    public String PlayerStateToString(MultiPlayerGame game_in)
    {
        String rez = "";
        switch (game_in.GetPlayersState(this))
        {
            case Decision_None:
                rez = "None";
                break;
            case Decision_Blind:
                rez = "Blind";
                break;
            case Decision_Fold:
                rez = "Fold";
                break;
            case Decision_Check:
                rez = "Check";
                break;
            case Decision_LittleRaise:
                rez = "LittleRaise";
                break;
            case Decision_MidRaise:
                rez = "MidRaise";
                break;
            case Decision_BigRaise:
                rez = "BigRaise";
                break;
        }
        return rez;
    }

    // Возвращает комбинацию текущего игрока вместе с картами на столе table_in.
    public PokerCombination CombinationWithTable(Hand table_in)
    {
        PokerCombination rez;
        Hand HandCurrPlayer = this.getHand();
        ArrayList<Card> CardMixed = Hand.MixTwoHand(table_in, HandCurrPlayer);
        rez = new PokerCombination(CardMixed);
        return rez;
    }
}

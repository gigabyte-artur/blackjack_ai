package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.black_jack.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.MultiPlayerGame;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public class GamePoker extends MultiPlayerGame
{

    private Hand deck;                       // Колода текущей игры.
    private Hand table;                      // Карты, лежащие на столе.
    private Player DealerPlayer;             // Текущий игрок-дилер.
    private Player LittleBlindPlayer;        // Игрок с малым бландом.
    private Player BigBlindPlayer;           // Игрок с большим блайндом.
    private int CurrBet;                     // Размер текущей ставки.
    private int GameState;                   // Текущее состояние игры.

    public static final int GameState_Preflop = 0;    // Состояние текущей игры Префлоп.
    public static final int GameState_Flop = 1;       // Состояние текущей игры Флоп.
    public static final int GameState_Turn = 2;       // Состояние текущей игры Тёрн.
    public static final int GameState_River = 3;      // Состояние текущей игры Ривер.
    public static final int MAX_PLAYERS = 6;           // Количество игроков за столом.
    public static final int NEW_AMOUNT = 10000;        // Размер банка у нового игрока.

    // Считывает входные сигналы всех игроков.
    private void PlayersReadInputSignals()
    {
        for (Player curr_Players:this.getPlayers())
        {
            if (curr_Players instanceof PokerPlayer)
                ((PokerPlayer)curr_Players).ReadInputSignals(this);
        }
    }

    // Раздаёт карты в текущей игре.
    private void DealCards(Hand deck_in)
    {
        // Раздача карт.
        for (Player curr_Players:this.getPlayers())
        {
            if (curr_Players instanceof PokerPlayer) {
                ((PokerPlayer)curr_Players).getHand().DrawCard(deck_in);
                ((PokerPlayer)curr_Players).getHand().DrawCard(deck_in);        // Вытянем две карты.
            }
        }
        // Установка дилера, малого, большого блайнда.
        if (this.getPlayers().size() > 2)
        {
            DealerPlayer         = this.getPlayers().get(0);
            LittleBlindPlayer    = this.getPlayers().get(1);
            BigBlindPlayer       = this.getPlayers().get(2);
        }
    }

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Play()
    {
        int rez = 0;
        DealCards(this.deck);
        PlayersReadInputSignals();
        return rez;
    }

    // Генерирует пустую колоду в текущую руку.
    private Hand GenerateDeck()
    {
        Hand rez = new Hand();
        rez.InitDeck();
        rez.Shuffle();
        return rez;
    }

    // Инициализирует игру.
    public void Init()
    {
        // Инициализация стола.
        this.table = new Hand();
        this.table.Empty();
        // Колода для игры.
        deck = GenerateDeck();
        this.setDeck(deck);
        // Добавление игроков.
        for (int c=1; c<=MAX_PLAYERS; c++)
        {
            PokerPlayer Player_new = NewPlayer();
            this.AddPlayer(Player_new);
        }
        // Установка состояния игры.
        this.setGameState(GameState_Preflop);
        // Инициализация ставки.
        this.setCurrBet(0);
    }

    // Генерирует игрока текущей игры.
    public PokerPlayer NewPlayer()
    {
        PokerPlayer rez = new PokerPlayer();
        rez.setAmount(NEW_AMOUNT);
        return rez;
    }

    public void setDeck(Hand deck)
    {
        this.deck = deck;
    }

    private void setGameState(int GameState_in)
    {
        this.GameState = GameState_in;
    }

    private void setCurrBet(int currBet_in)
    {
        this.CurrBet = currBet_in;
    }

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        // ВХОДНЫЕ НЕЙРОНЫ:
        // 1-54      (54) - есть ли карта с номером в руке у игрока.
        // 55-110    (54) - есть ли карта с номером на столе.
        // 111-116   (6) - спасовал ли игрок 1-6.
        // 117-122   (6) - продолжил ли игрок 1-6.
        // 123-128   (6) - поднял ли ставку *1 игрок 1-6.
        // 129-134   (6) - поднял ли ставку *3 игрок 1-6.
        // 135-140   (6) - поднял ли ставку *10 игрок 1-6.
        // 141-146   (6) - моя позиция относительно дилера.
        // 147       (1) - размер моего депозита (в процентах от 0 до 3 начальных).
        //
        // ВЫХОДНЫЕ НЕЙРОНЫ:
        // 1 - скинуть карты.
        // 2 - продолжить.
        // 3 - поднять *1.
        // 4 - поднять *3.
        // 5 - поднять *10.
        NeuroNet rez = new NeuroNet();
        rez.GenerateAddDenseLayer(147, true, false);
        rez.GenerateAddDenseLayer(147, false, false);
        rez.GenerateAddDenseLayer(147, false, false);
        rez.GenerateAddDenseLayer(147, false, false);
        rez.GenerateAddDenseLayer(5, false, true);
        rez.Compile();
        return rez;
    }

    public Hand getDeck()
    {
        return deck;
    }

    public Hand getTable()
    {
        return table;
    }
}

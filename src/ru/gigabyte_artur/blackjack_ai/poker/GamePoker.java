package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.card_game.Card;
import ru.gigabyte_artur.blackjack_ai.card_game.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.BidsOfPlayers;
import ru.gigabyte_artur.blackjack_ai.gaming.MultiPlayerGame;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;
import java.util.ArrayList;

public class GamePoker extends MultiPlayerGame
{
    private Hand deck;                       // Колода текущей игры.
    private Hand table;                      // Карты, лежащие на столе.
    private Player DealerPlayer;             // Текущий игрок-дилер.
    private Player LittleBlindPlayer;        // Игрок с малым бландом.
    private Player BigBlindPlayer;           // Игрок с большим блайндом.
    private int CurrentBid;                  // Размер текущей ставки.
    private int GameState;                   // Текущее состояние игры.
    private BidsOfPlayers Bids;              // Ставки игроков.
    private Player LastRaisedPlayer;

    public static final int GameState_Preflop = 0;    // Состояние текущей игры Префлоп.
    public static final int GameState_Flop = 1;       // Состояние текущей игры Флоп.
    public static final int GameState_Turn = 2;       // Состояние текущей игры Тёрн.
    public static final int GameState_River = 3;      // Состояние текущей игры Ривер.
    public static final int PokerPlayerState_Fold = 1;          // Состояние игрока "сбросил".
    public static final int PokerPlayerState_binding = 2;       // Состояние игрока "обязательная ставка".
    public static final int PokerPlayerState_Check = 3;         // Состояние игрока "проверил".
    public static final int PokerPlayerState_RaiseLess = 4;     // Состояние игрока "поднял немного".
    public static final int PokerPlayerState_RaiseNormal = 5;   // Состояние игрока "поднял средне".
    public static final int PokerPlayerState_RaiseBig = 6;      // Состояние игрока "поднял много".
    public static final int MAX_PLAYERS = 6;           // Количество игроков за столом.
    public static final int NEW_AMOUNT = 10000;        // Размер банка у нового игрока.
    public static final int MINIMAL_BID = 5;

    public void setCurrentBid(int currentBid_in)
    {
        this.CurrentBid = currentBid_in;
    }

    private void setDealerPlayer(Player Player_in)
    {
        this.DealerPlayer = Player_in;
    }

    public void setLittleBlindPlayer(Player littleBlindPlayer_in)
    {
        this.LittleBlindPlayer = littleBlindPlayer_in;
    }

    public void setBigBlindPlayer(Player bigBlindPlayer_in)
    {
        this.BigBlindPlayer = bigBlindPlayer_in;
    }

    public void setDeck(Hand deck)
    {
        this.deck = deck;
    }

    private void setGameState(int GameState_in)
    {
        this.GameState = GameState_in;
    }

    public void setLastRaisedPlayer(Player lastRaisedPlayer)
    {
        LastRaisedPlayer = lastRaisedPlayer;
    }

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

    // Стадия игры Префлоп. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Preflop()
    {
        int rez = -1;
        Player NewDealerPlayer, NewBigBlindPlayer, NewLittleBlindPlayer;
        Player NewBigBlind = new Player();
        if (getPlayers().size() > 1)
        {
            // Установка ставок.
            this.Bids = new BidsOfPlayers(this.getPlayers());
            // Раздача карт.
            DealCards(this.deck);
            // Игрок-дилер.
            NewDealerPlayer = getPlayerById(0);
            setDealerPlayer(NewDealerPlayer);
            // Малый блайнд.
            NewLittleBlindPlayer = getPlayerById(1);
            MakeLittleBlind(NewLittleBlindPlayer);
            // Большой блайнд.
            NewBigBlindPlayer = getPlayerById(2);
            MakeBigBlind(NewBigBlindPlayer);
//            PlayersReadInputSignals();
        }
        else
        {
            System.out.println("Недостаточно игроков для начала игры");
        }
        return rez;
    }

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Play()
    {
        int rez = -1;
//        this.table.DrawCard(this.deck);
//        this.table.DrawCard(this.deck);
//        this.table.DrawCard(this.deck);
//        this.table.DrawCard(this.deck);
//        this.table.DrawCard(this.deck);
        Preflop();
//        this.Show();
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
        this.setCurrentBid(0);
    }

    // Генерирует игрока текущей игры.
    public PokerPlayer NewPlayer()
    {
        PokerPlayer rez = new PokerPlayer();
        rez.setAmount(NEW_AMOUNT);
        return rez;
    }

    // Устанавливает в текущей игре ставку bid_in игроком player_in.
    private void MakeNewBid(Player player_in, int bid_in)
    {
        this.Bids.setBidOfPlayer(player_in, bid_in);
        this.setCurrentBid(bid_in);
    }

    // Делает ставку малого блайнда игроком player_in.
    private void MakeLittleBlind(Player player_in)
    {
        setLittleBlindPlayer(player_in);
        MakeNewBid(player_in, MINIMAL_BID);                 // Малый блайнд - 1 минимальная ставка.
        setLastRaisedPlayer(player_in);
    }

    // Делает ставку большого блайнда игроком player_in.
    private void MakeBigBlind(Player player_in)
    {
        setBigBlindPlayer(player_in);
        MakeNewBid(player_in, MINIMAL_BID * 2);                 // Большой блайнд - 2 минимальные ставки.
        setLastRaisedPlayer(player_in);
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

    // Отображает текущее состояние игры в консоли.
    public void Show()
    {
        // Инициализация.
        int c = 0;
        ArrayList<Card> CardMixed = new ArrayList<Card>();
        Hand HandCurrPlayer;
        PokerCombination CombinationCurrPlayer = new PokerCombination();
        // Вывод карт на столе.
        System.out.println("На столе: ");
        this.table.Show();
        System.out.println("-----");
        // Вывод карт у игроков.
        for (Player curr_Players:this.getPlayers())
        {
            System.out.println("Карты игрока #" + (c + 1) + ":");
            if (curr_Players instanceof PokerPlayer)
            {
                HandCurrPlayer = ((PokerPlayer)curr_Players).getHand();
                HandCurrPlayer.Show();
                CardMixed = Hand.MixTwoHand(this.table, HandCurrPlayer);
                CombinationCurrPlayer = new PokerCombination(CardMixed);
                CombinationCurrPlayer.Show();
            }
            c = c + 1;
        }
    }
}

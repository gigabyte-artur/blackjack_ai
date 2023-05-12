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
    private Player LastPlayerCircle;         // Последний игрок, участовавший в торгах.
    private int CurrentBid;                  // Размер текущей ставки.
    private int GameState;                   // Текущее состояние игры.
    private BidsOfPlayers Bids;              // Ставки игроков.
    private Player LastRaisedPlayer;         // Игрок, в последний раз поднимавший ставки.

    public static final int GameState_Preflop = 0;    // Состояние текущей игры Префлоп.
    public static final int GameState_Flop = 1;       // Состояние текущей игры Флоп.
    public static final int GameState_Turn = 2;       // Состояние текущей игры Тёрн.
    public static final int GameState_River = 3;      // Состояние текущей игры Ривер.
    public static final int MAX_PLAYERS = 6;          // Количество игроков за столом.
    public static final int NEW_AMOUNT = 10000;       // Размер банка у нового игрока.
    public static final int MINIMAL_BID = 5;          // Размер минимальной ставки.

    public int getCurrentBid()
    {
        return CurrentBid;
    }

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

    public void setLastPlayerCircle(Player lastPlayerCircle)
    {
        LastPlayerCircle = lastPlayerCircle;
    }

    public Player getLastPlayerCircle()
    {
        return LastPlayerCircle;
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
            MakeLittleBlind((PokerPlayer)NewLittleBlindPlayer);
            // Большой блайнд.
            NewBigBlindPlayer = getPlayerById(2);
            MakeBigBlind((PokerPlayer)NewBigBlindPlayer);
            // Круги торгов.
            TradesCircles();
        }
        else
        {
            System.out.println("Недостаточно игроков для начала игры");
        }
        return rez;
    }

    // Круги торгов.
    private void TradesCircles()
    {
        // Инициализация.
        boolean ContinueTrades = true;
        int Decision = 0;
        Player NextPlayer;
        while (ContinueTrades)
        {
            // Принятие решения следующим игроком.
            NextPlayer = this.GetNextPlayer(this.getLastPlayerCircle());
            if (this.GetPlayersState(NextPlayer) != PokerPlayer.Decision_Fold)
            {
                Decision = ((PokerPlayer) NextPlayer).Decide();
                switch (Decision) {
                    // TODO: Реализовать обработку принятых решений.
                    case PokerPlayer.Decision_None:
                        // Неизвестный вариант. Пропускаем.
                        break;
                    case PokerPlayer.Decision_Blind:
                        // Сделан блайнд. Пропускаем.
                        break;
                    case PokerPlayer.Decision_Fold:
                        // Игрок решил скинуть карты и прекратить игру.
                        SetPlayersState(NextPlayer, PokerPlayer.Decision_Fold);
                        break;
                    case PokerPlayer.Decision_Check:
                        // Игрок оставляет ставки как есть.
                        SetPlayersState(NextPlayer, PokerPlayer.Decision_Check);
                        break;
                    case PokerPlayer.Decision_LittleRaise:
                        // Игрок поднимает ставку на 1 минимальную ставки.
                        RaiseBid((PokerPlayer)NextPlayer, MINIMAL_BID);
                        SetPlayersState(NextPlayer, PokerPlayer.Decision_LittleRaise);
                        break;
                    case PokerPlayer.Decision_MidRaise:
                        // Игрок поднимает ставку на 3 минимальные ставки.
                        RaiseBid((PokerPlayer)NextPlayer, 3 * MINIMAL_BID);
                        SetPlayersState(NextPlayer, PokerPlayer.Decision_MidRaise);
                        break;
                    case PokerPlayer.Decision_BigRaise:
                        // Игрок поднимает ставку на 10 минимальные ставки.
                        RaiseBid((PokerPlayer)NextPlayer, 10 * MINIMAL_BID);
                        SetPlayersState(NextPlayer, PokerPlayer.Decision_BigRaise);
                        break;
                }
            }
            else
            {
                // Игрок скинул свои карты. Пропускаем.
            }
            this.setLastPlayerCircle(NextPlayer);
            // Проверка на продолжение.
            ContinueTrades = (NextPlayer != LastRaisedPlayer);
        }
    }

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Play()
    {
        int rez = -1;
        Preflop();
        // TODO: Реализовать прочие стадии.
        Show();
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
            this.SetPlayersState(Player_new, PokerPlayer.Decision_None);
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
    private void MakeNewBid(PokerPlayer player_in, int bid_in)
    {
        int new_amount;
        this.Bids.setBidOfPlayer(player_in, bid_in);
        this.setCurrentBid(bid_in);
        new_amount = player_in.getAmount() - bid_in;
        player_in.setAmount(new_amount);
    }

    // Делает ставку малого блайнда игроком player_in.
    private void MakeLittleBlind(PokerPlayer player_in)
    {
        setLittleBlindPlayer(player_in);
        MakeNewBid(player_in, MINIMAL_BID);                 // Малый блайнд - 1 минимальная ставка.
        setLastRaisedPlayer(player_in);
        setLastPlayerCircle(player_in);
        SetPlayersState(player_in, PokerPlayer.Decision_Blind);
    }

    // Делает ставку большого блайнда игроком player_in.
    private void MakeBigBlind(PokerPlayer player_in)
    {
        setBigBlindPlayer(player_in);
        MakeNewBid(player_in, MINIMAL_BID * 2);                 // Большой блайнд - 2 минимальные ставки.
        setLastRaisedPlayer(player_in);
        setLastPlayerCircle(player_in);
        SetPlayersState(player_in, PokerPlayer.Decision_Blind);
    }

    // Поднимает игроком player_in ставки на величину AdditionalBid_in.
    private void RaiseBid(PokerPlayer player_in, int AdditionalBid_in)
    {
       int curr_bid = this.getCurrentBid();
       int new_bid = curr_bid + AdditionalBid_in;
       MakeNewBid(player_in, new_bid);
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
        String PlayerText;
        ArrayList<Card> CardMixed = new ArrayList<Card>();
        Hand HandCurrPlayer;
        PokerCombination CombinationCurrPlayer = new PokerCombination();
        // Вывод карт на столе.
        System.out.println("(" + this.getCurrentBid()+ ") " + "На столе: ");
        this.table.Show();
        System.out.println("-----");
        // Вывод карт у игроков.
        for (Player curr_Players:this.getPlayers())
        {
            PlayerText = "";
            PlayerText = PlayerText + "(";
            PlayerText = PlayerText + Bids.getBidOfPlayer(curr_Players);
            PlayerText = PlayerText + "/";
            PlayerText = PlayerText + ((PokerPlayer)curr_Players).getAmount();
            PlayerText = PlayerText + ") Карты игрока #";
            PlayerText = PlayerText + (c + 1);
            PlayerText = PlayerText + " [";
            PlayerText = PlayerText + ((PokerPlayer) curr_Players).PlayerStateToString(this);
            PlayerText = PlayerText + "]:";
            System.out.println(PlayerText);
            if (curr_Players instanceof PokerPlayer)
            {
                HandCurrPlayer = ((PokerPlayer)curr_Players).getHand();
                System.out.print("   ");
                HandCurrPlayer.Show();
                CardMixed = Hand.MixTwoHand(this.table, HandCurrPlayer);
                CombinationCurrPlayer = new PokerCombination(CardMixed);
                System.out.print("   ");
                CombinationCurrPlayer.Show();
            }
            c = c + 1;
        }
    }
}

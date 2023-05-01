package ru.gigabyte_artur.blackjack_ai.poker;

import ru.gigabyte_artur.blackjack_ai.black_jack.Hand;
import ru.gigabyte_artur.blackjack_ai.gaming.MultiPlayerGame;
import ru.gigabyte_artur.blackjack_ai.gaming.Player;

public class GamePoker extends MultiPlayerGame
{

    private Hand deck;                       // Колода текущей игры.
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

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public int Play()
    {
        int rez = 0;

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
        // Колода для игры.
        deck = GenerateDeck();
        this.setDeck(deck);
        // Добавление игроков.
        for (int c=1; c<=MAX_PLAYERS; c++)
        {
            PokerPlayer Player_new = NewPlayer();
            Player_new.getHand().DrawCard(deck);
            Player_new.getHand().DrawCard(deck);        // Вытянем две карты.
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
}

package ru.gigabyte_artur.blackjack_ai.gaming;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public abstract class TwoPlayersGame
{
    public Player player1;      // Первый игрок.
    public Player player2;      // Второй игрок.

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public abstract  int Play();
    // Инициализирует игру.
    public abstract void Init();
    // Генерирует игрока текущей игры.
    public abstract Player NewPlayer();

    // Возвращает пустую нейронную сеть для новой игры.
    public static NeuroNet GenerateModel()
    {
        return new NeuroNet();
    }

    // Устанавливает игроков player1_in и player2_in.
    public void SetPlayers(Player player1_in, Player player2_in)
    {
        this.player1 = player1_in;
        this.player2 = player2_in;
    }

    // Играет игру игрока player_in с пользователем (в консольном режиме). Если победил пользователь - возвращает 1,
    //  если победил игрок - возвращает 2. В случае ничьи возвращает -1.
    public int PlayWithUser(Player player_in)
    {
        int rez = -1;
        return rez;
    }

    // Возвращает игрока 1.
    public Player getPlayer1()
    {
        return this.player1;
    }

    // Возвращает игрока 2.
    public Player getPlayer2()
    {
        return this.player2;
    }
}

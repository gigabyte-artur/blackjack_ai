package ru.gigabyte_artur.blackjack_ai;

import java.util.Scanner;

public abstract class TwoPlayersGame
{
    Player player1;
    Player player2;

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public abstract  int Play();
    public abstract void Init();

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

}

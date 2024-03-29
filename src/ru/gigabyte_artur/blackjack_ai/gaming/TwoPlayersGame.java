package ru.gigabyte_artur.blackjack_ai.gaming;

import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

public abstract class TwoPlayersGame extends MultiPlayerGame
{

    public TwoPlayersGame()
    {
        this.SetEmptyPlayers(2);
    }

    // Устанавливает игроков player1_in и player2_in.
    public void SetPlayers(Player player1_in, Player player2_in)
    {
        this.setPlayerById(0, player1_in);
        this.setPlayerById(1, player2_in);
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
        return this.getPlayerById(0);
    }

    // Возвращает игрока 2.
    public Player getPlayer2()
    {
        return this.getPlayerById(1);
    }

    @Override
    public int GetStandardNumberOfPlayers()
    {
        int rez = 2;        // Всегда два игрока.
        return rez;
    }
}

package ru.gigabyte_artur.blackjack_ai.gaming;

import java.util.ArrayList;

public abstract class MultiPlayerGame
{
    private ArrayList<Player> Players = new ArrayList<>();

    // Играет текущую игру. Возвращает номер победителя. В случае ничьи возвращает -1.
    public abstract  int Play();
    // Инициализирует игру.
    public abstract void Init();
    // Генерирует игрока текущей игры.
    public abstract Player NewPlayer();

    // Очищает и устанавливает в массив игроков пустых игроков в количестве num_players_in.
    public void SetEmptyPlayers(int num_players_in)
    {
        this.Players.clear();
        for (int c = 0; c < num_players_in; c++)
        {
            Player EmptyPlayer = new Player();
            this.Players.add(EmptyPlayer);
        }
    }

    // Возвращает игрока по его номеру.
    public Player getPlayerById(int id_player_in)
    {
        Player rez = new Player();
        if (this.Players.size() >= id_player_in)
        {
            rez = this.Players.get(id_player_in);
        }
        else
        {
            System.out.println("Невозможно получить игрока номер " + id_player_in + ". В игре недостаточно игроков.");
        }
        return rez;
    }

    // Устанавливает игрока Player_in в массив игроков под номером id_player_in.
    public void setPlayerById(int id_player_in, Player Player_in)
    {
        if (this.Players.size() >= id_player_in)
        {
            this.Players.set(id_player_in, Player_in);
        }
        else
        {
            System.out.println("Невозможно уставновить игрока номер " + id_player_in + ". В игре недостаточно игроков.");
        }
    }
}

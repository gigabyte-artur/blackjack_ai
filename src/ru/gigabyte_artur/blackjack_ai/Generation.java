package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;

public class Generation
{
    private ArrayList<Player> Players = new ArrayList<>();

    // Добавляет игрока player_in в список игроков.
    public void AddPlayer(Player player_in)
    {
        this.Players.add(player_in);
    }

    // Инициализирует поколение случайными игроками.
    public void InitRandom(int generation_size_in)
    {
        Player added_player;
        NeuroNet model;
        model = GameBlackJack.GenerateModel();
        this.Players.clear();
        for (int i = 0; i < generation_size_in; i++)
        {
            added_player = new Player();
            added_player.RandomPlayer(model);
        }
    }
}

package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Generation
{
    private ArrayList<Player> Players = new ArrayList<>();
    ScoreGen scores = new ScoreGen();

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
            this.Players.add(added_player);
        }
    }

    // Разыгрывает игры между игроками. Помещает результаты в хранилище результатов.
    public void Play()
    {
        GameBlackJack game1;
        int winner_id;
        for (Player player1: this.Players)
        {
            for (Player player2: this.Players)
            {
                if (!player1.equals(player2))
                {
                    game1 = new GameBlackJack();
                    game1.SetPlayers(player1, player2);
                    winner_id = game1.Play();
                    if (winner_id == 1)
                    {
                        this.scores.IncreaseScorePlayer(player1, 1);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                    else if (winner_id == 2)
                    {
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 1);
                    }
                    else
                    {
                        // Ничья. Не изменяем результат.
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                }
            }
        }
    }

    // Отображает результаты игр текущего поколения.
    public void ShowScores()
    {
        this.scores.ShowScores();
    }

    // Вовзращает игроков с результатами от min_in до max_in места.
    public ArrayList<Player> GetTopPlayer(int min_in, int max_in)
    {
        return this.scores.GetTopPlayer(min_in, max_in);
    }

}

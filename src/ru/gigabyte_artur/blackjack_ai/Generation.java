package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Generation
{
    private ArrayList<Player> Players = new ArrayList<>();
    Map<Player, Integer> scores = new HashMap<>();

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

    // Увеличивает в соответствии результатов результат игрока player_in на score_in.
    private void IncreaseScorePlayer(Player player_in, int score_in)
    {
        int old_score, new_score;
        if (this.scores.containsKey(player_in))
        {
            old_score = this.scores.get(player_in);
            new_score = old_score + score_in;
            this.scores.put(player_in, new_score);
        }
        else
        {
            this.scores.put(player_in, score_in);
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
                        this.IncreaseScorePlayer(player1, 1);
                        this.IncreaseScorePlayer(player2, 0);
                    }
                    else if (winner_id == 2)
                    {
                        this.IncreaseScorePlayer(player1, 0);
                        this.IncreaseScorePlayer(player2, 1);
                    }
                    else
                    {
                        // Ничья. Не изменяем результат.
                        this.IncreaseScorePlayer(player1, 0);
                        this.IncreaseScorePlayer(player2, 0);
                    }
                }
            }
        }
    }

    // Отображает результаты игр текущего поколения.
    public void ShowScores()
    {
        int i = 1;
        for (Map.Entry<Player, Integer> entry: this.scores.entrySet())
        {
            System.out.println(i + ": " + entry.getValue());
            i = i + 1;
        }
    }

}

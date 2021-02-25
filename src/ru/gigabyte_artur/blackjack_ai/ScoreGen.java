package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ScoreGen
{
    Map<Player, Integer> scores = new HashMap<>();

    // Увеличивает в соответствии результатов результат игрока player_in на score_in.
    public void IncreaseScorePlayer(Player player_in, int score_in)
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

    // Вовзращает игроков с результатами от min_in до max_in места.
    public ArrayList<Player> GetTopPlayer(int min_in, int max_in)
    {
        ArrayList<Player> temp_players = new ArrayList<>();
        ArrayList<Player> rez = new ArrayList<>();
        Player curr_player, player1, player2;
        boolean sorted;
        int score1, score2;
        for (Map.Entry<Player, Integer> pair : this.scores.entrySet())
        {
            curr_player = pair.getKey();
            temp_players.add(curr_player);
        }
        sorted = false;
        while (!sorted)
        {
            sorted = true;
            for (int i = 0; i < this.scores.size() - 1; i++)
            {
                player1 = temp_players.get(i);
                player2 = temp_players.get(i + 1);
                score1 = this.scores.get(player1);
                score2 = this.scores.get(player2);
                if (score1 < score2)
                {
                    temp_players.set(i, player2);
                    temp_players.set(i + 1, player1);
                    sorted = false;
                }
            }
        }
        for (int i = min_in + 1; i < max_in +1; i++)
        {
            curr_player = temp_players.get(i);
            rez.add(curr_player);
        }
        return rez;
    }
}

package ru.gigabyte_artur.blackjack_ai;

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
}

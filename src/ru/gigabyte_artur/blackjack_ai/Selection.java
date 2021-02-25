package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;

public class Selection
{
    private int unhangable_player_number;

    public Selection()
    {
        this.SetParameters(3);
    }

    // Выполняет селекцию поколения generation_in. Возвращает новое поколение.
    public Generation MakeSelection(Generation generation_in)
    {
        ArrayList<Player> array_unchangable;
        Generation rez = new Generation();
        array_unchangable = generation_in.GetTopPlayer(0, this.unhangable_player_number);
        for (Player curr_player: array_unchangable)
        {
            rez.AddPlayer(curr_player);
        }
        return rez;
    }

    // Устанавливает параметры текущей селекции, согласно переданным параметрам функции.
    public void SetParameters(int unhangable_player_number_in)
    {
        this.unhangable_player_number = unhangable_player_number_in;
    }
}

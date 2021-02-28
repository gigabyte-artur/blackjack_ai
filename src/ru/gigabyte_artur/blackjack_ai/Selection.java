package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;

public class Selection
{
    private int unhangable_player_number;
    private int probablity_mutation;

    public Selection()
    {
        this.SetParameters(3, 5);
    }

    // Выполняет селекцию поколения generation_in. Возвращает новое поколение.
    public Generation MakeSelection(Generation generation_in)
    {
        ArrayList<Player> array_unchangable;
        Generation rez = new Generation();
        NeuroNet curr_neuro_net;
        array_unchangable = generation_in.GetTopPlayer(0, this.unhangable_player_number);
        for (Player curr_player: array_unchangable)
        {
            rez.AddPlayer(curr_player);
            curr_neuro_net = curr_player.GetNeuroNet();
            curr_neuro_net.Mutate(this.probablity_mutation);
        }
        return rez;
    }

    // Устанавливает параметры текущей селекции, согласно переданным параметрам функции.
    public void SetParameters(int unhangable_player_number_in, int probablitity_mutation_in)
    {
        this.unhangable_player_number = unhangable_player_number_in;
        this.probablity_mutation = probablitity_mutation_in;
    }
}

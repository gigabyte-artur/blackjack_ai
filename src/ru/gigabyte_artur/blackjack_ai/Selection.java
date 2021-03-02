package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.Random;

public class Selection
{
    private int unсhangable_player_number;      // Число неизменяемых стратегий.
    private int probablity_mutation;            // Вероятность мутации.
    private int mutated_player_number;          // Число мутирующих стратегий.
    private int mutated_out_number;             // Число выходных после мутирования стратегий.
    private int probablity_hard_mutation;       // Вероятность сильной мутации.
    private int hard_mutated_player_number;     // Число сильно мутирующих стратегий.
    private int hard_mutated_out_number;        // Число выходных после сильного мутирования стратегий.

    // Осуществляет селекцию.
    public Selection()
    {
        this.SetParameters(3, 5, 20, 65,
                15, 20, 27);
    }

    // Выполняет селекцию поколения generation_in. Возвращает новое поколение.
    public Generation MakeSelection(Generation generation_in)
    {
        ArrayList<Player> array_unchangable;
        ArrayList<Player> array_mutable;
        Generation rez = new Generation();
        NeuroNet curr_neuro_net;
        int mutable_count, new_index;
        final Random random = new Random();
        Player mutate_player = new Player();
        // Неизменяемые стратегии.
        array_unchangable = generation_in.GetTopPlayer(0, this.unсhangable_player_number);
        for (Player curr_player: array_unchangable)
        {
            rez.AddPlayer(curr_player);
            curr_neuro_net = curr_player.GetNeuroNet();
        }
        // Мутирующие стратегии.
        array_mutable = generation_in.GetTopPlayer(0, this.mutated_player_number);
        mutable_count = array_mutable.size();
        for (int i = 0; i < this.mutated_out_number; i++)
        {
            new_index = random.nextInt(mutable_count);
            mutate_player = array_mutable.get(new_index);
            curr_neuro_net = mutate_player.GetNeuroNet();
            curr_neuro_net.Mutate(this.probablity_mutation);
            rez.AddPlayer(mutate_player);
        }
        // Сильно мутирующие стратегии.
        array_mutable = generation_in.GetTopPlayer(0, this.hard_mutated_player_number);
        mutable_count = array_mutable.size();
        for (int i = 0; i < this.hard_mutated_out_number; i++)
        {
            new_index = random.nextInt(mutable_count);
            mutate_player = array_mutable.get(new_index);
            curr_neuro_net = mutate_player.GetNeuroNet();
            curr_neuro_net.Mutate(this.probablity_hard_mutation);
            rez.AddPlayer(mutate_player);
        }
        return rez;
    }

    // Устанавливает параметры текущей селекции, согласно переданным параметрам функции.
    public void SetParameters(int unсhangable_player_number_in, int probablitity_mutation_in, int mutated_player_number_in,
        int mutated_out_number_in, int probablity_hard_mutation_in, int hard_mutated_player_number_in,
        int hard_mutated_out_number_in)
    {
        this.unсhangable_player_number   = unсhangable_player_number_in;
        this.probablity_mutation         = probablitity_mutation_in;
        this.mutated_player_number       = mutated_player_number_in;
        this.mutated_out_number          = mutated_out_number_in;
        this.probablity_hard_mutation    = probablity_hard_mutation_in;
        this.hard_mutated_player_number  = hard_mutated_player_number_in;
        this.hard_mutated_out_number     = hard_mutated_out_number_in;
    }
}

package ru.gigabyte_artur.blackjack_ai.genetic_algorithm;

import ru.gigabyte_artur.blackjack_ai.gaming.Player;
import ru.gigabyte_artur.blackjack_ai.gaming.TwoPlayersGame;
import ru.gigabyte_artur.blackjack_ai.neuro_net.NeuroNet;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Selection
{
    private int unchangable_player_number;      // Число неизменяемых стратегий.
    private int probablity_mutation;            // Вероятность мутации.
    private int mutated_player_number;          // Число мутирующих стратегий.
    private int mutated_out_number;             // Число выходных после мутирования стратегий.
    private int probablity_hard_mutation;       // Вероятность сильной мутации.
    private int hard_mutated_player_number;     // Число сильно мутирующих стратегий.
    private int hard_mutated_out_number;        // Число выходных после сильного мутирования стратегий.
    private int random_player_number;           // Число случайных стратегий

    // Осуществляет селекцию.
    public Selection()
    {
        this.SetParameters(3, 5, 20, 65,
                15, 20, 27, 5);
    }

    // Выполняет селекцию поколения generation_in. Возвращает новое поколение.
    public Generation MakeSelection(Generation generation_in, TwoPlayersGame game_in)
    {
        // Инициализация.
        Generation rez = new Generation();
        ArrayList<Player> array_mutable;
        int mutable_count;
        final Random random = new Random();
        // Инициализация многопоточности.
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Future<Player>> futures = new ArrayList<>(); // список для хранения результатов вычислений
        // Неизменяемые стратегии.
        ArrayList<Player> array_unchangable;
        array_unchangable = generation_in.GetTopPlayer(0, this.unchangable_player_number);
        for (Player curr_player: array_unchangable) {
            Future<Player> future = executor.submit(() -> {
                return curr_player;
            });
            futures.add(future);
        }
        // Мутирующие стратегии.
        array_mutable = generation_in.GetTopPlayer(0, this.mutated_player_number);
        for (int i = 0; i < this.mutated_out_number; i++)
        {
            final  ArrayList<Player> array_mutable_lamda = array_mutable;
            Future<Player> future = executor.submit(() -> {
                int mutable_count_lamda, new_index_lamda;
                NeuroNet curr_neuro_net_lamda;
                Player mutate_player_lamda = new Player();
                mutable_count_lamda = array_mutable_lamda.size();
                new_index_lamda = random.nextInt(mutable_count_lamda);
                mutate_player_lamda = array_mutable_lamda.get(new_index_lamda);
                curr_neuro_net_lamda = mutate_player_lamda.GetNeuroNet();
                curr_neuro_net_lamda.Mutate(this.probablity_mutation);
                return mutate_player_lamda;
            });
            futures.add(future);
        }
        // Сильно мутирующие стратегии.
        array_mutable = generation_in.GetTopPlayer(0, this.hard_mutated_player_number);
        mutable_count = array_mutable.size();
        for (int i = 0; i < this.hard_mutated_out_number; i++)
        {
            final  ArrayList<Player> array_mutable_lamda = array_mutable;
            Future<Player> future = executor.submit(() ->
            {
                int new_index_lamda;
                Player mutate_player_lamda = new Player();
                NeuroNet curr_neuro_net_lamda;
                new_index_lamda = random.nextInt(mutable_count);
                mutate_player_lamda = array_mutable_lamda.get(new_index_lamda);
                curr_neuro_net_lamda = mutate_player_lamda.GetNeuroNet();
                curr_neuro_net_lamda.Mutate(this.probablity_hard_mutation);
                return mutate_player_lamda;
            });
            futures.add(future);
        }
        // Новые стратегии.
        for (int i = 0; i < this.random_player_number; i++)
        {
            Future<Player> future = executor.submit(() ->
            {
                Player random_player_lamda = new Player();
                random_player_lamda = game_in.NewPlayer();
                NeuroNet players_neuronet = random_player_lamda.GetNeuroNet();
                players_neuronet.RandomWeights();
                rez.AddPlayer(random_player_lamda);
                return random_player_lamda;
            });
            futures.add(future);
        }
        // Ожидаем завершения всех задач.
        for (Future<Player> future : futures)
        {
            try
            {
                Player result = future.get(); // получаем результат вычислений
                rez.AddPlayer(result);
            } catch (InterruptedException | ExecutionException e)
            {
                e.printStackTrace();
            }
        }
        return rez;
    }

    // Устанавливает параметры текущей селекции, согласно переданным параметрам функции.
    public void SetParameters(int unchangable_player_number_in, int probablitity_mutation_in, int mutated_player_number_in,
        int mutated_out_number_in, int probablity_hard_mutation_in, int hard_mutated_player_number_in,
        int hard_mutated_out_number_in, int random_player_number_in)
    {
        this.unchangable_player_number   = unchangable_player_number_in;
        this.probablity_mutation         = probablitity_mutation_in;
        this.mutated_player_number       = mutated_player_number_in;
        this.mutated_out_number          = mutated_out_number_in;
        this.probablity_hard_mutation    = probablity_hard_mutation_in;
        this.hard_mutated_player_number  = hard_mutated_player_number_in;
        this.hard_mutated_out_number     = hard_mutated_out_number_in;
        this.random_player_number        = random_player_number_in;
    }
}

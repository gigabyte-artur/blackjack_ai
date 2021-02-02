package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Neuron;

import java.util.ArrayList;

public class Layer
{
    private ArrayList<Neuron> Neurons = new ArrayList<>();

    // Добавляет в текущий слой нейрон neuron_in.
    public void AddNeuron(Neuron neuron_in)
    {
        Neurons.add(neuron_in);
    }

    // ДОбавляет в текущий слой count_in нейронов.
    public void GenerateLayer(int count_in)
    {
        for (int i = 0; i < count_in; i++)
        {
            Neuron new_neuron = new Neuron();
            this.AddNeuron(new_neuron);
        }
    }

    // Выводит значения нейронов текущего слоя.
    public void ShowLayer()
    {
        int curr_signal;
        for (Neuron curr_neuron: Neurons)
        {
            curr_signal = curr_neuron.GetSignal();
            System.out.print(curr_signal + " ");
        }
        System.out.println();
    }

    // Возвращает количество нейронов в слое.
    public int GetSize()
    {
        return this.Neurons.size();
    }
}

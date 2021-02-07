package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Neuron;

import java.util.ArrayList;

public class Layer
{
    private ArrayList<Neuron> Neurons = new ArrayList<>();
    private NeuroNet parent_neuro_net;

    // Добавляет в текущий слой нейрон neuron_in.
    public void AddNeuron(Neuron neuron_in)
    {
        Neurons.add(neuron_in);
        neuron_in.SetParentLayer(this);
    }

    // Добавляет в текущий слой count_in нейронов.
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
        double curr_signal;
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

    // Возвращает родительскую нейронную сеть.
    public NeuroNet GetParentNeuroNet()
    {
       return this.parent_neuro_net;
    }

    // Устанавливает родительскую нейронную сеть в parent_neuro_net_in.
    public void SetParentNeuroNet(NeuroNet parent_neuro_net_in)
    {
        this.parent_neuro_net = parent_neuro_net_in;
    }

    // Возвращает все нейроны слоя.
    public ArrayList<Neuron> GetNeurons()
    {
        return Neurons;
    }

    // Обнуляет сигналы во всех нейронах слоя.
    public void EmptySignals()
    {
        for (Neuron curr_neuron:Neurons)
        {
            curr_neuron.SetSignal(0);
        }
    }

    // Устанавливает нейрону с номером neuron_id_in сигнал signal_in.
    public void SetSignalToNeuron(int neuron_id_in, double signal_in)
    {
        Neuron neuron_chng;
        if (this.Neurons.size() >= neuron_id_in)
        {
            neuron_chng = this.Neurons.get(neuron_id_in);
            neuron_chng.SetSignal(signal_in);
        }
        else
        {
            System.out.println("Недостаточно нейронов для установки сигнала");
        }
    }
}

package ru.gigabyte_artur.blackjack_ai;

import ru.gigabyte_artur.blackjack_ai.Neuron;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class Layer
{
    private ArrayList<Neuron> Neurons = new ArrayList<>();
    private NeuroNet parent_neuro_net;
    boolean is_input;
    boolean is_output;
    String id;

    // Конструктор.
    public Layer()
    {
        UUID uuid = UUID.randomUUID();
        this.id = uuid.toString();
    }

    // Добавляет в текущий слой нейрон neuron_in.
    public void AddNeuron(Neuron neuron_in)
    {
        Neurons.add(neuron_in);
        neuron_in.SetParentLayer(this);
    }

    // Добавляет в текущий слой count_in нейронов.
    public void GenerateLayer(int count_in, boolean is_input_in, boolean is_output_in)
    {
        for (int i = 0; i < count_in; i++)
        {
            Neuron new_neuron = new Neuron();
            this.AddNeuron(new_neuron);
        }
        this.is_input = is_input_in;
        this.is_output = is_output_in;
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

    // Возвращает, является ли текущий слой выходным.
    public boolean IsOutput()
    {
        return this.is_output;
    }

    // Возвращает, является ли текущий слой входным.
    public boolean IsInput()
    {
        return this.is_input;
    }

    // Возвращает максимальный сигнал в слое.
    private double MaxSignal()
    {
        ArrayList<Neuron> TargetNeurons;
        double rez = 0, curr_signal;
        TargetNeurons = this.GetNeurons();
        for (Neuron curr_target_neurons : TargetNeurons)
        {
            curr_signal = curr_target_neurons.GetSignal();
            if (Math.abs(curr_signal) > rez)
                rez = Math.abs(curr_signal);
        }
        return rez;
    }

    // Номализует текущий слой.
    public void Normalize()
    {
        ArrayList<Neuron> TargetNeurons, OutputNeurons;
        double curr_signal, max_signal, new_signal;
        Neuron OutputNeuron;
        TargetNeurons = this.GetNeurons();
        max_signal = this.MaxSignal();
        if (max_signal != 0)
        {
            for (Neuron curr_target_neurons : TargetNeurons)
            {
                curr_signal = curr_target_neurons.GetSignal();
                new_signal = curr_signal / max_signal;
                curr_target_neurons.SetSignal(new_signal);
            }
        }
        else
        {
            // Нулевой максимум. Пропускаем.
        }
    }

    // С вероятностью probablity_in устанавливает всем аксонам текущего слоя
    // случайные веса.
    public void Mutate(double probablity_in)
    {
        for (Neuron curr_neuron:this.Neurons)
        {
            curr_neuron.Mutate(probablity_in);
        }
    }

    // Возвращает идентификатор слоя.
    public String GetId()
    {
        return this.id;
    }

}

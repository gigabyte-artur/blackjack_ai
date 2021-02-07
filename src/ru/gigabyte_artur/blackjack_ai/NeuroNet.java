package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.List;

public class NeuroNet
{
    private ArrayList<Layer> Layers = new ArrayList<>();

    // Добавляет в модель слой layer_in.
    public void AddLayer(Layer layer_in)
    {
        this.Layers.add(layer_in);
        layer_in.SetParentNeuroNet(this);
    }

    // Генерирует слой с размером count_in и добавляет его в модель.
    public void GenerateAddLayer(int count_in)
    {
        Layer new_layer = new Layer();
        new_layer.GenerateLayer(count_in);
        this.AddLayer(new_layer);
    }

    // Выводит модель на экран.
    public void Show()
    {
        for (Layer curr_layer:this.Layers)
        {
            curr_layer.ShowLayer();
        }
    }

    // Получает следующий за layer_in слой в модели.
    private Layer NextLayer(Layer layer_in)
    {
        Layer rez = new Layer();
        boolean found = false;
        for (Layer curr_layer:this.Layers)
        {
            if (found)
            {
                rez = curr_layer;
                break;
            }
            if (curr_layer == layer_in)
            {
                found = true;
            }
        }
        return rez;
    }

    // Компилирует модель.
    public void Compile()
    {
        Layer next_layer;
        ArrayList <Neuron> target_neurons = new ArrayList<>();
        ArrayList <Neuron> source_neurons = new ArrayList<>();
        for (Layer curr_layer:this.Layers)
        {
            next_layer = this.NextLayer(curr_layer);
            if (next_layer != null)
            {
                target_neurons = next_layer.GetNeurons();
                source_neurons = curr_layer.GetNeurons();
                for (Neuron curr_target_neurons: target_neurons)
                {
                    for (Neuron curr_source_neurons:source_neurons)
                        curr_target_neurons.GenerateAddAxon(curr_source_neurons, 0);
                }
            }
        }
    }

    // Обнуляет сигналы во всех слоях текущей сети.
    public void EmptySignals()
    {
        for (Layer curr_layer:Layers)
        {
            curr_layer:EmptySignals();
        }
    }

    // Копирует модель из нейросети neuro_net_in.
    public void CopyModel(NeuroNet neuro_net_in)
    {
//        NeuroNet rez = new NeuroNet();
//        rez = neuro_net_in;
//        return rez;
        // TODO: Написать контруктор копирования.
//        this = neuro_net_in;
    }

    // Устанавливает во входном нейроне neuron_id_in значение сигнала signal_in.
    public void SetInputSignal(int neuron_id_in, double signal_in)
    {
        Layer input_layer;
        if (this.Layers.size() != 0)
        {
            input_layer = this.Layers.get(0);
            input_layer.SetSignalToNeuron(neuron_id_in, signal_in);
        }
        else
        {
            System.out.println("В нейронной сети недосточно слоёв для установки начального сигнала");
        }
    }
}

package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.Random;

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
    public void GenerateAddLayer(int count_in, boolean is_input_in, boolean is_output_in)
    {
        Layer new_layer = new Layer();
        new_layer.GenerateLayer(count_in, is_input_in, is_output_in);
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
        ArrayList <Neuron> target_neurons;
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

//    // Обнуляет сигналы во всех слоях текущей сети.
//    public void EmptySignals()
//    {
//        for (Layer curr_layer:Layers)
//        {
//            curr_layer:EmptySignals();
//        }
//    }

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

    // Вычисляет сигналы в текущей нейронной сети по слоям.
    public void CalcSignals()
    {
        ArrayList<Neuron> TargetNeurons;
        ArrayList<Axon> SourceAxons = new ArrayList<>();
        double sum_signal, curr_weight, curr_signal;
        Neuron source_neuron, OutputNeuron;
        for (Layer curr_layer: Layers)
        {
            if (!curr_layer.IsInput())
            {
                // Вычисление сигналов.
                TargetNeurons = curr_layer.GetNeurons();
                for (Neuron curr_target_neurons : TargetNeurons)
                {
                    SourceAxons = curr_target_neurons.GetAxons();
                    sum_signal = 0;
                    for (Axon curr_source_axons : SourceAxons)
                    {
                        curr_weight = curr_source_axons.GetWeight();
                        source_neuron = curr_source_axons.GetSource();
                        curr_signal = source_neuron.GetSignal();
                        sum_signal = sum_signal + (curr_signal * curr_weight);
                    }
                    curr_target_neurons.SetSignal(sum_signal);
                }
                // Нормализация.
                if (!curr_layer.IsOutput())
                    curr_layer.Normalize();
            }
            else
            {
                // Попускаем входной слой.
            }
        }
    }

    // Возвращает сигнал последнего нейрона.
    public double GetOutputSignal()
    {
        double rez;
        rez = 0;
        int layers_size;
        Neuron first_neuron;
        ArrayList<Neuron> last_last_neurons = new ArrayList<>();
        Layer last_layer;
        layers_size = this.Layers.size();
        last_layer = this.Layers.get(layers_size -1);
        if (last_layer.GetSize() > 0)
        {
            last_last_neurons = last_layer.GetNeurons();
            first_neuron = last_last_neurons.get(0);
            rez = first_neuron.GetSignal();
        }
        else
        {
            rez = 0;
        }
        return rez;
    }

    // Устанавливает во все веса случайные значения.
    public void RandomWeights()
    {
        double new_weight;
        final Random random = new Random();
        for (Layer curr_layer:this.Layers)
        {
            for (Neuron curr_neuron: curr_layer.GetNeurons())
            {
                for (Axon curr_axon: curr_neuron.GetAxons())
                {
                    new_weight = (random.nextDouble()*2) - 1;
                    curr_axon.SetWeight(new_weight);
                }
            }
        }
    }

    // С вероятностью probablity_in устанавливает всем аксонам текущей нейронной сети
    // случайные веса.
    public void Mutate(double probablity_in)
    {
        for (Layer curr_layer:this.Layers)
        {
            curr_layer.Mutate(probablity_in);
        }
    }
}

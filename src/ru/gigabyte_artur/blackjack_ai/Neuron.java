package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import ru.gigabyte_artur.blackjack_ai.Axon;

public class Neuron
{
    private double signal;
    private ArrayList<Axon> Axons = new ArrayList<>();
    private Layer parent_layer;

    // Получает сигнал нейрона.
    public double GetSignal()
    {
        return this.signal;
    }

    // Устанавливает сигнал нейрона в signal_in.
    public void SetSignal(int signal_in)
    {
        this.signal = signal_in;
    }

    // Добавляет аксон axon_in в список аксонов текщего нейрона.
    public void AddAxon(Axon axon_in)
    {
        this.Axons.add(axon_in);
    }

    // Создает аксон текщего нейрона с нейроном neuron_in со весом свзяи weight_in.
    public void GenerateAddAxon(Neuron neuron_in, double weight_in)
    {
        Axon new_axon = new Axon();
        new_axon.Set(neuron_in, weight_in);
        this.AddAxon(new_axon);
    }

    // Возвращает значение родительского слоя.
    public Layer GetParentLayer()
    {
        return this.parent_layer;
    }

    // Устанавливает родителским слоем слой parent_layer_in.
    public void SetParentLayer(Layer parent_layer_in)
    {
        this.parent_layer = parent_layer_in;
    }
}

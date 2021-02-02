package ru.gigabyte_artur.blackjack_ai;

import java.util.ArrayList;
import java.util.List;

public class NeuroNet
{
    private ArrayList<Layer> Layers = new ArrayList<>();

    //private List<ArrayList<Integer>> Weights = new ArrayList<ArrayList<Integer>>();

    // Добавляет в модель слой layer_in.
    public void AddLayer(Layer layer_in)
    {
        this.Layers.add(layer_in);
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
}

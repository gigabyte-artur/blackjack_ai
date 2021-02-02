package ru.gigabyte_artur.blackjack_ai;

public class Neuron
{
    private double signal;

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
}

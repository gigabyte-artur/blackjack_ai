package ru.gigabyte_artur.blackjack_ai;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.util.ArrayList;

public class Main
{
    public static void main(String[] args) throws IOException, SAXException, ParserConfigurationException {
        Generation generation1 = new Generation();
        /*Generation generation2 = new Generation();
        Selection selection1 = new Selection();
        generation1.InitRandom(100);
        for (int i = 0; i < 1000; i++)
        {
            System.out.print(i + ": ");
            generation1.Play();
            generation1.ShowStatic();
            generation2 = selection1.MakeSelection(generation1);
            generation1 = generation2;
            if (i % 100 == 0)
                generation2.SaveToFile("D:\\cars.xml");
        }*/
        generation1.LoadFromFile("D:\\cars.xml");
    }
}

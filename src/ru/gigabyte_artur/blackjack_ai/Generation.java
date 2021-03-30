package ru.gigabyte_artur.blackjack_ai;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import java.io.File;

public class Generation
{
    private ArrayList<Player> Players = new ArrayList<>();
    ScoreGen scores = new ScoreGen();

    // Добавляет игрока player_in в список игроков.
    public void AddPlayer(Player player_in)
    {
        this.Players.add(player_in);
    }

    // Инициализирует поколение случайными игроками.
    public void InitRandom(int generation_size_in)
    {
        Player added_player;
        NeuroNet model;
        model = GameBlackJack.GenerateModel();
        this.Players.clear();
        for (int i = 0; i < generation_size_in; i++)
        {
            added_player = new Player();
            added_player.RandomPlayer(model);
            this.Players.add(added_player);
        }
    }

    // Разыгрывает игры между игроками. Помещает результаты в хранилище результатов.
    public void Play()
    {
        GameBlackJack game1;
        int winner_id;
        for (Player player1: this.Players)
        {
            for (Player player2: this.Players)
            {
                if (!player1.equals(player2))
                {
                    game1 = new GameBlackJack();
                    game1.SetPlayers(player1, player2);
                    winner_id = game1.Play();
                    if (winner_id == 1)
                    {
                        this.scores.IncreaseScorePlayer(player1, 1);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                    else if (winner_id == 2)
                    {
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 1);
                    }
                    else
                    {
                        // Ничья. Не изменяем результат.
                        this.scores.IncreaseScorePlayer(player1, 0);
                        this.scores.IncreaseScorePlayer(player2, 0);
                    }
                }
            }
        }
    }

    // Отображает результаты игр текущего поколения.
    public void ShowScores()
    {
        this.scores.ShowScores();
    }

    // Вовзращает игроков с результатами от min_in до max_in места.
    public ArrayList<Player> GetTopPlayer(int min_in, int max_in)
    {
        return this.scores.GetTopPlayer(min_in, max_in);
    }

    // Отображает статистику игры.
    public void ShowStatic()
    {
        this.scores.ShowStatic();
    }

    // Сохраняет текущее поколение в файл с именем file_name_in.
    public void SaveToFile(String file_name_in)
    {
        try
        {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();
            // Поколение.
            Element root_element = doc.createElement("generation");
            doc.appendChild(root_element);
            // Игроки.
            Element players_element = doc.createElement("players");
            root_element.appendChild(players_element);
            for (Player curr_player:this.Players)
            {
                // Игрок.
                Element player_element = doc.createElement("player");
                players_element.appendChild(player_element);
                // Нейросеть.
                Element neuro_net_element = doc.createElement("neuro");
                player_element.appendChild(neuro_net_element);
                // Слои.
                Element layers_element = doc.createElement("layers");
                neuro_net_element.appendChild(layers_element);
                for (Layer curr_layer:curr_player.GetNeuroNet().GetLayers())
                {
                    // Слой.
                    Element layer_element = doc.createElement("layer");
                    layers_element.appendChild(layer_element);
                    // Идентификатор слоя.
                    Element layer_id_element = doc.createElement("id");
                    layer_element.appendChild(layer_id_element);
                    // Текст идентификатора.
                    Text layer_id_text = doc.createTextNode(curr_layer.GetId());
                    layer_id_element.appendChild(layer_id_text);

                }
            }

            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(file_name_in));
            transformer.transform(source, result);

//            // root element
//            Element rootElement = doc.createElement("cars");
//            doc.appendChild(rootElement);
//
//            // supercars element
//            Element supercar = doc.createElement("supercars");
//            rootElement.appendChild(supercar);
//
//            // setting attribute to element
//            Attr attr = doc.createAttribute("company");
//            attr.setValue("Ferrari");
//            supercar.setAttributeNode(attr);
//
//            // carname element
//            Element carname = doc.createElement("carname");
//            Attr attrType = doc.createAttribute("type");
//            attrType.setValue("formula one");
//            carname.setAttributeNode(attrType);
//            carname.appendChild(doc.createTextNode("Ferrari 101"));
//            supercar.appendChild(carname);
//
//            Element carname1 = doc.createElement("carname");
//            Attr attrType1 = doc.createAttribute("type");
//            attrType1.setValue("sports");
//            carname1.setAttributeNode(attrType1);
//            carname1.appendChild(doc.createTextNode("Ferrari 202"));
//            supercar.appendChild(carname1);
//
//            // write the content into xml file
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File("D:\\cars.xml"));
//            transformer.transform(source, result);
//
//            // Output to console for testing
//            StreamResult consoleResult = new StreamResult(System.out);
//            transformer.transform(source, consoleResult);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}

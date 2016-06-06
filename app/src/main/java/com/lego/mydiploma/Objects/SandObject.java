package com.lego.mydiploma.Objects;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lego on 05.05.2016.
 */
public class SandObject {
    public String name;
    public String value;

    public SandObject(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public List<SandObject> Data;

    public void initializeData() {
        Data = new ArrayList<>();
        Data.add(new SandObject("Моноцит", "71"));
        Data.add(new SandObject("Кварц", "29"));
        Data.add(new SandObject("Пирит", "Не найдено"));
        Data.add(new SandObject("Ильменит", "Не найдено"));
        Data.add(new SandObject("Гранат", "Не найдено"));
        Data.add(new SandObject("Мартит", "Не найдено"));
        Data.add(new SandObject("Ставролит", "Не найдено"));
        Data.add(new SandObject("Турмалин", "Не найдено"));
        Data.add(new SandObject("Циркон", "Не найдено"));
        Data.add(new SandObject("Магнетит", "Не найдено"));
        Data.add(new SandObject("Эпидот", "Не найдено"));
        Data.add(new SandObject("Рутил", "Не найдено"));
        Data.add(new SandObject("Биотит", "Не найдено"));
        Data.add(new SandObject("Барит", "Не найдено"));
        Data.add(new SandObject("Циртолит", "Не найдено"));
        Data.add(new SandObject("Дистен", "Не найдено"));
        Data.add(new SandObject("Амфибола", "Не найдено"));
        Data.add(new SandObject("Силлиманит", "Не найдено"));
        Data.add(new SandObject("Лейкоксен", "Не найдено"));
        Data.add(new SandObject("Халькопирит", "Не найдено"));
        Data.add(new SandObject("Полевой шпат", "Не найдено"));
        Data.add(new SandObject("Карбонат", "Не найдено"));
        Data.add(new SandObject("Андалузит", "Не найдено"));
        Data.add(new SandObject("Кремневые образования", "Не найдено"));
        Data.add(new SandObject("Углистый материал", "Не найдено"));
    }
}
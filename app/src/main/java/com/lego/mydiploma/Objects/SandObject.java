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

    // This method creates an ArrayList that has three Person objects
// Checkout the project associated with this tutorial on Github if
// you want to use the same images.
    public void initializeData() {
        Data = new ArrayList<>();
        Data.add(new SandObject("Emma Wilson", "23 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lavery Maiss", "25 years old"));
        Data.add(new SandObject("Lillie Watts", "35 years old"));
    }
}
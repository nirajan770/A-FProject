package com.abercrombiefitch.helper;


import android.content.Intent;

import com.abercrombiefitch.api.model.Button;
import com.abercrombiefitch.api.model.Promotion;

import java.util.ArrayList;
import java.util.List;

public class DataHelper {
    public static final String ITEM_TITLE = "Title";
    public static final String ITEM_DESCRIPTION = "Description";
    public static final String ITEM_URL = "http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-shorts";
    public static final String ITEM_FOOTER = "Footer";
    public static final String BUTTON_TARGET = "https://m.abercrombie.com";
    public static final String BUTTON_TITLE = "TEST BUTTON";

    private static Promotion item;
    private static Button button;

    public static Promotion getModelData() {
        item = new Promotion();
        item.setTitle(ITEM_TITLE);
        item.setDescription(ITEM_DESCRIPTION);
        item.setImage(ITEM_URL);
        item.setFooter(ITEM_FOOTER);
        Button button = new Button();
        button.setTitle(BUTTON_TITLE);
        button.setTarget(BUTTON_TARGET);
        List<Button> list = new ArrayList<>();
        list.add(button);
        // set the list of buttons
        item.setButton(list);

        return item;
    }
}

package com.abercrombiefitch;

import android.view.View;

import com.abercrombiefitch.api.model.Button;
import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.ui.CardAdapter;
import com.abercrombiefitch.ui.MainActivity;
import com.abercrombiefitch.util.PrefUtils;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertThat;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CardAdapterTest extends TestBase {

    private static final String ITEM_TITLE = "Title";
    private static final String ITEM_DESCRIPTION = "Description";
    private static final String ITEM_URL = "http://anf.scene7.com/is/image/anf/anf-US-20150629-app-women-shorts";
    private static final String ITEM_FOOTER = "Footer";
    private static final String BUTTON_TARGET = "https://m.abercrombie.com";
    private static final String BUTTON_TITLE = "TEST BUTTON";

    private MainActivity mainActivity;

    private CardAdapter cardAdapter;
    private Promotion item;

    @Before
    public void setup() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
        cardAdapter = new CardAdapter();
        cardAdapter.addData(createData());
    }

    /**
     * Creates sample data item
     */
    private Promotion createData() {
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

    @Test
    public void testItemCount() {
        int count = cardAdapter.getItemCount();

    }
}

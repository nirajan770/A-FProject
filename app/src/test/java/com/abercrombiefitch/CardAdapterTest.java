package com.abercrombiefitch;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.helper.DataHelper;
import com.abercrombiefitch.ui.CardAdapter;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;


import static org.assertj.android.api.Assertions.assertThat;
import static org.assertj.android.recyclerview.v7.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class CardAdapterTest extends TestBase {

    private Context context;
    private CardAdapter cardAdapter;
    private Promotion item;
    private RecyclerView recyclerView;

    @Before
    public void setup() {
        context = RuntimeEnvironment.application;
        cardAdapter = new CardAdapter();
        cardAdapter.addData(DataHelper.getModelData());
        item = cardAdapter.getItem(0);
    }

    @Test
    public void testGetItemTitle() {
        assertEquals(DataHelper.ITEM_TITLE + " was expected", item.getTitle(), cardAdapter.getItem(0).getTitle());
    }

    @Test
    public void testGetItemId() {
        assertEquals("Wrong ID", 0, cardAdapter.getItemId(0));
    }

    @Test
    public void testItemCount() {
        assertEquals("Incorrect item count", 1, cardAdapter.getItemCount());
    }

    @Test
    public void testViewHolderViews() {
        recyclerView = new RecyclerView(context);
        //View v = LayoutInflater.from(context).inflate(R.layout.recycler_card_item, null);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        CardAdapter.ViewHolder viewHolder = cardAdapter.onCreateViewHolder(recyclerView, 0);
        cardAdapter.onBindViewHolder(viewHolder, 0);

        // JUnit Assertions
        assertEquals(View.VISIBLE, viewHolder.getItemTitle().getVisibility());

        // Assert-J
        assertThat(viewHolder.getItemTitle()).isVisible().containsText(DataHelper.ITEM_TITLE);
        assertThat(cardAdapter).hasItemCount(1);
    }


}

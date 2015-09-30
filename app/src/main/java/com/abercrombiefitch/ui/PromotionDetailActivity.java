package com.abercrombiefitch.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.graphics.Palette;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abercrombiefitch.R;
import com.abercrombiefitch.api.model.Promotion;
import com.abercrombiefitch.ui.base.BaseActivity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PromotionDetailActivity extends BaseActivity {

    public static final String KEY_INTENT_PROMOTION_ITEM = "PROMOTION";

    @Bind(R.id.detail_item_image)
    ImageView itemImage;

    @Bind(R.id.detail_item_description)
    TextView itemDescription;

    @Bind(R.id.promo_button)
    Button promoButton;

    @Nullable
    @Bind(R.id.detail_item_footer)
    TextView itemFooter;

    private String title;
    private String description;
    private String buttonTitle;
    private String buttonTarget;
    private String footer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        if (intent != null) {
            try {
                Promotion passedItem = new ObjectMapper().readValue(intent.getStringExtra(KEY_INTENT_PROMOTION_ITEM), Promotion.class);
                // check for null
                if (passedItem != null) {
                    Picasso.with(itemImage.getContext())
                            .load(passedItem.getImage())
                                    //.into(itemImage);
                            .into(new Target() {
                                @Override
                                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                    itemImage.setImageBitmap(bitmap);
                                    Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
                                        @Override
                                        public void onGenerated(Palette palette) {
                                            Palette.Swatch vibrant = palette.getVibrantSwatch();
                                            promoButton.setBackgroundColor(vibrant.getRgb());
                                        }
                                    });
                                }
                                @Override
                                public void onBitmapFailed(Drawable errorDrawable) {
                                }

                                @Override
                                public void onPrepareLoad(Drawable placeHolderDrawable) {
                                }
                            });
                    // Set Toolbar title
                    title = passedItem.getTitle();
                    if (title != null && !TextUtils.isEmpty(title)) {
                        getSupportActionBar().setTitle(passedItem.getTitle());
                    }
                    // Set the description
                    description = passedItem.getDescription();
                    if(description != null && !TextUtils.isEmpty(description)) {
                        itemDescription.setText(description);
                    }
                    // Set the button attributes
                    com.abercrombiefitch.api.model.Button mButton = passedItem.getButton().get(0);
                    buttonTitle = mButton.getTitle();
                    buttonTarget = mButton.getTarget();
                    promoButton.setText(buttonTitle);
                    // Set the footer
                    footer = passedItem.getFooter();
                    if(footer != null && !TextUtils.isEmpty(footer)) {
                        itemFooter.setText(Html.fromHtml(footer).toString());
                    } else {
                        itemFooter.setVisibility(View.GONE);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    @OnClick(R.id.promo_button)
    public void onClick(Button button) {
        Intent i = new Intent(this, WebViewAcitivity.class);
        i.putExtra(WebViewAcitivity.KEY_URL_TO_LOAD, buttonTarget);
        startActivity(i);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("title", title);
        outState.putString("desc", description);
        outState.putString("button_title", buttonTitle);
        outState.putString("button_target", buttonTarget);
        outState.putString("footer", footer);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        title = savedInstanceState.getString("title");
        description = savedInstanceState.getString("desc");
        buttonTitle = savedInstanceState.getString("button_title");
        buttonTarget = savedInstanceState.getString("button_target");
        footer = savedInstanceState.getString("footer");
    }


}

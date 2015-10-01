package com.abercrombiefitch;


import android.content.Intent;

import com.abercrombiefitch.api.AbercrombieAPI;
import com.abercrombiefitch.api.model.ResponseData;
import com.abercrombiefitch.helper.DataHelper;
import com.abercrombiefitch.ui.MainActivity;
import com.abercrombiefitch.ui.PromotionDetailActivity;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import java.util.List;

import retrofit.Callback;

import static org.junit.Assert.assertNotNull;

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MainActivityUnitTest extends TestBase{

    private MainActivity mainActivity;

    @Mock
    private AbercrombieAPI mockAbercrombieAPI;

    @Captor
    private ArgumentCaptor<Callback<List<ResponseData>>> cb;



    @Before
    public void setup() {
        mainActivity = Robolectric.setupActivity(MainActivity.class);
    }

    @Test
    public void checkActivityNotNull() throws Exception {
        assertNotNull(mainActivity);
    }

}

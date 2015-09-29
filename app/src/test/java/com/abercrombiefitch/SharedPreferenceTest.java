package com.abercrombiefitch;

import android.content.SharedPreferences;
import android.test.suitebuilder.annotation.SmallTest;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@SmallTest
@RunWith(MockitoJUnitRunner.class)
public class SharedPreferenceTest {

    @Mock
    SharedPreferences preferences;
    @Mock
    SharedPreferences.Editor editor;



}
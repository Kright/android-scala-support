package com.github.kright.helloapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

/**
 * <a href="http://d.android.com/tools/testing/testing_android.html">Testing Fundamentals</a>
 */
public class ApplicationTest extends ActivityInstrumentationTestCase2<MainActivity> {

    public ApplicationTest() {
        super(MainActivity.class);
    }

    public void testActivityContent() {
        TextView textView = (TextView) getActivity().findViewById(R.id.text_view_hello);
        assertEquals(textView.getText(), "Hello World!");
    }
}

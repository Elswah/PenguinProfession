package com.mobileaders.penguinprofession.Utilities;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Amr on 9/24/2016.
 */

public class Fonts {

    public static void set(TextView[] textViews, Context context, int fontIndex) {
        for (TextView textView : textViews) {
            Typeface font = null;

            switch (fontIndex) {
                case 0:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans.ttf");
                    break;

                case 1:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans_bold.ttf");
                    break;

            }

            textView.setTypeface(font);
        }
    }


    public static void set(EditText[] editTexts, Context context, int fontIndex) {
        for (EditText editText : editTexts) {
            Typeface font = null;

            switch (fontIndex) {
                case 0:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans.ttf");
                    break;

                case 1:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans_bold.ttf");
                    break;
            }

            editText.setTypeface(font);
        }
    }

    public static void set(Button[] buttons, Context context, int fontIndex) {
        for (Button button : buttons) {
            Typeface font = null;

            switch (fontIndex) {
                case 0:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans.ttf");
                    break;

                case 1:
                    font = Typeface.createFromAsset(context.getResources().getAssets(), "droidsans_bold.ttf");
                    break;
            }

            button.setTypeface(font);
        }
    }

}

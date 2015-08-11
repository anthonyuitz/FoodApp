package com.khmkau.codeu.foodapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.widget.TextView;

/**
 * Created by kellyhosokawa on 8/2/15.
 */
public class DisclaimerActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disclaimer);

        // Food groups - children
        TextView link = (TextView) findViewById(R.id.textView2);
        String linkText = "<a href='https://www.eatforhealth.gov.au/food-essentials/how-much-do-we-need-each-day/recommended-number-serves-children-adolescents-and'>Eat for Health (Children)</a>";
        link.setText(Html.fromHtml(linkText));
        link.setMovementMethod(LinkMovementMethod.getInstance());

        // Food groups - adults
        TextView link2 = (TextView) findViewById(R.id.textView3);
        String linkText2 = "<a href='https://www.eatforhealth.gov.au/food-essentials/how-much-do-we-need-each-day/recommended-number-serves-adults'>Eat for Health (Adults)</a>";
        link.setText(Html.fromHtml(linkText));
        link2.setText(Html.fromHtml(linkText2));
        link2.setMovementMethod(LinkMovementMethod.getInstance());

        // Water
        TextView link3 = (TextView) findViewById(R.id.textView4);
        String linkText3 = "<a href='http://health.usnews.com/health-news/blogs/eat-run/2013/09/13/the-truth-about-how-much-water-you-should-really-drink'>U.S. News - Health</a>";
        link3.setText(Html.fromHtml(linkText3));
        link3.setMovementMethod(LinkMovementMethod.getInstance());

        // Nutritional Categories
        TextView link4 = (TextView) findViewById(R.id.textView6);
        String linkText4 = "<a href='http://www.fda.gov/Food/GuidanceRegulation/GuidanceDocumentsRegulatoryInformation/LabelingNutrition/ucm064928.htm'>U.S. FDA</a>";
        link4.setText(Html.fromHtml(linkText4));
        link4.setMovementMethod(LinkMovementMethod.getInstance());

        // Image Credits
        TextView link5 = (TextView) findViewById(R.id.textView8);
        String linkText5 = "<a href='http://www.flaticon.com/authors/freepik'>Flat Icon</a>";
        link5.setText(Html.fromHtml(linkText5));
        link5.setMovementMethod(LinkMovementMethod.getInstance());

    }

}

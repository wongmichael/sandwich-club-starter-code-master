package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    TextView akaTv;
    TextView originTv;
    TextView ingredientsTv;
    @BindView(R.id.description_tv) TextView descriptionTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
         originTv = findViewById(R.id.origin_tv);
        //TextView akaTv = findViewById(R.id.also_known_tv);
        akaTv = findViewById(R.id.also_known_tv);
         ingredientsTv = findViewById(R.id.ingredients_tv);
         //descriptionTv = findViewById(R.id.description_tv);
        //bind the view using butterknife
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if (intent == null) {
            //Toast.makeText(this, "a", Toast.LENGTH_SHORT).show();
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            //Toast.makeText(this, "b", Toast.LENGTH_SHORT).show();
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            //Toast.makeText(this, "c", Toast.LENGTH_SHORT).show();
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                //placeholder
                //.placeholder(R.drawable.user_placeholder)
                .placeholder(R.mipmap.ic_launcher)
                //error
                //.error(R.drawable.user_placeholder_error)
                .error(R.mipmap.ic_launcher)
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        List<String> aka = sandwich.getAlsoKnownAs();
        if (aka != null && !aka.isEmpty()) {
            //akaTv.setText(listToStrEnumeration(aka));
            akaTv.append(listStrings(aka));
            //Toast.makeText(this, String.valueOf(aka.size()), Toast.LENGTH_SHORT).show();
        }
        originTv.append(sandwich.getPlaceOfOrigin()+"\n");
        descriptionTv.append(sandwich.getDescription()+"\n");
        ingredientsTv.append(listStrings(sandwich.getIngredients()));

    }

    private String listStrings(List<String> list){
        StringBuilder str = new StringBuilder();
        for (int i=0;i<list.size();i++){
            str.append(list.get(i)+"\n");
            //break;
        }
        //alternative
        //str.append(TextUtils.join("\n",list));
        return str.toString();
    }

    private String listToStrEnumeration(List<String> list) {
        int listSize = list.size();
        int positionBeforeAnd = listSize - 2;
        int positionOfLastItem = listSize - 1;

        // StringBuilder used because of the performance.
        StringBuilder strBuilder = new StringBuilder();

        for (int i = 0; i < listSize; i++) {
            strBuilder.append(list.get(i));

            if (i < positionBeforeAnd) {
                strBuilder.append(", ");
            } else if (i < positionOfLastItem) {
                strBuilder.append(" and ");
            }
        }

        return strBuilder.toString();
    }
}

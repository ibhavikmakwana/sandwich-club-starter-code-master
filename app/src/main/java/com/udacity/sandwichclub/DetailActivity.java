package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        TextView mTvDescription = findViewById(R.id.description_tv);
        TextView mTvIngredients = findViewById(R.id.ingredients_tv);
        TextView mTvAlsoKnownAs = findViewById(R.id.also_known_tv);
        TextView mTvOrigin = findViewById(R.id.origin_tv);

        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        mTvDescription.setText(sandwich.getDescription().isEmpty() ? "NA" : sandwich.getDescription());
        if (sandwich.getIngredients().size() > 0) {
            for (String ingredients : sandwich.getIngredients()) {
                mTvIngredients.append(ingredients + "\n");
            }
        } else {
            mTvIngredients.setText("NA");
        }
        if (sandwich.getAlsoKnownAs().size() > 0) {
            for (String alsoKnownAs : sandwich.getAlsoKnownAs()) {
                mTvAlsoKnownAs.append(alsoKnownAs + "\n");
            }
        } else {
            mTvAlsoKnownAs.setText("NA");
        }
        mTvOrigin.setText(sandwich.getPlaceOfOrigin().isEmpty() ? "NA" : sandwich.getPlaceOfOrigin());

    }
}

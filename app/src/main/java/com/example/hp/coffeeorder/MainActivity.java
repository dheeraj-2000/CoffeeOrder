


package com.example.hp.coffeeorder;



import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity > 99) {
            Toast.makeText(this,"You cannot have more than 100 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else
            quantity = quantity + 1;
        display(quantity);
    }
    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity < 2) {
            Toast.makeText(this, "You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        else
            quantity = quantity - 1;
        display(quantity);
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        EditText enterName = (EditText) findViewById(R.id.enter_name);
        String name = enterName.getText().toString();

        CheckBox whippedCreamCheckbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckbox.isChecked();

        CheckBox chocolateCheckbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean haschChocolate = chocolateCheckbox.isChecked();


        int price =  calculatePrice( hasWhippedCream, haschChocolate);
        String priceMessage = createOrderSummary(name , price , hasWhippedCream , haschChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Cofee Order For " + name);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private int calculatePrice(boolean addWhippedCream, boolean addChocolate){
        int basePrice = 5;
        if ( addWhippedCream){
            basePrice = basePrice + 1;
        }

        if ( addChocolate) {
            basePrice = basePrice + 2;
        }

        return quantity * basePrice;
    }
    private  String createOrderSummary(String namae , int price , boolean addWhippedCrream , boolean addChocolate){
        String priceMessage = "Name: " + namae;
        priceMessage += "\nAdd Whopped cream? " + addWhippedCrream;
        priceMessage += "\nAdd Chocolate? " + addChocolate;
        priceMessage +=  "\nQuantity: " + quantity ;
        priceMessage +=  "\n\nTotal Price: $" + price +"\n" + getString(R.string.Thank_you);
        return priceMessage;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int numberOfCoffes) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + numberOfCoffes
        );
    }
}
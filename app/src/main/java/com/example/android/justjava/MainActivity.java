package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 98;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the plus button is clicked.
     */
    public void increment(View view) {
        if (quantity == 100) {
            Toast.makeText(this, "You cannot order more than 100 cups of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, "You cannot order less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        CheckBox whippedCreamBox = findViewById(R.id.whCream_check_box);
        boolean hasWhippedCream = whippedCreamBox.isChecked();
        CheckBox chocolateBox = findViewById(R.id.chocolate_check_box);
        boolean hasChocolate = chocolateBox.isChecked();
        EditText nameBox = findViewById(R.id.name_edit_box);
        String myName = nameBox.getText().toString();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String dspMessage = createOrderSummary(price, hasWhippedCream, hasChocolate, myName);
        displayMessage(dspMessage);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(intent.EXTRA_SUBJECT, "Just Java order for " + myName);
        intent.putExtra(Intent.EXTRA_TEXT, dspMessage);
        if (intent.resolveActivity(getPackageManager())!=null){
         startActivity(intent);
        }
    }


    /**
     * This method creates a summary of the order.
     *
     * @param price of the order
     * @return text summary
     */
    public String createOrderSummary(int price, boolean whippedState, boolean chocolateState, String myName) {
        String priceMessage = getString(R.string.order_summary_name, myName) +
                "\nAdd Whipped Cream? " + whippedState +
                "\nAdd Whipped Cream? " + chocolateState +
                "\nQuantity: " + quantity +
                "\nTotal: $" + price;
        priceMessage += "\nThank you!";
        return priceMessage;
    }

    /**
     * Calculates the price of the order.
     *
     * @param addChocolate    is whether or not the user wants chocolate topping
     * @param addWhippedCream is whether or not the user wants whipped cream topping
     * @return total price
     */
    private int calculatePrice(boolean addWhippedCream, boolean addChocolate) {
        int basePrice = 5;
        if (addWhippedCream) {
            basePrice += 1;
        }
        if (addChocolate) {
            basePrice += 2;
        }
        return quantity * basePrice;
    }

    /**
     * This method displays the given message on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = findViewById(
                R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
package com.example.android.quiz;

import android.content.Context;
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
    int quantity = 1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    /**
     * This method is called when the submit button is clicked.
     */
    public void submitTest(View view) {
        CheckBox Q1O1 = (CheckBox)findViewById(R.id.checkboxQ1O1);
        boolean Question1Option1 = Q1O1.isChecked();
        CheckBox Q1O2 = (CheckBox)findViewById(R.id.checkboxQ1O2);
        boolean Question1Option2 = Q1O2.isChecked();
        int score = calculateScore(Question1Option1,Question1Option2);
        EditText clientNameField = (EditText) findViewById(R.id.Name_edit_view);
        String clientName = clientNameField.getText().toString();
        String orderSummary = createOrderSummary (clientName, score, Question1Option1, Question1Option2);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "JustJave order for " + clientName);
        intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * Old method for Order Button= This method is called when the order button is clicked.
     */
//    public void submitOrder(View view) {
//      CheckBox whippedCreamBox = (CheckBox)findViewById(R.id.checkboxQ1O1);
//      boolean hasWhippedCream = whippedCreamBox.isChecked();
//      CheckBox chocolateBox = (CheckBox)findViewById(R.id.checkboxQ1O2);
//      boolean hasChocolate = chocolateBox.isChecked();
//      int price = calculatePrice(hasWhippedCream,hasChocolate);
//      EditText clientNameField = (EditText) findViewById(R.id.Name_edit_view);
//      String clientName = clientNameField.getText().toString();
//      String orderSummary = createOrderSummary (clientName, price, hasWhippedCream, hasChocolate);
//      Intent intent = new Intent(Intent.ACTION_SENDTO);
//      intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//      intent.putExtra(Intent.EXTRA_SUBJECT, "JustJave order for " + clientName);
//      intent.putExtra(Intent.EXTRA_TEXT, orderSummary);
//      if (intent.resolveActivity(getPackageManager()) != null) {
//          startActivity(intent);
//      }
//  }

    /**
     * Calculates the price of the order.
     *
     * @return Total Price
     */
    private int calculateScore(boolean Question1Option1, boolean Question1Option2) {
        int baseScore = 0;
        if (Question1Option1) {
            baseScore = baseScore + 1;
        }
        if (Question1Option2)  {
            baseScore = baseScore;
        }
        return baseScore;
    }

    /**
     * takes the total amount of sale and returns a confirmation message.
     * @param score from the test
     * @param Question1Option1 Option 1 of the first question - testing passing it through
     * @param Question1Option2 Option 2 of the first question - testing passing it through
     * @param clientName name of the customer
     *@return final confirmation back to the main activity of price name and thank you.
     */
    private String createOrderSummary(String clientName, int score, boolean Question1Option1, boolean Question1Option2) {

        String PriceMessage = getString(R.string.order_summary_name, clientName) ;
        PriceMessage += "\n" + "Add whipped cream? " + Question1Option1;
        PriceMessage += "\n" + "Add Chocolate? " + Question1Option2;
        PriceMessage += "\n" + "Quantity : " + quantity ;
        PriceMessage += "\n" + "Total : $" + score ;
        PriceMessage += "\n" + getString(R.string.thank_you);
        return PriceMessage;


    }
    /**
     * Creates an email intent with the order Summary.

     */
    public void composeEmail(String[] addresses, String subject, Uri attachment) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }



    /**
     * This method is called when the - button is clicked.
     */
    public void decrement(View view) {
        if (quantity >= 2) {
            quantity = quantity - 1;
        }
        else {
            /**
             * Creates a toast message for too few coffee cups.
             */
            Context context = getApplicationContext();
            CharSequence text = "You can't order less than 1";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }



}

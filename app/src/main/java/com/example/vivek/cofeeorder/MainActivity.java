package com.example.vivek.cofeeorder;
import java.text.NumberFormat;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    /**
     * This app displays an order form to order coffee.
     */
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void increment(View view){
        if(quantity == 100)
        {
            Toast.makeText(this,"Cannot be more than 100",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity= quantity+1;
        display(quantity);
    }
    public void decrement(View view){
        if(quantity == 1)
        {
            Toast.makeText(this,"Cannot be less than 1",Toast.LENGTH_SHORT).show();
            return;
        }
        quantity=quantity-1;
        display(quantity);
    }
    private int calculatePrice(boolean cream, boolean choco)
    {
        int basePrice = 5;
        if(cream)
        {
            basePrice = basePrice +1;
        }
        if (choco) {
            basePrice = basePrice +2;
        }
        basePrice = quantity * basePrice;
        return basePrice;
    }
    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText userName = (EditText) findViewById(R.id.edit_text_view);
        String name = userName.getText().toString();
        CheckBox cream = (CheckBox) findViewById(R.id.cream_check_box);
        boolean creamChecked = cream.isChecked();
        CheckBox choco = (CheckBox) findViewById(R.id.choco_check_box);
        boolean chocolatechecked = choco.isChecked();
        int price = calculatePrice(creamChecked,chocolatechecked);
        String message = createOrderSummery(name,price,creamChecked,chocolatechecked);
        displayMessage(message);
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        //intent.putExtra(Intent.EXTRA_EMAIL, );
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Oder for "+name);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.order_summery_text_view);
        priceTextView.setText(message);
    }
    private String createOrderSummery(String name, int price, boolean creamTopping, boolean chocolateTopping){

        String priceMessage = "Name : "+name;
        priceMessage += "\nQuantity : " +quantity;
        if(creamTopping == true) {
            priceMessage += "\nAdd Cream topping " ;
        }
        if(chocolateTopping==true){
        priceMessage += "\nAnd Chocolate topping"; }
        priceMessage += "\nTotal Price : " +price;
        priceMessage += "\nThank you.!!!";
        return priceMessage;
    }

}

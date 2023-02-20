package com.example.arithmeticapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

enum Operator
{
    Addition,
    Subtraction,
    Multiplication,
    Division
}

public class MainActivity extends AppCompatActivity {

    double answer;
    int correct = 0;
    int wrong = 0;
    Operator currentOperator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        _createOperatorSpinner();
        _createDigitSpinner();
        _updateScoreText();
        this._nextQuestion();
    }

    public void genQuestion(View v) {
        this._nextQuestion();
    }

    public void checkAnswers(View v) {
        double userAnswer = this._getUserAnswer();
        if (userAnswer != -1) {
            boolean correct = userAnswer == answer;
            this._showResponse(correct);
            this._updateScore(correct);
            this._updateScoreText();
            this._nextQuestion();
        }
    }

    private void _nextQuestion() {
        this.currentOperator = _getCurrentOperator();
        int currentDigits = _getCurrentDigits();
        int n1 = 1 + (int)(Math.random()*(Math.pow(10, currentDigits) -1));
        int n2 = 1 + (int)(Math.random()*(Math.pow(10, currentDigits) -1));
        switch(this.currentOperator)
        {
            case Addition:
                this.answer = n1 + n2;
                break;
            case Subtraction:
                this.answer = n1 - n2;
                break;
            case Multiplication:
                this.answer = n1 * n2;
                break;
            case Division:
                this.answer = (double) n1 / (double) n2;
                break;
        }
        _showQuestion(n1, n2);
    }

    private void _showQuestion(int n1, int n2) {
        TextView questionText = findViewById(R.id.textView);
        questionText.setText(n1 + _getOperatorSymbol(this.currentOperator) + n2);
        ((EditText) findViewById(R.id.answerInput)).setText("");
    }

    private Operator _getCurrentOperator() {
        Spinner operatorSpinner = findViewById(R.id.spinner);
        switch((int) operatorSpinner.getSelectedItemId())
        {
            case 0: return Operator.Addition;
            case 1: return Operator.Subtraction;
            case 2: return Operator.Multiplication;
            case 3: return Operator.Division;
        }
        return Operator.Addition;
    }

    private String _getOperatorSymbol(Operator op) {
        switch(this.currentOperator) {
            case Addition: return "+";
            case Subtraction: return "-";
            case Multiplication: return "x";
            case Division: return "/";
        }
        return "";
    }

    private int _getCurrentDigits() {
        Spinner spinner = findViewById(R.id.digitsSpinner);
        return (int) spinner.getSelectedItemId()+1;
    }

    private void _showResponse(boolean correct) {
        String responseString;
        if (correct)
            responseString =  "Correct";
        else
            responseString = "Wrong";
        Toast.makeText(getApplicationContext(), responseString, Toast.LENGTH_LONG).show();
    }

    private void _updateScore(boolean correct) {
        if (correct)
            this.correct++;
        else
            this.wrong++;
    }

    private void _updateScoreText() {
        ((TextView) findViewById(R.id.pointsText)).setText("Points " + 3 * wrong);
        ((TextView) findViewById(R.id.correctText)).setText("Wrong " + wrong);
        ((TextView) findViewById(R.id.wrongsText)).setText("Correct " + correct);
    }

    private void _createOperatorSpinner() {
        Spinner operatorSpinner = findViewById(R.id.spinner);
        String[] operatorsList = new String[] { "Addition", "Subtraction", "Multiplaction", "Division" };

        ArrayAdapter<String> adapterOperators = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, operatorsList);
        operatorSpinner.setAdapter(adapterOperators);
    }

    private void _createDigitSpinner() {
        Spinner spinner = findViewById(R.id.digitsSpinner);
        String[] list = new String[] { "Single Digit", "Two Digits", "Three Digits" };

        ArrayAdapter<String> adapterOperators = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinner.setAdapter(adapterOperators);
    }

    private double _getUserAnswer()
    {
        EditText answerInput = findViewById(R.id.answerInput);
        try {
            return Double.parseDouble(answerInput.getText().toString());
        }
        catch (NumberFormatException e) {
            Toast.makeText(getApplicationContext(), "Check your answer is a number", Toast.LENGTH_LONG).show();
        }
        return -1;
    }

}
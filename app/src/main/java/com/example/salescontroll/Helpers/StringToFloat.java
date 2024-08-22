package com.example.salescontroll.Helpers;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Locale;

public class StringToFloat {

    public static float convertStringToFloat(String value) {
        if (value == null) return 0.0f;

        NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY); // Definindo a localidade para interpretar a vírgula como separador decimal
        try {
            Number number = format.parse(value);
            return number.floatValue();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0.0f; // Retornar um valor padrão em caso de erro
        }
    }

    public static String convertFloatToString(float value) {
        NumberFormat format = NumberFormat.getNumberInstance(Locale.GERMANY); // Definindo a localidade para formatar com vírgula como separador decimal
        return format.format(value);
    }

    public static String editStringValueWithDollar(String value) {
        return "R$ " + value + ",00";
    }








}

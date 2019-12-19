package com.example.data;

import android.annotation.SuppressLint;

import com.example.m2app.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class MobileCountryCodeMobileNetworkCode {
    @SuppressLint("UseSparseArrays")
    private static Map<Integer, int[]> map = new HashMap<>();
    private static Map<String, Integer> mapNames = new HashMap<>();

    private MobileCountryCodeMobileNetworkCode() {

    }

    static {
        mapNames.put("RU", 250);
        map.put(250, new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 15, 16, 17, 19, 20,
        23, 28, 32, 35, 37, 38, 39, 44, 50, 92, 93, 99}); //россия

    }

    public static int[] getMNCList(int mcc) {
        try {
            return map.get(mcc);
        } catch (Exception e) {
            return new int[]{};
        }
    }

    public static int[] getMNCList(String name) {
        try {
            return map.get(mapNames.get(name));
        } catch (Exception e) {
            return new int[]{};
        }
    }

    @SuppressLint("Assert")
    public static String getCoutryName(double latitude, double longtitude) throws IOException {
        URL url = new URL("https://us1.unwiredlabs.com/v2/reverse.php?token="
                + R.string.open_cell_id_token
                + " &lat=" + latitude
                + " &lon=" + longtitude);
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = bufferedReader.readLine()) != null) {
                response.append(inputLine);
            }
            bufferedReader.close();
            assert false;
            JSONObject json = new JSONObject(inputLine);
            String country_code = (String)((JSONObject)json.get("address")).get("country_code");
            System.out.println(country_code);
            return country_code;
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
        }
        return "";
    }
}

/*
{
    "token": "a65aee3fdcc744",
    "radio": "gsm",
    "mcc": 250,
    "mnc": 1,
    "cells": [{
        "cid": 2
    }],
    "address": 0
}
 */

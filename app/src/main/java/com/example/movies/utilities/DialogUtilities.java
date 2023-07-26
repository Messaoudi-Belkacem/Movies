package com.example.movies.utilities;

import android.app.Activity;
import android.app.AlertDialog;
import android.util.Log;

public class DialogUtilities {

    private DialogUtilities() {}

    public static AlertDialog MoviesFolderDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Attention! The \"Movies\" folder was not found." +
                        "To proceed, kindly create the folder and place all your movie files inside it." +
                        "Thank you!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Log.d("MoviesFolderDialog.java", "User Saw the MoviesFolderDialog and clicked OK");
                });
        return builder.create();
    }

    public static AlertDialog MoviesFolderIsEmptyDialog(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setMessage("Attention! The \"Movies\" folder was found." +
                        "To proceed, kindly place all your movie files inside it." +
                        "Thank you!")
                .setPositiveButton("OK", (dialogInterface, i) -> {
                    Log.d("MoviesFolderDialog.java", "User Saw the MoviesFolderIsEmptyDialog and clicked OK");
                });
        return builder.create();
    }
}

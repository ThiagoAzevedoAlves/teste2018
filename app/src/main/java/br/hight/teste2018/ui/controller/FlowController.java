package br.hight.teste2018.ui.controller;

/**
 * Created by Thiago Azevedo on 26/05/2017.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class FlowController {

    /**
     * Lança a Activity
     *
     * @param origin  Activity de origem
     * @param destiny Activity de destino
     */
    public static void launchActivity(@NonNull Activity origin, @NonNull Class<? extends Activity> destiny) {
        launchActivity(origin, destiny, false, null);
    }

    /**
     * Lança a Activity
     *
     * @param origin  Activity de origem
     * @param destiny Activity de destino
     * @param newTask Limpar histórico de navegação
     */
    public static void launchActivity(@NonNull Activity origin, @NonNull Class<? extends Activity> destiny, boolean
            newTask) {
        launchActivity(origin, destiny, newTask, null);
    }

    /**
     * Lança a Activity
     *
     * @param origin  Activity de origem
     * @param destiny Activity de destino
     * @param newTask Limpar histórico de navegação
     * @param args    Argumentos para a nova Activity
     */
    public static void launchActivity(@NonNull Activity origin, @NonNull Class<? extends Activity> destiny, boolean
            newTask, @Nullable Bundle args) {
        Intent intent = new Intent(origin, destiny);
        if (newTask) {
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        if (args != null) {
            intent.putExtras(args);
        }
        origin.startActivity(intent);
    }

}

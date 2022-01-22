package com.dysconcsa.pancasan;

import com.dysconcsa.pancasan.dao.DataAccess;
import com.dysconcsa.pancasan.views.AppViewManager;
import com.gluonhq.attach.util.Platform;
import com.gluonhq.charm.glisten.application.AppManager;
import com.gluonhq.charm.glisten.visual.Swatch;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.util.Objects;

public class PancasanApplication extends Application {

    private final AppManager appManager = AppManager.initialize(this::postInit);
    public static final String POPUP_FILTER_CAJA = "Filtrar Caja";
    public static final String POPUP_FILTER_RUBRO = "Filtrar Rubro";
    public static final String MENU_CAJA_LAYER = "Menu Caja";

    @Override
    public void init() {
        AppViewManager.registerViewsAndDrawer();
    }

    @Override
    public void start(Stage primaryStage) {
        DataAccess.getInstance();
        primaryStage.setTitle("Sistema de Gestion Comercial - Taller Pancasan");
        if (Platform.isDesktop()) {
            primaryStage.setMaximized(true);
        }
        appManager.start(primaryStage);
    }

    private void postInit(Scene scene) {
        Swatch.GREEN.assignTo(scene);
        scene.getStylesheets().add(Objects.requireNonNull(PancasanApplication.class.getResource("style.css")).toExternalForm());
        ((Stage) scene.getWindow()).getIcons().add(new Image(Objects.requireNonNull(PancasanApplication.class.getResourceAsStream("/icon.png"))));
    }

    @Override
    public void stop() {
        javafx.application.Platform.exit();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
